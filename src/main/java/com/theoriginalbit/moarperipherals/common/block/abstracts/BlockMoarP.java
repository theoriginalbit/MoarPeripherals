/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.block.abstracts;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.api.event.IBlockEventHandler;
import com.theoriginalbit.moarperipherals.api.tile.IHasGui;
import com.theoriginalbit.moarperipherals.api.tile.IHasSpecialDrops;
import com.theoriginalbit.moarperipherals.api.tile.IPairedDevice;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockMoarP extends BlockContainer {

    protected final String blockName;
    protected final IIcon[] icons = new IIcon[6];

    public BlockMoarP(String name) {
        this(Material.rock, name);
    }

    public BlockMoarP(Material material, String name) {
        super(material);
        blockName = name;
        setHardness(5.0f);
        setResistance(10.0f);
        setCreativeTab(MoarPeripherals.creativeTab);
        setBlockName(ModInfo.RESOURCE_DOMAIN + ':' + blockName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        IIcon icon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + blockName);
        for (int i = 0; i < icons.length; ++i) {
            icons[i] = icon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[side];
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof IBlockEventHandler) {
            ((IBlockEventHandler) tile).blockPlaced();
        }

        if (tile != null && entity instanceof EntityPlayer) {
            ((TileMoarP) tile).setOwner(((EntityPlayer) entity).getDisplayName());
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof IHasGui) {
            player.openGui(MoarPeripherals.instance, ((IHasGui) tile).getGuiId().ordinal(), world, x, y, z);
            return true;
        }

        return tile instanceof IBlockEventHandler && ((IBlockEventHandler) tile).blockActivated(player, side, hitX,
                hitY, hitZ);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof IBlockEventHandler) {
            ((IBlockEventHandler) tile).blockBroken(x, y, z);
        }

        if (tile instanceof IInventory) {
            InventoryUtils.explodeInventory((IInventory) tile, world, x, y, z);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean harvest) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        final List<ItemStack> tileDrops = Lists.newArrayList();

        if (tile instanceof IPairedDevice) {
            ItemStack drop = ((IPairedDevice) tile).getPairedDrop();
            tileDrops.add(drop);
        }
        if (tile instanceof IHasSpecialDrops) {
            ((IHasSpecialDrops) tile).addDrops(tileDrops);
        }

        for (ItemStack stack : tileDrops) {
            dropBlockAsItem(world, x, y, z, stack);
        }

        return super.removedByPlayer(world, player, x, y, z, harvest);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof IBlockEventHandler) {
            ((IBlockEventHandler) tile).neighborChanged();
        }

        super.onNeighborBlockChange(world, x, y, z, block);
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return false;
    }

}
