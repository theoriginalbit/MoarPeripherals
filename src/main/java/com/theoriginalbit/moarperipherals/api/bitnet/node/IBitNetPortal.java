/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.bitnet.node;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * The representation of a portal on the network.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IBitNetPortal extends IBitNetNode {
    /**
     * Gets whether the portal is active and a message is able to transmit through it
     *
     * @return whther the portal is active
     */
    boolean portalActive();

    /**
     * Gets the world that the portal connects to so that the message can be sent over that network
     *
     * @return the world of the connection
     */
    World getConnectionWorld();

    /**
     * Gets the position in the world of the portal's connection so that the message can be sent over that network
     *
     * @return the position of the connection
     */
    Vec3 getConnectionPosition();
}
