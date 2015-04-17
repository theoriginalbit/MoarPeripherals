/*
 * Copyright 2015 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.bitnet;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetUniverse;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetWorld;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetNode;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetPortal;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetRelay;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class BitNetWorld implements IBitNetWorld {
    /**
     * Makes sure that the supplied channel is between the specified range
     *
     * @param channel the channel to check
     * @return the valid channel
     * @throws LuaException if the channel is invalid an error will occur
     */
    public static int checkChannel(int channel) throws LuaException {
        if (channel >= 0 && channel <= 65535) {
            return channel;
        }
        throw new LuaException("Expected number in range 0-65535");
    }

    /**
     * Gets the range a message can be transmitted from a node, the node type and whether the world is storming are
     * the factors which determine the distance.
     *
     * @param relay the node to find the range of
     * @return the range the message can be sent
     */
    private static int getTransmitRange(IBitNetRelay relay) {
        final World world = relay.getWorld();
        final boolean isStorming = world.isRaining() && world.isThundering();

        switch (relay.getRelayType()) {
            case SHORT_RANGE:
                return isStorming ? ConfigData.miniAntennaRangeStorm : ConfigData.miniAntennaRange;
            case LONG_RANGE:
                return isStorming ? ConfigData.antennaRangeStorm : ConfigData.antennaRange;
        }

        throw new IllegalStateException("Invalid NodeType encountered while trying to calculate transmission range");
    }

    /**
     * Gets the Euclidean distance between the two supplied locations
     *
     * @param origin the starting location
     * @param target the ending location
     * @return the Euclidean distance
     */
    private static double getDistanceBetween(Vec3 origin, Vec3 target) {
        return Math.sqrt(origin.squareDistanceTo(target));
    }

    /**
     * The universe this network resides within, this reference is kept so that portals can work
     */
    private final IBitNetUniverse universe;

    /**
     * The world this network transmits in
     */
    private final World world;

    /**
     * A mapping of the relays on the channels
     */
    private final Map<Integer, Set<IBitNetRelay>> channelMap = Maps.newHashMap();

    /**
     * The {@link IBitNetRelay}s that are on the network
     */
    private final Set<IBitNetRelay> relaySet = Sets.newHashSet();

    /**
     * The {@link IBitNetPortal}s that are on the network
     */
    private final Set<IBitNetPortal> portalSet = Sets.newHashSet();

    /**
     * A mapping to track the messages that have been seen by particular nodes so that messages do not permanently
     * get sent around the network when a node is working in an auto-repeating mode, or to prevent messages from
     * travelling through portals multiple times.
     */
    private final Map<IBitNetNode, Set<UUID>> seenMessages = Maps.newHashMap();

    /**
     * The queue of {@link BitNetMessage}s to be sent
     */
    private final ConcurrentLinkedQueue<DelayedMessage> messageQueue = new ConcurrentLinkedQueue<>();

    public BitNetWorld(BitNetUniverse universe, World world) {
        this.universe = universe;
        this.world = world;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPortal(IBitNetPortal portal) {
        if (!world.equals(portal.getWorld())) {
            throw new IllegalStateException("Portal attempting to register to a network that is not in the same world");
        }
        portalSet.add(portal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePortal(IBitNetPortal portal) {
        portalSet.remove(portal);
        seenMessages.remove(portal); // remove the seen messages, this node is disappearing from the network
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRelay(IBitNetRelay relay) {
        if (!world.equals(relay.getWorld())) {
            throw new IllegalStateException("Relay attempting to register to a network that is not in the same world");
        }
        relaySet.add(relay);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRelay(IBitNetRelay relay) {
        relaySet.remove(relay);
        seenMessages.remove(relay); // remove the seen messages, this node is disappearing from the network
    }

    @Override
    public boolean isChannelOpen(IBitNetRelay relay, int channel) throws LuaException {
        if (!relaySet.contains(relay)) {
            throw new IllegalStateException("Developer error. Relay that is trying to open channel is not registered");
        }
        return getRelaySet(channel).contains(relay);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean openChannel(IBitNetRelay relay, int channel) throws LuaException {
        if (!relaySet.contains(relay)) {
            throw new IllegalStateException("Developer error. Relay that is trying to open channel is not registered");
        }
        return getRelaySet(channel).add(relay);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean closeChannel(IBitNetRelay relay, int channel) throws LuaException {
        if (!relaySet.contains(relay)) {
            throw new IllegalStateException("Developer error. Relay that is trying to close channel is not registered");
        }
        return getRelaySet(channel).remove(relay);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tick() {
        final Iterator<DelayedMessage> it = messageQueue.iterator();
        while (it.hasNext()) {
            DelayedMessage message = it.next();
            if (message.tick()) {
                it.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transmit(IBitNetRelay sender, BitNetMessage payload) throws LuaException {
        // get the max distance the message can be sent
        final int range = getTransmitRange(sender);

        // make this message seen for the sending node so we don't try to send to itself later
        makeSeen(sender, payload);
        transmitImpl(sender.getPosition(), range, payload);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void teleport(Vec3 pos, double distanceRemaining, BitNetMessage payload) throws LuaException {
        transmitImpl(pos, distanceRemaining, payload);
    }

    /**
     * Sends a {@link BitNetMessage} over the network. This method accepts a send position and distance to allow for
     * custom sends for say a portal repeating, where the message needs to be sent from a non-node location at the
     * other side of a portal.
     * <p/>
     * Once invoked this method will iterate all the nodes that are on this network and registered to receive on the
     * sending channel and check if a message should be sent to it. Once done it will also check all the portals on
     * this network and see if a message teleport can occur
     *
     * @param senderPosition the location the message is being sent from
     * @param distance       the max send distance
     * @param payload        the message to send
     */
    private void transmitImpl(Vec3 senderPosition, double distance, BitNetMessage payload) throws LuaException {
        // validate the channels
        checkChannel(payload.getSendChannel());
        checkChannel(payload.getReplyChannel());

        /*
         * Send to the relays that are registered to receive on the sending channel of the payload
         */
        final Set<IBitNetRelay> relays = getRelaySet(payload.getSendChannel());

        for (IBitNetRelay receiver : relays) {
            final Set<UUID> seen = getSeen(receiver);

            // if the node hasn't seen the message before
            if (!seen.contains(payload.getId())) {
                // get the Euclidean distance between the two nodes
                final double distanceBetween = getDistanceBetween(senderPosition, receiver.getPosition());

                // if the node is within range we send the message
                if (distanceBetween > 0 && distanceBetween <= distance) {
                    // make it so this node never sees this message again
                    makeSeen(receiver, payload);

                    /*
                     * create a new instance of the message from the current one so that the distance updates correctly
                     * especially when its a repeated message; we build it from the current one so it still identifies
                     * as the same message but can have different distances for different paths
                     */
                    final BitNetMessage newMessage = new BitNetMessage(payload, distanceBetween);
                    messageQueue.add(new DelayedMessage(receiver, newMessage, distanceBetween));
                }
            }
        }

        /*
         * Send to portals within range
         */

        for (IBitNetPortal portal : portalSet) {
            // get the distance from the original sender to this portal
            final double distanceBetween = getDistanceBetween(senderPosition, portal.getPosition());
            // figure out how much further the message can send
            final double remaining = distance - distanceBetween;
            final Set<UUID> seen = getSeen(portal);

            // if the portal is active, hasn't seen the message before, and there is still transmit range
            if (portal.portalActive() && !seen.contains(payload.getId()) && remaining > 0) {
                // make it so this portal never sees this message again
                makeSeen(portal, payload);
                // send the message on the other side with the remaining distance to preserve the 'original' range
                final IBitNetWorld otherNetwork = universe.getBitNetWorld(portal.getConnectionWorld());
                otherNetwork.teleport(portal.getConnectionPosition(), remaining, payload);
            }
        }
    }

    /**
     * Gets the set of nodes which are registered to receive under a specific channel. If there are no nodes that are
     * receiving on this channel an empty set will be returned.
     *
     * @param channel the send channel
     * @return the nodes receiving on this channel
     */
    private Set<IBitNetRelay> getRelaySet(int channel) throws LuaException {
        Set<IBitNetRelay> set = channelMap.get(checkChannel(channel));
        if (set == null) {
            channelMap.put(channel, set = new HashSet<>());
        }
        return set;
    }

    /**
     * Get the set of all messages seen by a particular node. If the node has not seen any messages yet and empty set
     * will be returned.
     *
     * @param node the node to get the seen messages for
     * @return the messages seen by this node
     */
    private Set<UUID> getSeen(IBitNetNode node) {
        Set<UUID> set = seenMessages.get(node);
        if (set == null) {
            seenMessages.put(node, set = new HashSet<>());
        }
        return set;
    }

    /**
     * Adds the message to the seen messages list for the supplied node.
     *
     * @param node    the node to set the message as seen
     * @param payload the message to set as seen
     */
    private void makeSeen(IBitNetNode node, BitNetMessage payload) {
        Set<UUID> set = getSeen(node);
        set.add(payload.getId());
    }
}
