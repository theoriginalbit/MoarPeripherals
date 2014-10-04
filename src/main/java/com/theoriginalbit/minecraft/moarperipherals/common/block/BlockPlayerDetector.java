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
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TilePlayerDetector;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlayerDetector extends BlockMoarP {

    public BlockPlayerDetector() {
        super(ConfigHandler.blockIdPlayerDetector, "playerdetector");
        setStepSound(soundStoneFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TilePlayerDetector();
    }

}