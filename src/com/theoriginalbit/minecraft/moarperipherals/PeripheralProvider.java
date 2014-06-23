package com.theoriginalbit.minecraft.moarperipherals;

import com.theoriginalbit.minecraft.moarperipherals.generic.TilePeripheral;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

public class PeripheralProvider implements IPeripheralProvider {

	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TilePeripheral) {
			return (IPeripheral) tile;
		}
		return null;
	}

}
