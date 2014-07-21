package com.theoriginalbit.minecraft.moarperipherals.itemblock;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.tile.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.utils.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

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
public class ItemBlockPairable extends ItemBlock {

    public ItemBlockPairable(int id, String name) {
        super(id);
        setMaxStackSize(1);
        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + '.' + name);
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
            ChatUtils.sendChatToPlayer(player.username, Constants.CHAT.CHAT_PAIRED.getFormattedLocalised(x, y, z));
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
