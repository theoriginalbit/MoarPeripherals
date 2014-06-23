package com.theoriginalbit.minecraft.moarperipherals.reference;

import java.io.File;

import net.minecraftforge.common.Configuration;

public final class Config {
	private static final String ENABLED = "enabled";
	private static final String BLOCKID = "blockId";
	private static final String ENABLEFORMAT = "Enable the %s Block";
	private static final String BLOCKIDFORMAT = "The Block ID of the %s Block";
	
	private static final String CHATBOX = "ChatBox";
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
	
	private static final int getBlockId(String cat, int id) {
		return getInt(cat, BLOCKID, id, String.format(BLOCKIDFORMAT, cat));
	}
	
	public static final void init(File c) {
		config = new Configuration(c);
		
		// Player Detector
		Settings.enablePlayerDetector = getEnabled(PLAYERDETECTOR);
		Settings.blockPlayerDetectorID = getBlockId(PLAYERDETECTOR, Settings.blockPlayerDetectorID);
		
		// ChatBox
		Settings.enableChatBox = getEnabled(CHATBOX);
		Settings.blockChatBoxID = getBlockId(CHATBOX, Settings.blockChatBoxID);
		Settings.displayChatBoxCoords = getBoolean(CHATBOX, "displayCoords", false, "Show the x, y, and z coordinates of the ChatBox in chat messages");
		Settings.chatRangeSay = getInt(CHATBOX, "sayRange", Settings.chatRangeSay, "Range for the ChatBox peripheral's say function, set to -1 for infinite");
		Settings.chatRangeTell = getInt(CHATBOX, "tellRange", Settings.chatRangeSay, "Range for the ChatBox peripheral's tell (private message) function, set to -1 for infinite");
		Settings.chatRangeRead = getInt(CHATBOX, "readRange", Settings.chatRangeRead, "Range for the ChatBox peripheral's ability to 'hear' the chat, set to -1 for infinite");
		Settings.chatSayRate = getInt(CHATBOX, "sayRate", Settings.chatSayRate, "Maximum number of messages per second a ChatBox peripheral can 'say'");
		
		// Iron Note Block
		Settings.enableIronNote = getEnabled(IRONNOTE);
		Settings.blockIronNoteID = getBlockId(IRONNOTE, Settings.blockIronNoteID);
		
		if (config.hasChanged()) {
			config.save();
		}
	}
}