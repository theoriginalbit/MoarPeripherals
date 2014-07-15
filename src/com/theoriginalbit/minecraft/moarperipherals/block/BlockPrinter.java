package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePrinter;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockPrinter extends BlockRotatable {

	public BlockPrinter() {
		super(Settings.blockIdPrinter, Material.rock, "printer");
		setRotationMode(RotationMode.FOUR);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePrinter();
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public final boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		if (side == ForgeDirection.DOWN) {
			return true;
		}
		int meta = world.getBlockMetadata(x, y, z) + 2;
		switch (side) {
			case NORTH:
				return meta == 5;
			case SOUTH:
				return meta == 3;
			case EAST:
				return meta == 4;
			case WEST:
				return meta == 2;
			default:
				return false;
		}
	}
}