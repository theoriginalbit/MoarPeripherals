/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Contract interface for block-events.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IBlockEventHandler {
    /**
     * Invoked when the host block is placed.
     *
     * @see Block#onBlockAdded(World, int, int, int)
     */
    void blockPlaced();

    /**
     * Invoked when the host block is broken.
     *
     * @see Block#breakBlock(World, int, int, int, Block, int)
     */
    void blockBroken(int x, int y, int z);

    /**
     * Invoked when a neighbor of the host block has changed.
     *
     * @see Block#onNeighborBlockChange(World, int, int, int, Block)
     */
    void neighborChanged();

    /**
     * Invoked when the host block is right-clicked.
     *
     * @see Block#onBlockActivated(World, int, int, int, EntityPlayer, int, float, float, float)
     */
    boolean blockActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ);
}
