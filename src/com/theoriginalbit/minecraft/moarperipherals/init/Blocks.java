package com.theoriginalbit.minecraft.moarperipherals.init;

import com.theoriginalbit.minecraft.moarperipherals.block.*;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

public final class Blocks {
	public static BlockGeneric blockChatBox;
	public static BlockGeneric blockPlayerDetector;
	public static BlockGeneric blockIronNote;
	
	public static void init() {
		if (Settings.enablePlayerDetector) { blockPlayerDetector = new BlockPlayerDetector(); }
		if (Settings.enableChatBox) { blockChatBox = new BlockChatBox(); }
		if (Settings.enableIronNote) { blockIronNote = new BlockIronNote(); }
	}
}