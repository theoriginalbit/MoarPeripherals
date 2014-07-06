package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

public final class Blocks {
	public static BlockChatBox blockChatBox;
	public static BlockPlayerDetector blockPlayerDetector;
	public static BlockIronNote blockIronNote;
	
	public static void init() {
		if (Settings.enablePlayerDetector) { blockPlayerDetector = new BlockPlayerDetector(); }
		if (Settings.enableChatBox) { blockChatBox = new BlockChatBox(); }
		if (Settings.enableIronNote) { blockIronNote = new BlockIronNote(); }
	}
}