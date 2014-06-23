package com.theoriginalbit.minecraft.moarperipherals.reference;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class Config {
	private static final String ENABLED = "enabled";
	private static final String BLOCKID = "blockId";
	private static final String ENABLEFORMAT = "Enable the %s Block";
	private static final String BLOCKIDFORMAT = "The Block ID of the %s Block";
	
	private static final String PLAYERDETECTOR = "Player Detector";
	
	private static Configuration config;
	
	private static boolean getBoolean(String cat, String key, boolean defBool, String desc) {
		return config.get(cat, key, defBool, desc).getBoolean(defBool);
	}
	
	private static boolean getBoolean(String cat, String key, String desc) {
		return getBoolean(cat, key, true, desc);
	}
	
	private static int getInt(String cat, String key, int defInt, String desc) {
		return config.get(cat, key, defInt, desc).getInt();
	}
	
	private static boolean getEnabled(String cat) {
		return getBoolean(cat, ENABLED, String.format(ENABLEFORMAT, cat));
	}
	
	private static int getBlockId(String cat, int id) {
		return getInt(cat, BLOCKID, id, String.format(BLOCKIDFORMAT, cat));
	}
	
	public static void init(File c) {
		config = new Configuration(c);
		
		// Player Detector
		Settings.enablePlayerDetector = getEnabled(PLAYERDETECTOR);
		Settings.blockPlayerDetectorID = getBlockId(PLAYERDETECTOR, Settings.blockPlayerDetectorID);
		
		if (config.hasChanged()) {
			config.save();
		}
	}

}
