/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.block.abstracts.BlockMoarP;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAntenna extends BlockMoarP {

    public BlockAntenna() {
        this("antenna");
    }

    public BlockAntenna(String name) {
        super(Material.iron, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        super.registerBlockIcons(registry);
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":antennaCable");
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":antennaCable");
    }

    @Override
    public int getRenderType() {
        return Constants.RENDER_ID.ANTENNA;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return null;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        // search for the controller and notify of placement
        for (int i = 1; i < 16; ++i) {
            if (notifyController(world, x, y - i, z, true)) {
                break;
            }
        }
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        // search for the controller and notify of destruction
        for (int i = 1; i < 16; ++i) {
            if (notifyController(world, x, y - i, z, false)) {
                break;
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    private boolean notifyController(World world, int x, int y, int z, boolean added) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile != null && tile instanceof TileAntennaController) {
                if (added) {
                    ((TileAntennaController) tile).onBlockAdded();
                } else {
                    ((TileAntennaController) tile).onBlockRemoved();
                }
                return true;
            }
        }
        return false;
    }

}
