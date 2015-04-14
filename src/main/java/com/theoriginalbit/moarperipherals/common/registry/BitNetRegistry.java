/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.istack.internal.NotNull;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetNode;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetNode.NodeType;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetRegistry;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class BitNetRegistry implements IBitNetRegistry {
    /**
     * The BitNet instance
     */
    public static final BitNetRegistry INSTANCE = new BitNetRegistry();

    /**
     * The {@link IBitNetNode}s on the network
     */
    private final ArrayList<IBitNetNode> nodes = Lists.newArrayList();

    /**
     * The queue of {@link BitNetMessage}s to be sent
     */
    private final ConcurrentLinkedQueue<DelayedMessage> messageQueue = new ConcurrentLinkedQueue<>();

    /**
     *
     */
    private final HashMap<IBitNetNode, ArrayList<UUID>> seenMessages = Maps.newHashMap();

    private BitNetRegistry() {
        // prevent instances
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(@NotNull IBitNetNode node) {
        if (!nodes.contains(node)) {
            nodes.add(node);
            // add a list to track the messages sent to this node, that way we don't
            // send a message multiple times to the same node
            seenMessages.put(node, new ArrayList<UUID>());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(@NotNull IBitNetNode node) {
        if (nodes.contains(node)) {
            nodes.remove(node);
            // since this node is being removed we don't need to track messages
            // previously sent to this node
            seenMessages.remove(node);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void transmit(@NotNull IBitNetNode sender, @NotNull BitNetMessage payload) {
        final World world = sender.getWorld();
        final Vec3 pos = sender.getWorldPosition();
        final int range = calculateTransmitRange(sender.getNodeType(), (world.isRaining() && world.isThundering()));

        // search all the nodes on the network
        for (IBitNetNode receiver : nodes) {
            // check if there is a mapping which contains this message for this receiver
            final boolean seen = seenMessages.get(receiver).contains(payload.getId());

            // if this node isn't the sending node, and both nodes are in the same world
            if (!sender.equals(receiver) && world.equals(receiver.getWorld()) && !seen) {
                // get the Euclidean distance between the two nodes
                final double distance = Math.sqrt(pos.squareDistanceTo(receiver.getWorldPosition()));
                // if the node is within range we send the message
                if (distance > 0 && distance <= range) {
                    /*
                     * create a new instance of the message from the current one so that the distance updates correctly
                     * especially when its a repeated message; we build it from the current one so it still identifies
                     * as the same message for detection against message propagation
                     */
                    final BitNetMessage newMessage = new BitNetMessage(payload).addDistance(distance);
                    messageQueue.add(new DelayedMessage(receiver, newMessage, distance));
                }
            }
        }
    }

    /**
     * Calculates the range a message can be transmitted from a node, the node type and whether the world is storming
     * are the factors which determine the distance.
     *
     * @param nodeType   the node type
     * @param isStorming whether the world is storming
     * @return the range the message can be sent
     */
    private int calculateTransmitRange(NodeType nodeType, boolean isStorming) {
        switch (nodeType) {
            case MINI_ANTENNA:
                return isStorming ? ConfigData.miniAntennaRangeStorm : ConfigData.miniAntennaRange;
            case ANTENNA:
                return isStorming ? ConfigData.antennaRangeStorm : ConfigData.antennaRange;
        }

        throw new IllegalStateException("Invalid NodeType encountered while trying to calculate transmission range");
    }

    /**
     * Invoked once per server-tick, each tick the delayed messages are informed so that they can reduce their
     * counter, once their counter is complete they will dispatch themselves.
     *
     * @param event the FML event
     */
    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            final Iterator<DelayedMessage> it = messageQueue.iterator();
            while (it.hasNext()) {
                DelayedMessage message = it.next();
                if (message.tick()) {
                    it.remove();
                }
            }
        }
    }

    /**
     * A container class for a {@link com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage}, tracking how
     * many ticks remain before the message payload should be delivered to the receiving tower
     *
     * @author theoriginalbit
     */
    private static final class DelayedMessage {

        private final IBitNetNode receiver;
        private final BitNetMessage payload;
        private int sendDelay;

        public DelayedMessage(IBitNetNode tower, BitNetMessage message, double distance) {
            receiver = tower;
            payload = message;
            // calculate the cost to send this message
            sendDelay = (int) (Math.ceil(distance / 100) * ConfigData.bitNetMessageDelay);
            LogUtils.debug(String.format("Created delayed message, delay=%.2d payload=%s", sendDelay, payload));
        }

        /**
         * Invoked once per server-tick so the delay tracker can count down and determine when the message
         * should be sent
         *
         * @return whether the message was dispatched
         */
        public boolean tick() {
            if (--sendDelay <= 0) {
                LogUtils.debug(String.format("Delay expired, sending message %s", payload));
                receiver.receive(payload);
                return true;
            }
            return false;
        }

    }

}
