package com.theoriginalbit.minecraft.moarperipherals.handler;

import java.io.File;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import net.minecraftforge.common.Configuration;

public final class ConfigurationHandler {
	private static final String ENABLED = "enabled";
	private static final String BLOCKID = "blockId";
	private static final String ENABLEFORMAT = "Enable the %s Block";
	private static final String BLOCKIDFORMAT = "The Block ID of the %s Block";
	private static final String ITEMIDFORMAT = "The ID for the %s Item";
	private static final String LIQUIDIDFORMAT = "The Liquid ID for %s";
	
	private static final String FLUIDS = "Fluids";
	private static final String CHATBOX = "ChatBox";
	private static final String PRINTER = "Printer";
	private static final String IRONNOTE = "Iron Note";
	private static final String PLAYERDETECTOR = "Player Detector";
	
	private static Configuration config;
	
	private static final boolean getBoolean(String cat, String key, boolean defBool, String desc) {
		return config.get(cat, key, defBool, desc).getBoolean(defBool);
	}
	
	private static final boolean getBoolean(String cat, String key, String desc) {
		return getBoolean(cat, key, true, desc);
	}
	
	private static final int getInt(String cat, String key, int defInt, String desc) {
		return config.get(cat, key, defInt, desc).getInt();
	}
	
	private static final boolean getEnabled(String cat) {
		return getBoolean(cat, ENABLED, String.format(ENABLEFORMAT, cat));
	}
	
	private static final int getBlockId(String cat, int defId) {
		return getInt(cat, BLOCKID, defId, String.format(BLOCKIDFORMAT, cat));
	}
	
	private static final int getItemId(String cat, String key, int defId, String readableName) {
		return getInt(cat, key, defId, String.format(ITEMIDFORMAT, readableName));
	}
	
	private static final int getFluidId(String key, int defId) {
		return getInt(FLUIDS, key, defId, null);
	}
	
	private static final int getFluidId(String key, int defId, String desc) {
		return getInt(FLUIDS, key, defId, String.format(LIQUIDIDFORMAT, desc));
	}
	
	public static final void init(File c) {
		if (config == null) {
			config = new Configuration(c);
			load();
		}
	}
	
	private static void load() {
		config.load();
		
		// Player Detector
		Settings.enablePlayerDetector = getEnabled(PLAYERDETECTOR);
		Settings.blockIdPlayerDetector = getBlockId(PLAYERDETECTOR, Settings.blockIdPlayerDetector);
		
		// ChatBox
		Settings.enableChatBox = getEnabled(CHATBOX);
		Settings.blockIdChatBox = getBlockId(CHATBOX, Settings.blockIdChatBox);
		Settings.displayChatBoxCoords = getBoolean(CHATBOX, "displayCoords", false, "Show the x, y, and z coordinates of the ChatBox in chat messages");
		Settings.chatRangeSay = getInt(CHATBOX, "sayRange", Settings.chatRangeSay, "Range for the ChatBox peripheral's say function, set to -1 for infinite");
		Settings.chatRangeTell = getInt(CHATBOX, "tellRange", Settings.chatRangeSay, "Range for the ChatBox peripheral's tell (private message) function, set to -1 for infinite");
		Settings.chatRangeRead = getInt(CHATBOX, "readRange", Settings.chatRangeRead, "Range for the ChatBox peripheral's ability to 'hear' the chat, set to -1 for infinite");
		Settings.chatSayRate = getInt(CHATBOX, "sayRate", Settings.chatSayRate, "Maximum number of messages per second a ChatBox peripheral can 'say'");
		
		// Iron Note Block
		Settings.enableIronNote = getEnabled(IRONNOTE);
		Settings.blockIdIronNote = getBlockId(IRONNOTE, Settings.blockIdIronNote);
		Settings.noteRange = getInt(IRONNOTE, "noteRange", Settings.noteRange, "The range at which the note can be heard. Note: Does not seem to work for audio, yet (?).");
		
		// Printer
		Settings.enablePrinter = getEnabled(PRINTER);
		Settings.blockIdPrinter = getBlockId(PRINTER, Settings.blockIdPrinter);
		Settings.enableFluidInk = getBoolean(PRINTER, "fluidInk", false, "Enable inks, ink cartridges will need to be filled with ink, not dyes. Note: Not yet implemented");
		Settings.itemIdInkCartridgeEmpty = getItemId(PRINTER, "inkCartridgeEmpty", Settings.itemIdInkCartridgeEmpty, "Empty Ink Cartridge");
		Settings.itemIdInkCartridgeFilled = getItemId(PRINTER, "inkCartridgeFilled", Settings.itemIdInkCartridgeFilled, "Filled Ink Cartridge");
		Settings.enableInkCartridgeModel = getBoolean(PRINTER, "enableInkCartridgeModel", "Enable whether the ink cartridge should be rendered in the players hand as a model or texture");
		
		// Fluids
		Settings.fluidInkWhiteID = getFluidId("fluidInkWhiteID", Settings.fluidInkWhiteID);
		Settings.fluidInkOrangeID = getFluidId("fluidInkOrangeID", Settings.fluidInkOrangeID);
		Settings.fluidInkMagentaID = getFluidId("fluidInkMagentaID", Settings.fluidInkMagentaID);
		Settings.fluidInkLightBlueID = getFluidId("fluidInkLightBlueID", Settings.fluidInkLightBlueID);
		Settings.fluidInkYellowID = getFluidId("fluidInkYellowID", Settings.fluidInkYellowID);
		Settings.fluidInkLimeID = getFluidId("fluidInkLimeID", Settings.fluidInkLimeID);
		Settings.fluidInkPinkID = getFluidId("fluidInkPinkID", Settings.fluidInkPinkID);
		Settings.fluidInkGrayID = getFluidId("fluidInkGrayID", Settings.fluidInkGrayID);
		Settings.fluidInkLightGrayID = getFluidId("fluidInkLightGrayID", Settings.fluidInkLightGrayID);
		Settings.fluidInkCyanID = getFluidId("fluidInkCyanID", Settings.fluidInkCyanID);
		Settings.fluidInkPurpleID = getFluidId("fluidInkPurpleID", Settings.fluidInkPurpleID);
		Settings.fluidInkBlueID = getFluidId("fluidInkBlueID", Settings.fluidInkBlueID);
		Settings.fluidInkBrownID = getFluidId("fluidInkBrownID", Settings.fluidInkBrownID);
		Settings.fluidInkGreenID = getFluidId("fluidInkGreenID", Settings.fluidInkGreenID);
		Settings.fluidInkRedID = getFluidId("fluidInkRedID", Settings.fluidInkRedID);
		Settings.fluidInkBlackID = getFluidId("fluidInkBlackID", Settings.fluidInkBlackID);
		Settings.fluidPlasticID = getFluidId("fluidPlasticID", Settings.fluidPlasticID, "Plastic");
	
		if (config.hasChanged()) {
			config.save();
		}
	}
}