/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.bitnet;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * A node within the BitNet network. Nodes can be registered to the {@link IBitNetRegistry} to be notified when a
 * {@link BitNetMessage} has been sent, a node may also send messages over the network; these actions are independent
 * of each other, an unregistered node may send messages.
 * <p/>
 * There are several kinds of node types, the difference between these is the transmit range.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IBitNetNode {
    /**
     * Gets the world that the node resides within.
     *
     * @return the world
     */
    World getWorld();

    /**
     * Gets the location that the node occupies in the world. This does not need to be exact, but just an approximate.
     * A central location of the multi-block nodes would be ideal.
     *
     * @return the position within the world
     */
    Vec3 getPosition();

    /**
     * Gets the type of this node on the network in order to determine range
     *
     * @return the node type
     */
    NodeType getNodeType();

    /**
     * Invoked when this node was in range of a message that was dispatched
     *
     * @param payload the message that was sent.
     */
    void receive(BitNetMessage payload);

    /**
     * The type of node this node represents on the network
     */
    enum NodeType {
        MINI_ANTENNA, ANTENNA
    }
}
