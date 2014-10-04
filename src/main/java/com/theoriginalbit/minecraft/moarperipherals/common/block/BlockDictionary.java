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
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TileDictionary;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDictionary extends BlockMoarP {

    public BlockDictionary() {
        super(ConfigHandler.blockIdDictionary, Material.iron, "itemDictionary");
        setStepSound(soundMetalFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileDictionary();
    }
}
