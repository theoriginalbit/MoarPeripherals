/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.itemblock;

import com.theoriginalbit.moarperipherals.api.tile.IPairableDevice;
import com.theoriginalbit.moarperipherals.reference.ModInfo;
import com.theoriginalbit.moarperipherals.reference.Constants;
import com.theoriginalbit.moarperipherals.utils.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockPairable extends ItemBlock {

    public ItemBlockPairable(String name) {
        super();
        setMaxStackSize(1);
        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + ':' + name);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        if (super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta)) {
            IPairableDevice tile = getPairable(world, x, y, z);
            setupTileEntity(stack, tile);
            return true;
        }
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        // if this is the server, and the player wasn't sneaking
        if (WorldUtils.isServer(world) && !player.isSneaking() && ComputerUtils.getTileComputerBase(world, x, y, z) != null) {
            NBTUtils.setInteger(stack, Constants.NBT.TARGET_X, x);
            NBTUtils.setInteger(stack, Constants.NBT.TARGET_Y, y);
            NBTUtils.setInteger(stack, Constants.NBT.TARGET_Z, z);
            ChatUtils.sendChatToPlayer(player.getDisplayName(), Constants.CHAT.CHAT_PAIRED.getFormattedLocalised(x, y, z));
            return true;
        }
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        if (KeyboardUtils.isShiftKeyDown()) {
            if (containsTargetInfo(stack)) {
                int x = NBTUtils.getInteger(stack, Constants.NBT.TARGET_X);
                int y = NBTUtils.getInteger(stack, Constants.NBT.TARGET_Y);
                int z = NBTUtils.getInteger(stack, Constants.NBT.TARGET_Z);

                list.add(EnumChatFormatting.ITALIC + Constants.TOOLTIPS.PAIRED.getLocalised());
                list.add(EnumChatFormatting.ITALIC + "  X: " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + x);
                list.add(EnumChatFormatting.ITALIC + "  Y: " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + y);
                list.add(EnumChatFormatting.ITALIC + "  Z: " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + z);
            } else {
                list.add(EnumChatFormatting.ITALIC + Constants.TOOLTIPS.NOT_PAIRED.getLocalised());
            }
        } else {
            list.add(Constants.TOOLTIPS.SHIFT_INFO.getLocalised());
        }
    }

    private IPairableDevice getPairable(World world, int x, int y, int z) {
        return WorldUtils.getTileEntity(world, x, y, z, IPairableDevice.class);
    }

    private boolean setupTileEntity(ItemStack stack, IPairableDevice tile) {
        return containsTargetInfo(stack) && tile.configureTargetFromNbt(NBTUtils.getItemTag(stack));
    }

    private boolean containsTargetInfo(ItemStack stack) {
        return NBTUtils.hasTag(stack, Constants.NBT.TARGET_X) && NBTUtils.hasTag(stack, Constants.NBT.TARGET_Y) && NBTUtils.hasTag(stack, Constants.NBT.TARGET_Z);
    }

}
