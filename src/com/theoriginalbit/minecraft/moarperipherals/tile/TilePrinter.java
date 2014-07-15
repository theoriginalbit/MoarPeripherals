package com.theoriginalbit.minecraft.moarperipherals.tile;

import openperipheral.api.Ignore;

import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;

@Ignore
public class TilePrinter extends TilePeripheral {
	
	private static final String TYPE = "advanced_printer";
	
	public TilePrinter() {
		super(TYPE);
	}

}
