/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.hook;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Gives the ability for a {@link net.minecraft.tileentity.TileEntity} to provide custom item drops when the block is
 * broken.
 */
public interface IBlockDropHook {
    /**
     * Invoked when the block is broken to determine what should be dropped when it is broken.
     *
     * @param dropsList the list of items to be dropped
     */
    void addDrops(List<ItemStack> dropsList);
}
