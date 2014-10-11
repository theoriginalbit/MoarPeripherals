/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.block;

import com.theoriginalbit.minecraft.moarperipherals.common.block.abstracts.BlockMoarP;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TileAntennaController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockAntenna extends BlockMoarP {

    public BlockAntenna() {
        this(ConfigHandler.blockIdAntenna, "antenna");
    }

    public BlockAntenna(int id, String name) {
        super(id, Material.iron, name);
        setStepSound(soundMetalFootstep);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        super.registerIcons(registry);
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
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
        return side == 0 || side == 1;
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return isBlockSolid(world, x, y, z, side.ordinal());
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
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        // search for the controller and notify of destruction
        for (int i = 1; i < 16; ++i) {
            if (notifyController(world, x, y - i, z, false)) {
                break;
            }
        }
        super.breakBlock(world, x, y, z, id, meta);
    }

    private boolean notifyController(World world, int x, int y, int z, boolean added) {
        if (!world.isRemote) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
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
