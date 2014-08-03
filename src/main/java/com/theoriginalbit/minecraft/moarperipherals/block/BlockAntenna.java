package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.block.base.BlockMPBase;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileAntennaController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class BlockAntenna extends BlockMPBase {

    public BlockAntenna() {
        this(Settings.blockIdAntenna, "antenna");
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
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
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
                    ((TileAntennaController) tile).blockAdded();
                } else {
                    ((TileAntennaController) tile).blockRemoved();
                }
                return true;
            }
        }
        return false;
    }

}
