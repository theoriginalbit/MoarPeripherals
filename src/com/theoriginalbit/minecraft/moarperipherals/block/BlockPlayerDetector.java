package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePlayerDetector;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlayerDetector extends BlockGeneric {
	
	public BlockPlayerDetector() {
		super(Settings.blockPlayerDetectorID, Material.rock, "playerdetector");
	}
	
	@Override
	protected void init() {
		setStepSound(Block.soundStoneFootstep);
		GameRegistry.registerBlock(this, getUnlocalizedName());
		GameRegistry.registerTileEntity(TilePlayerDetector.class, "MoarPeripherals Player Detector");
		GameRegistry.addRecipe(new ItemStack(this), "SBS", "BRB", "SBS", 'S', Block.stone, 'B', Block.stoneButton, 'R', Item.redstone);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePlayerDetector();
	}
}