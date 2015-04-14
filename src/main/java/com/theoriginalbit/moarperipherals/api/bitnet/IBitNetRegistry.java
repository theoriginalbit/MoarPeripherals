/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.bitnet;

import com.sun.istack.internal.NotNull;

/**
 * The BitNet registry allows Nodes to register to receive messages, and also allows them to send
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IBitNetRegistry {
    /**
     * Adds a {@link IBitNetNode} to the network so that it may receive {@link BitNetMessage}s that have been
     * transmitted as long as the node is within transmit range of the node that has sent the message.
     *
     * @param node the node to add to the network
     */
    void add(@NotNull IBitNetNode node);

    /**
     * Removes a {@link IBitNetNode} from the network so that it may no longer receive {@link BitNetMessage}s, it is
     * however possible for a node to continue to send messages across the network.
     *
     * @param node the node to remove from the network
     */
    void remove(@NotNull IBitNetNode node);

    /**
     * Sends a {@link BitNetMessage} across the network. Any nodes that have been registered to the network will
     * receive this message as long as it is within range of the sending node. In order to replicate real networks,
     * the BitNet network will delay the dispatch of a {@link BitNetMessage} to each receiving node for the required
     * amount of time that it would take to travel that distance, basically don't rely on message sending/receiving
     * to be instantaneous like the Rednet system in ComputerCraft. The time that a message takes to dispatch is
     * configurable and therefore may vary, but the default is 3 ticks per 100 blocks (rounded up), therefore the
     * minimum delay would be 3 ticks.
     *
     * @param sender  the node that has sent the message
     * @param payload the message to be sent
     */
    void transmit(@NotNull IBitNetNode sender, @NotNull BitNetMessage payload);
}
