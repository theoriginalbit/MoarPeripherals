/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.bitnet;

import com.sun.istack.internal.NotNull;
import com.theoriginalbit.moarperipherals.api.bitnet.node.IBitNetPortal;
import com.theoriginalbit.moarperipherals.api.bitnet.node.IBitNetRelay;
import net.minecraft.util.Vec3;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IBitNetWorld {
    /**
     * Adds a {@link IBitNetPortal} to the network so that messages can be sent through them. This should be used
     * when the portal is created. Once a portal is registered it need not do anything in order for a message to be
     * sent through it, this process is handled by the logic of {@link #transmit(IBitNetRelay, BitNetMessage)} and
     * {@link #teleport(Vec3, double, BitNetMessage)}
     *
     * @param portal the portal to add to the network
     * @see #transmit(IBitNetRelay, BitNetMessage)
     * @see #teleport(Vec3, double, BitNetMessage)
     */
    void addPortal(@NotNull IBitNetPortal portal);

    /**
     * Removes a {@link IBitNetPortal} from the network so that messages are no longer sent through them, this should
     * only be used when the portal is destroyed, if the portal is simply deactivated you should use {@link
     * IBitNetPortal#portalActive()}
     *
     * @param portal the portal to remove
     */
    void removePortal(@NotNull IBitNetPortal portal);

    /**
     * Adds a {@link IBitNetRelay} to the network so that it may receive messages that have been sent. Simply
     * registering a relay will not give it the messages, the relay must also open channels which the messages are
     * sent over
     *
     * @param relay the relay to add
     * @see #openChannel(IBitNetRelay, int)
     */
    void addRelay(@NotNull IBitNetRelay relay);

    /**
     * Removes a {@link IBitNetRelay} from the network so that it no longer receive messages that have been sent
     *
     * @param relay the relay to remove
     */
    void removeRelay(@NotNull IBitNetRelay relay);

    /**
     * Gets whether the channel is open on the supplied relay
     *
     * @param relay   the relay to check
     * @param channel the channel to check
     * @return if the channel is open
     */
    boolean isChannelOpen(@NotNull IBitNetRelay relay, int channel);

    /**
     * Notifies the network that the relay has the supplied channel open and can receive messages on that channel.
     * Note: the relay must be registered with {@link #addRelay(IBitNetRelay)} to the network before opening channels
     *
     * @param relay   the relay that the channel is open for
     * @param channel the channel that is open
     * @return whether the channel was opened
     * @see #addRelay(IBitNetRelay)
     */
    boolean openChannel(@NotNull IBitNetRelay relay, int channel);

    /**
     * Notifies the network that the relay has closed the supplied channel and no longer wants to receive messages on
     * that channel.
     *
     * @param relay   the relay that the channel has closed on
     * @param channel the channel that has closed
     * @return whether the channel was closed
     */
    boolean closeChannel(@NotNull IBitNetRelay relay, int channel);

    /**
     * Sends a message over the network with the supplied distance. The distance is typically calculated one
     *
     * @param sender  where the message is being sent from
     * @param payload the message being sent
     */
    void transmit(@NotNull IBitNetRelay sender, @NotNull BitNetMessage payload);

    /**
     * Sends a message over the network which has come from another world through the supplied portal. The portal's
     * position in this world is given so that distance calculations can occur. How much further the message can
     * travel is also given so that the message doesn't send too far once leaving a portal. This remaining distance
     * is based off how far the message could be sent in the first place (i.e. the {@link com.theoriginalbit
     * .moarperipherals.api.bitnet.node.IBitNetRelay.RelayType} of the relay as well as the weather in the sending
     * world) minus how far it had to travel to enter the portal.
     *
     * @param pos               the position of the portal in this world
     * @param distanceRemaining how far the message can be sent
     * @param payload           the message being sent
     */
    void teleport(Vec3 pos, double distanceRemaining, @NotNull BitNetMessage payload);

    /**
     * Used internally to inform the networks that they should send any messages that are in the queue that have
     * had their delay expire
     */
    void tick();
}
