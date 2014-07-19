package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import openperipheral.api.Ignore;

@Ignore
public class TilePrinter extends TilePeripheral {

    private static final String TYPE = "advanced_printer";

    public TilePrinter() {
        super(TYPE);
    }

}