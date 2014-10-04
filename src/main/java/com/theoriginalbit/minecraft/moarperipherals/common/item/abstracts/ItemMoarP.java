/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.item.abstracts;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.api.ITooltipInformer;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.KeyboardUtils;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.NEIUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMoarP extends Item {

    private final String name;

    public ItemMoarP(int itemId, String itemName) {
        super(itemId - 256);
        name = itemName;

        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + "." + name);
        setCreativeTab(MoarPeripherals.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        itemIcon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        Item item = stack.getItem();
        if (item instanceof ITooltipInformer) {
            if (KeyboardUtils.isShiftKeyDown()) {
                ((ITooltipInformer) item).addInformativeTooltip(stack, player, list, bool);
            } else {
                list.add(Constants.TOOLTIPS.SHIFT_INFO.getLocalised());
            }
        }
    }

    /**
     * Stops the block from appearing in Not Enough Items
     */
    public final ItemMoarP hideFromNEI() {
        NEIUtils.hideFromNEI(itemID);
        return this;
    }

    /**
     * Removes the block from the creative menu, by default it is added to the MoarPeripherals creative tab.
     */
    public final ItemMoarP hideFromCreative() {
        setCreativeTab(null);
        return this;
    }

}