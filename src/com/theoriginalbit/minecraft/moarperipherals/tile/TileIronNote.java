package com.theoriginalbit.minecraft.moarperipherals.tile;

import openperipheral.api.Ignore;

import com.google.common.base.Preconditions;
import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

@Ignore
public class TileIronNote extends TilePeripheral {
	private static final String TYPE = "iron_note";
	private static final int MIN_INST = 0;
	private static final int MAX_INST = 4;
	private static final int MIN_PITCH = 0;
	private static final int MAX_PITCH = 24;
	private static final int MAX_TICK = 5; // this is 5 notes per tick, allowing for 5 note chords
	private int ticker = 0;

	public TileIronNote() {
		super(TYPE);
	}
	
	@LuaFunction
	public boolean playNote(int instrument, int pitch) throws Exception {
		Preconditions.checkArgument(instrument >= MIN_INST && instrument <= MAX_INST, "Expected instrument 0-4");
		Preconditions.checkArgument(pitch >= MIN_PITCH && pitch <= MAX_PITCH, "Expected pitch 0-24");
		Preconditions.checkArgument(ticker++ <= MAX_TICK, "Too many notes (over " + MAX_TICK + " per tick)");
		
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, Settings.blockIronNoteID, instrument, pitch);
		return true;
	}
	
	@Override
	public void updateEntity() {
		ticker = 0;
	}
}