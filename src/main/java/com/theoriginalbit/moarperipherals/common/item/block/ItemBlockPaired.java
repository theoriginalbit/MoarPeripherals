/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.item.block;

import com.theoriginalbit.moarperipherals.api.hook.IPairedDeviceHook;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.util.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockPaired extends ItemBlock {

    public ItemBlockPaired(Block block) {
        super(block);
        setMaxStackSize(1);
        setUnlocalizedName(block.getUnlocalizedName());
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        if (super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta)) {
            IPairedDeviceHook tile = WorldUtil.getTileEntity(world, x, y, z, IPairedDeviceHook.class);
            setupTileEntity(stack, tile);
            return true;
        }
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        if (WorldUtil.isServer(world) && !player.isSneaking() && PairedUtil.isComputer(tile)) {
            if (!PairedUtil.isPairAllowed(tile)) {
                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + Constants.CHAT.CHAT_REJECTED_PAIR.getLocalised()));
                return true;
            }

            int instanceId = PairedUtil.getInstanceId(tile);

            if (!PairedUtil.isOn(PairedUtil.getInstance(instanceId))) {
                player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + Constants.CHAT.CHAT_PAIR_POWER_REQUIRED.getLocalised()));
                return true;
            }

            // It is allowed to pair, and the computer is on
            LogUtil.debug("Computer at %d, %d, %d has been right-clicked, getting computer information", x, y, z);
            String desc = PairedUtil.getDescription(instanceId);
            ChunkCoordinates coordinates = PairedUtil.getInstanceLocation(instanceId);

            // Inform the user of the connection
            ChatUtil.sendChatToPlayer(player.getDisplayName(), Constants.CHAT.CHAT_PAIRED.getFormattedLocalised(desc));

            // Save the connection info in the ItemStack
            NBTUtil.setInteger(stack, "instanceId", instanceId);
            return true;
        }
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean details) {
        if (KeyboardUtil.isShiftKeyDown()) {
            if (containsTargetInfo(stack)) {
                int instanceId = NBTUtil.getInteger(stack, "instanceId");
                String desc = PairedUtil.getDescription(instanceId);
                list.add(EnumChatFormatting.GRAY + Constants.TOOLTIPS.PAIRED.getFormattedLocalised(desc));
            } else {
                list.add(EnumChatFormatting.ITALIC + Constants.TOOLTIPS.NOT_PAIRED.getLocalised());
            }
        } else {
            list.add(Constants.TOOLTIPS.SHIFT_INFO.getLocalised());
        }
    }

    private boolean setupTileEntity(ItemStack stack, IPairedDeviceHook tile) {
        return containsTargetInfo(stack) && tile.configureTargetFromNbt(NBTUtil.getItemTag(stack));
    }

    private boolean containsTargetInfo(ItemStack stack) {
        return NBTUtil.hasTag(stack, "instanceId");
    }

}
