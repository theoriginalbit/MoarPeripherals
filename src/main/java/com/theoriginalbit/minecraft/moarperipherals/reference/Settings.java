package com.theoriginalbit.minecraft.moarperipherals.reference;

public final class Settings {

    // Block ID
    private static int startBlockID = 3470;
    public static int blockIdPlayerDetector = startBlockID++;
    public static int blockIdChatBox = startBlockID++;
    public static int blockIdIronNote = startBlockID++;
    public static int blockIdPrinter = startBlockID++;
    public static int blockIdKeyboard = startBlockID++;

    // Item ID
    private static int startItemID = 4470;
    public static int itemIdInkCartridge = startItemID++;
    public static int itemKeyboardPart = startItemID++;

    // Fluid ID
    private static int startFluidID = 5470;
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

    // Block/feature enabled
    public static boolean enablePlayerDetector;
    public static boolean enableChatBox;
    public static boolean enableIronNote;
    public static boolean enableKeyboard;
    public static boolean enablePrinter;

    // ChatBox settings
    public static boolean displayChatBoxCoords;
    public static int chatRangeSay = 64;
    public static int chatRangeTell = 64;
    public static int chatRangeRead = -1;
    public static int chatSayRate = 1;

    // Iron Note Block
    public static int noteRange = 64;

    // Printer settings
    public static boolean enableFluidInk;

    // Keyboard settings
    public static int keyboardRange = 16;

    // Renderer enabled
    public static boolean enableRendererInkCartridge;
    public static boolean enableRendererPrinter;

}