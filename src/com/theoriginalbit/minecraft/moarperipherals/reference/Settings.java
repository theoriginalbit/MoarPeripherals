package com.theoriginalbit.minecraft.moarperipherals.reference;

public final class Settings {
	private static int startBlockID = 3470;
	private static int startItemID = 4470;
	private static int startFluidID = 5470;

	// Player Detector
	public static boolean enablePlayerDetector;
	public static int blockIdPlayerDetector = startBlockID++;

	// ChatBox
	public static boolean enableChatBox;
	public static int blockIdChatBox = startBlockID++;
	public static boolean displayChatBoxCoords;
	public static int chatRangeSay = 64;
	public static int chatRangeTell = 64;
	public static int chatRangeRead = -1;
	public static int chatSayRate = 1;

	// Iron Note Block
	public static boolean enableIronNote;
	public static int blockIdIronNote = startBlockID++;
	public static int noteRange = 64;

	// Printer
	public static boolean enablePrinter;
	public static int blockIdPrinter = startBlockID++;
	public static boolean enableFluidInk;
	public static int itemIdInkCartridge = startItemID++;

	// Fluids
	public static int fluidInkWhiteID = startFluidID++;
	public static int fluidInkOrangeID = startFluidID++;
	public static int fluidInkMagentaID = startFluidID++;
	public static int fluidInkLightBlueID = startFluidID++;
	public static int fluidInkYellowID = startFluidID++;
	public static int fluidInkLimeID = startFluidID++;
	public static int fluidInkPinkID = startFluidID++;
	public static int fluidInkGrayID = startFluidID++;
	public static int fluidInkLightGrayID = startFluidID++;
	public static int fluidInkCyanID = startFluidID++;
	public static int fluidInkPurpleID = startFluidID++;
	public static int fluidInkBlueID = startFluidID++;
	public static int fluidInkBrownID = startFluidID++;
	public static int fluidInkGreenID = startFluidID++;
	public static int fluidInkRedID = startFluidID++;
	public static int fluidInkBlackID = startFluidID++;
	public static int fluidPlasticID = startFluidID++;
	
	// Keyboard
	public static boolean enableKeyboard;
	public static int blockIdKeyboard = startBlockID++;
	public static int keyboardRange = 16;
	
	// Renderer
	public static boolean enableInkCartridgeRenderer;
}