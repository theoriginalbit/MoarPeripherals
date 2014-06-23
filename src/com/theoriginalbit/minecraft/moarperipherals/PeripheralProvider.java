package com.theoriginalbit.minecraft.moarperipherals;

import com.theoriginalbit.minecraft.moarperipherals.tile.TilePeripheral;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

public final class PeripheralProvider implements IPeripheralProvider {

	@Override
	public final IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TilePeripheral) {
			return (IPeripheral) tile;
		}
		return null;
	}

}
