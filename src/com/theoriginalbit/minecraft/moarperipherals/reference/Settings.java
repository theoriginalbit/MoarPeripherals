package com.theoriginalbit.minecraft.moarperipherals.reference;

public class Settings {
	private static int startBlockID = 3470;
	
	// Player Detector
	public static boolean enablePlayerDetector;
	public static int blockPlayerDetectorID = startBlockID++;

	// ChatBox
	public static boolean enableChatBox;
	public static int blockChatBoxID = startBlockID++;
	public static boolean displayChatBoxCoords;
	public static int chatRangeSay = 64;
	public static int chatRangeTell = 64;
	public static int chatRangeRead = -1;
	public static int chatSayRate = 1;
}
