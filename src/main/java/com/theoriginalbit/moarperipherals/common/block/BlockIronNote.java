/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.block.base.BlockMoarP;
import com.theoriginalbit.moarperipherals.reference.Settings;
import com.theoriginalbit.moarperipherals.common.tile.TileIronNote;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockIronNote extends BlockMoarP {

    public BlockIronNote() {
        super(Material.iron, "ironnote");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileIronNote();
    }

}