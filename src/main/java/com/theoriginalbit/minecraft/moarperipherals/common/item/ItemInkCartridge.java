/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.item;

import com.theoriginalbit.minecraft.moarperipherals.api.ITooltipInformer;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.item.abstracts.ItemMoarP;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.List;

public class ItemInkCartridge extends ItemMoarP implements ITooltipInformer {

    private static final int emptyColorIndex = 16;

    private Icon iconInkC;
    private Icon iconInkM;
    private Icon iconInkY;
    private Icon iconInkK;
    private Icon iconInkE;

    public ItemInkCartridge() {
        super(ConfigHandler.itemIdInkCartridge, "inkCartridge");
        setMaxStackSize(1);
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
        iconInkC = registerIcon(registry, "inkCartridgeC");
        iconInkM = registerIcon(registry, "inkCartridgeM");
        iconInkY = registerIcon(registry, "inkCartridgeY");
        iconInkK = registerIcon(registry, "inkCartridgeK");
        iconInkE = registerIcon(registry, "inkCartridgeE");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconIndex(ItemStack stack) {
        int inkColor = getInkColor(stack);
        switch (inkColor) {
            case 0: return iconInkC;
            case 1: return iconInkM;
            case 2: return iconInkY;
            case 3: return iconInkK;
            default: return iconInkE;
        }
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
            String contents = Constants.TOOLTIPS.INK_CONTENTS.getLocalised() + ": ";
            // TODO: translate the colour name
            String inkName = "unknown";
            list.add(contents + inkName);
            // there was a colour, so also get the percent
            String percent = getInkPercent(stack);
            if (percent != null) {
                String level = Constants.TOOLTIPS.INK_LEVEL.getLocalised();
                list.add(level + ": " + percent);
            }
        } else {
            list.add("Empty");
        }
    }

    private Icon registerIcon(IconRegister registry, String name) {
        return registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ':' + name);
    }

}