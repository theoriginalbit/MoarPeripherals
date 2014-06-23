package com.theoriginalbit.minecraft.moarperipherals.playerdetector;

import com.theoriginalbit.minecraft.moarperipherals.generic.BlockGeneric;
import com.theoriginalbit.minecraft.moarperipherals.playerdetector.TilePlayerDetector;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlayerDetector extends BlockGeneric {
	
	public BlockPlayerDetector() {
		super(Settings.blockPlayerDetectorID, Material.rock, "playerdetector");
		setStepSound(Block.soundStoneFootstep);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePlayerDetector();
	}
	
}
