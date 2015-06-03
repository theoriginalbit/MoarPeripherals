/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.moarperipherals.api.bitnet;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * The representation of an entity on a BitNetwork
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public interface IBitNetNode {
    /**
     * Gets the world that this node resides within
     *
     * @return the world
     */
    World getWorld();

    /**
     * Gets the position this node is within the world. If the node is multi-block a central location (or the
     * location of the antenna) would be ideal
     *
     * @return the position of the node
     */
    Vec3 getPosition();
}
