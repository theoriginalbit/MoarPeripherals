/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.item;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.api.ITooltipInformer;
import com.theoriginalbit.moarperipherals.reference.Constants;
import com.theoriginalbit.moarperipherals.reference.ModInfo;
import com.theoriginalbit.moarperipherals.utils.KeyboardUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMPBase extends Item {

    private final String name;

    public ItemMPBase(String itemName) {
        super();
        name = itemName;

        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + ":" + name);
        setCreativeTab(MoarPeripherals.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
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

}