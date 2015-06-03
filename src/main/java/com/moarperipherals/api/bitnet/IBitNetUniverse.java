/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.moarperipherals.api.bitnet;

import net.minecraft.world.World;

/**
 * The state of the entire BitNetwork, this maintains instances of networks for each world in the game
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public interface IBitNetUniverse {
    /**
     * Gets the {@link IBitNetWorld} (network instance) for the supplied world.
     *
     * @param world the world to get the network for
     * @return the network
     */
    IBitNetWorld getBitNetWorld(World world);
}
