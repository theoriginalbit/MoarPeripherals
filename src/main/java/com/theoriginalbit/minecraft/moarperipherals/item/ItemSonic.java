package com.theoriginalbit.minecraft.moarperipherals.item;

import buildcraft.api.tools.IToolWrench;
import com.google.common.collect.ImmutableSet;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.BlockNotifyFlags;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

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
public class ItemSonic extends ItemMPBase implements IToolWrench {

    private static final ImmutableSet<Class<? extends Block>> blacklist = ImmutableSet.of(BlockLever.class, BlockButton.class, BlockBed.class, BlockTorch.class);
    private Icon iconTen;
    private Icon iconEleven;

    public ItemSonic() {
        super(Settings.itemIdSonic, "sonic");

        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        iconTen = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":sonic10");
        iconEleven = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":sonic11");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int meta) {
        return meta == 0 ? iconTen : iconEleven;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(int id, CreativeTabs tab, List list) {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int blockId = world.getBlockId(x, y, z);
        Block block = Block.blocksList[blockId];

        if (block == null) {
            return false;
        }

        // make sure it's not wood, the sonic doesn't work on wood
        if (Block.blocksList[blockId].blockMaterial == Material.wood) {
            String message = EnumChatFormatting.RED + Constants.CHAT.SONIC_WOOD.getLocalised();
            player.sendChatToPlayer(new ChatMessageComponent().addText(message));
            return true;
        }

        // make sure it's not blacklisted
        if (blacklist.contains(block.getClass())) {
            return false;
        }

        // rotate furnace
        if (block instanceof BlockFurnace && !(side == 0 || side == 1)) {
            if (player.isSneaking()) {
                side = ForgeDirection.getOrientation(side).getOpposite().ordinal();
            }
            world.setBlockMetadataWithNotify(x, y, z, side, BlockNotifyFlags.SEND_TO_CLIENTS);
            player.swingItem();
            return !world.isRemote;
        }

        // rotate stairs, if I left this to vanilla it rotates weird
        if (block instanceof BlockStairs) {
            return rotateStairs(stack, player, world, x, y, z, side);
        }

        // attempt to rotate anything else that supports rotations that I don't need to customise
        return rotate(block, player, world, x, y, z, side);
    }

    private boolean rotate(Block block, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
            player.swingItem();
            return !world.isRemote;
        }
        return false;
    }

    private boolean rotateStairs(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        // get the current meta, trimming extra bits if there's any
        int meta = world.getBlockMetadata(x, y, z) & 4;
        // toggle where the 'base' is, top or bottom
        if (player.isSneaking()) {
            meta ^= 4;
        }
        // rotation
        switch (side) {
            case 2:
                meta |= 2;
                break;
            case 3:
                meta |= 3;
                break;
            case 4:
                /* no meta change */
                break;
            case 5:
                meta |= 1;
                break;
        }
        // update new meta
        world.setBlockMetadataWithNotify(x, y, z, meta, BlockNotifyFlags.SEND_TO_CLIENTS);
        stack.damageItem(1, player);
        return true;
    }

    /*
     * BC wrench compatibility
     */

    @Override
    public boolean canWrench(EntityPlayer player, int x, int y, int z) {
        return true;
    }

    @Override
    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
        player.swingItem();
    }

}
