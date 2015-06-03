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
package com.moarperipherals.item;

import buildcraft.api.tools.IToolWrench;
import com.google.common.collect.ImmutableSet;
import com.moarperipherals.Constants;
import com.moarperipherals.ModInfo;
import com.moarperipherals.integration.Mods;
import com.moarperipherals.util.BlockNotifyFlags;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

@Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = Mods.BUILDCRAFT_CORE)
public class ItemSonic extends ItemMoarP implements IToolWrench {
    private static final ImmutableSet<Class<? extends Block>> blacklist = ImmutableSet.of(BlockLever.class, BlockButton.class, BlockBed.class, BlockTorch.class);
    private IIcon iconTen;
    private IIcon iconEleven;

    public ItemSonic() {
        super("sonic");

        setHasSubtypes(true);
        setMaxStackSize(1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        iconTen = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":sonic10");
        iconEleven = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":sonic11");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return meta == 0 ? iconTen : iconEleven;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);

        if (block == null) {
            return false;
        }

        // make sure it's not wood, the sonic doesn't work on wood
        if (block.getMaterial() == Material.wood) {
            String message = EnumChatFormatting.RED + Constants.CHAT.SONIC_WOOD.getLocalised();
            player.addChatComponentMessage(new ChatComponentText(message));
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

    @Override
    @Optional.Method(modid = Mods.BUILDCRAFT_CORE)
    public boolean canWrench(EntityPlayer player, int x, int y, int z) {
        return true;
    }

    @Override
    @Optional.Method(modid = Mods.BUILDCRAFT_CORE)
    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
        player.swingItem();
    }

}
