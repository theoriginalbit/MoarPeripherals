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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

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
    public boolean isNormalCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return null;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side) {
        return side == 0 || side == 1;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return isBlockSolid(world, x, y, z, side.ordinal());
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        double border = 0.1625d;
        return AxisAlignedBB.getBoundingBox(x + border, y, z + border, x + 1 - border, y + 1, z + 1 - border);
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 1) {
            double border = 0.1;
            return AxisAlignedBB.getBoundingBox(x + border, y, z + border, x + 1 - border, y + 1, z + 1 - border);
        }
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            // prevent fall damage
            player.fallDistance = 0f;
            // make it a slow decent
            player.motionY = Math.max(player.motionY, -0.15d);
        }
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
