package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.ITooltipInformer;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.utils.KeyboardUtils;
import com.theoriginalbit.minecraft.moarperipherals.utils.NEIUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class ItemMPBase extends Item {

    private final String name;

    public ItemMPBase(int itemId, String itemName) {
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
    public final ItemMPBase hideFromNEI() {
        NEIUtils.hideFromNEI(itemID);
        return this;
    }

    /**
     * Removes the block from the creative menu, by default it is added to the MoarPeripherals creative tab.
     */
    public final ItemMPBase hideFromCreative() {
        setCreativeTab(null);
        return this;
    }

}