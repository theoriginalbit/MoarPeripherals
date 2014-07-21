package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.ITooltipInformer;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;

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
public class ItemInkCartridge extends ItemGeneric implements ITooltipInformer {

    private static final int emptyColorIndex = 16;
    private static final String TOOLTIP_LOCALIZATION = "moarperipherals.tooltip.inkCartridge.";

    private final Icon[] icons;

    public ItemInkCartridge() {
        super(Settings.itemIdInkCartridge, "inkCartridge");

        setMaxStackSize(1);

        icons = new Icon[17];
    }

    public static boolean isCartridgeEmpty(ItemStack stack) {
        return getInkColor(stack) == emptyColorIndex;
    }

    public static int getInkColor(ItemStack stack) {
        NBTTagCompound tag = getItemTag(stack);
        if (tag.hasKey("inkColor")) {
            return tag.getInteger("inkColor");
        }
        // wasn't a colour, return the index of the empty texture
        return emptyColorIndex;
    }

    public static String getInkPercent(ItemStack stack) {
        NBTTagCompound tag = getItemTag(stack);
        if (tag.hasKey("inkLevel")) {
            float level = tag.getFloat("inkLevel");
            return (int) (level / FluidContainerRegistry.BUCKET_VOLUME * 100) + "%";
        }
        return null;
    }

    private static NBTTagCompound getItemTag(ItemStack stack) {
        if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound("tag");
        return stack.stackTagCompound;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        icons[emptyColorIndex] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeEmpty");

        for (int i = 0; i < icons.length - 1; ++i) {
            icons[i] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridge" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconIndex(ItemStack stack) {
        int inkColor = getInkColor(stack);
        return icons[inkColor];
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getSubItems(int itemId, CreativeTabs creativeTab, List itemList) {
        // add the empty ink cartridge
//		itemList.add(emptyCartridge);

        // add the ink cartridges from the list we generated before
//		for (int i = 0; i < inkCartridges.length; ++i) {
//			itemList.add(inkCartridges[i]);
//		}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformativeTooltip(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {
        // get the ink colour
        int inkColor = getInkColor(stack);
        // if it is not empty
        if (inkColor != emptyColorIndex) {
            String contents = StatCollector.translateToLocal(TOOLTIP_LOCALIZATION + "contents") + ": ";
            // translate the colour
            String inkName = StatCollector.translateToLocal(TOOLTIP_LOCALIZATION + "ink." + inkColor);
            list.add(contents + inkName);
            // there was a colour, so also get the percent
            String percent = getInkPercent(stack);
            if (percent != null) {
                String level = StatCollector.translateToLocal(TOOLTIP_LOCALIZATION + "level");
                list.add(level + ": " + percent);
            }
        } else {
            list.add("Empty");
        }
    }

}