/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.hook;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Gives the ability for items to easily add extended tooltip information to items. This extended tooltip information
 * displays when the user has pressed shift while hovering over the item. otherwise it'll tell the user to press shift.
 */
public interface ITooltipHook {
    /**
     * Invoked when the user has pressed shift over the item.
     */
    @SideOnly(Side.CLIENT)
    void addToTooltip(ItemStack stack, EntityPlayer player, List<String> list, boolean bool);
}