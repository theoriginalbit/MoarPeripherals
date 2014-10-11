/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.config;

import net.minecraftforge.common.Configuration;

import java.io.File;

public final class ConfigHandler {

    // Block ID
    private static int startBlockID = 3470;
    public static int blockIdPlayerDetector = startBlockID++;
    public static int blockIdChatBox = startBlockID++;
    public static int blockIdIronNote = startBlockID++;
    public static int blockIdPrinter = startBlockID++;
    public static int blockIdKeyboard = startBlockID++;
    public static int blockIdDictionary = startBlockID++;
    public static int blockIdAntenna = startBlockID++;
    public static int blockIdAntennaCell = startBlockID++;
    public static int blockIdAntennaMiniCell = startBlockID++;
    public static int blockIdAntennaController = startBlockID++;

    // Item ID
    private static int startItemID = 4470;
    public static int itemIdInkCartridge = startItemID++;
    public static int itemIdKeyboardPart = startItemID++;
    public static int itemIdSonic = startItemID++;
    public static int itemIdMonopoleAntenna = startItemID++;

    // Upgrade ID
    private static int startUpgradeID = 16384;
    public static int upgradeIdChatBox = startUpgradeID++;
    public static int upgradeIdIronNote = startUpgradeID++;
    public static int upgradeIdDictionary = startUpgradeID++;

    // Feature enabled
    public static boolean enablePlayerDetector;
    public static boolean enableChatBox;
    public static boolean enableIronNote;
    public static boolean enableKeyboard;
    public static boolean enablePrinter;
    public static boolean enableDictionary;
    public static boolean enableSonic;
    public static boolean enableAntenna;

    // ChatBox settings
    public static boolean displayChatBoxCoords;
    public static int chatRangeSay = 64;
    public static int chatRangeTell = 64;
    public static int chatRangeRead = -1;
    public static int chatSayRate = 1;

    // Iron Note Block
    public static int noteRange = 64;

    // Keyboard settings
    public static int keyboardRange = 16;

    // Antenna settings
    public static int antennaRange = 3000;
    public static int antennaRangeStorm = 2400;
    public static int antennaMessageDelay = 3;
    public static boolean antennaKeepChunkLoaded;

    // Renderer enabled
    public static boolean enablePrinterGfx;
    public static boolean enableSonicGfx;

    // Security settings
    public static boolean securityOpBreak;

    public static boolean isSonicEnabled() {
        /*
            this will be expanded in the future to ignore the enabled flag
            when a flag from blocks that require the sonic for configuration
            is enabled
         */
        return enableSonic;
    }

    public static boolean shouldChunkLoad() {
        /*
            this could expand in the future
         */
        return antennaKeepChunkLoaded;
    }

    public static boolean debug;

    private static final String ITEMID = "itemId";
    private static final String BLOCKID = "blockId";
    private static final String ENABLED = "enabled";
    private static final String ENABLEFORMAT = "Enable the %s";
    private static final String ITEMIDFORMAT = "The ID for the %s Item";
//	private static final String LIQUIDIDFORMAT = "The Liquid ID for %s";
    private static final String BLOCKIDFORMAT = "The Block ID of the %s Block";

//	private static final String FLUIDS = "Fluids";
    private static final String SONIC = "Sonic Screwdriver";
    private static final String CHATBOX = "ChatBox";
    private static final String PRINTER = "Printer";
    private static final String RENDERER = "Render";
    private static final String KEYBOARD = "Keyboard";
    private static final String SECURITY = "Security";
    private static final String IRONNOTE = "Iron Note";
    private static final String ANTENNA = "Communications Tower";
    private static final String DICTIONARY = "Item Dictionary";
    private static final String INKCARTRIDGE = "Ink Cartridge";
    private static final String KEYBOARDPART = "Keyboard Part";
    private static final String MONOPOLEPART = "Monopole Antenna";
    private static final String PLAYERDETECTOR = "Player Detector";
    private static final String ANTENNAPOLE = "Communications Tower (Pole Block)";
    private static final String ANTENNACELL = "Communications Tower (Cell Block)";
    private static final String ANTENNACTRLR = "Communications Tower (Controller)";
    private static final String ANTENNAMINI = "Communications Tower (Mini Cell Block)";

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

    private static boolean getEnabled(String key) {
        return getBoolean(ENABLED, key, String.format(ENABLEFORMAT, key));
    }

    private static int getBlockId(String key, int defId) {
        return getInt(BLOCKID, key, defId, String.format(BLOCKIDFORMAT, key));
    }

    private static int getItemId(String key, int defId) {
        return getInt(ITEMID, key, defId, String.format(ITEMIDFORMAT, key));
    }

//	private static final int getFluidId(String key, int defId) {
//		return getInt(FLUIDS, key, defId, null);
//	}
//	
//	private static final int getFluidId(String key, int defId, String desc) {
//		return getInt(FLUIDS, key, defId, String.format(LIQUIDIDFORMAT, desc));
//	}

    public static void init(File c) {
        if (config == null) {
            config = new Configuration(c);
            load();
        }
    }

    private static void load() {
        config.load();

        // Block ID
        blockIdChatBox = getBlockId(CHATBOX, blockIdChatBox);
        blockIdPrinter = getBlockId(PRINTER, blockIdPrinter);
        blockIdKeyboard = getBlockId(KEYBOARD, blockIdKeyboard);
        blockIdIronNote = getBlockId(IRONNOTE, blockIdIronNote);
        blockIdDictionary = getBlockId(DICTIONARY, blockIdDictionary);
        blockIdPlayerDetector = getBlockId(PLAYERDETECTOR, blockIdPlayerDetector);
        blockIdAntenna = getBlockId(ANTENNAPOLE, blockIdAntenna);
        blockIdAntennaCell = getBlockId(ANTENNACELL, blockIdAntennaCell);
        blockIdAntennaMiniCell = getBlockId(ANTENNAMINI, blockIdAntennaMiniCell);
        blockIdAntennaController = getBlockId(ANTENNACTRLR, blockIdAntennaController);

        // Item ID
        itemIdSonic = getItemId(SONIC, itemIdSonic);
        itemIdInkCartridge = getItemId(INKCARTRIDGE, itemIdInkCartridge);
        itemIdKeyboardPart = getItemId(KEYBOARDPART, itemIdKeyboardPart);
        itemIdMonopoleAntenna = getItemId(MONOPOLEPART, itemIdMonopoleAntenna);

        // Fluid ID
//		fluidInkCyanID = getFluidId("fluidInkCyanID", fluidInkCyanID);
//		fluidInkYellowID = getFluidId("fluidInkYellowID", fluidInkYellowID);
//		fluidInkMagentaID = getFluidId("fluidInkMagentaID", fluidInkMagentaID);
//		fluidInkBlackID = getFluidId("fluidInkBlackID", fluidInkBlackID);
//		fluidPlasticID = getFluidId("fluidPlasticID", fluidPlasticID, "Plastic");

        // Feature enabled
        enableSonic = getEnabled(SONIC);
        enableChatBox = getEnabled(CHATBOX);
        enablePrinter = getEnabled(PRINTER);
        enableKeyboard = getEnabled(KEYBOARD);
        enableIronNote = getEnabled(IRONNOTE);
        enableDictionary = getEnabled(DICTIONARY);
        enablePlayerDetector = getEnabled(PLAYERDETECTOR);
        enableAntenna = getEnabled(ANTENNA);

        // ChatBox settings
        displayChatBoxCoords = getBoolean(CHATBOX, "displayCoords", false, "Show the x, y, and z coordinates of the ChatBox in chat messages");
        chatRangeSay = getInt(CHATBOX, "sayRange", chatRangeSay, "Range for the ChatBox peripheral's say function, set to -1 for infinite");
        chatRangeTell = getInt(CHATBOX, "tellRange", chatRangeSay, "Range for the ChatBox peripheral's tell (private message) function, set to -1 for infinite");
        chatRangeRead = getInt(CHATBOX, "readRange", chatRangeRead, "Range for the ChatBox peripheral's ability to 'hear' the chat, set to -1 for infinite");
        chatSayRate = getInt(CHATBOX, "sayRate", chatSayRate, "Maximum number of messages per second a ChatBox peripheral can 'say'");

        // Iron Note Block
        noteRange = getInt(IRONNOTE, "noteRange", noteRange, "The range at which the note can be heard. Note: Does not seem to work for audio, yet (?).");

        // Printer settings
//		enableFluidInk = getBoolean(PRINTER, "fluidInk", false, "Enable inks, ink cartridges will need to be filled with ink, not dyes. Note: Not yet implemented");

        // Keyboard settings
        keyboardRange = getInt(KEYBOARD, "keyboardRange", keyboardRange, "The range that a keyboard can connect to a computer from. This cannot be infinite.");

        // Antenna settings
        antennaRange = getInt(ANTENNA, "towerRange", antennaRange, "The range in blocks the Cell Tower can transmit");
        antennaRangeStorm = getInt(ANTENNA, "towerRangeStorm", antennaRangeStorm, "The range in blocks the Cell Tower can transmit during a storm");
        antennaMessageDelay = getInt(ANTENNA, "towerMessageDelay", antennaMessageDelay, "The delay (in ticks) that the Cell Tower takes to send a message per 100 block distance (rounded up).");
        antennaKeepChunkLoaded = getBoolean(ANTENNA, "keepChunkLoaded", "Whether a cell tower should keep the chunk it resides in loaded");

        // Renderer enabled
        enablePrinterGfx = getBoolean(RENDERER, "printerModel", false, "Whether or not to render items and blocks, related to the printer, normally or as models.");
        enableSonicGfx = getBoolean(RENDERER, "sonicModel", "Whether or not to render the Sonic Screwdriver normally or as a model");

        // Security settings
        securityOpBreak = getBoolean(SECURITY, "canOpBreakSecurity", "Are OPs able to break blocks that they don't own (when applicable); It is suggested you have this set to false until needed e.g. griefing ");

        debug = getBoolean("debug", "debugMessages", false, "Print debugging messages to the console. WARNING: Spammy, only enable this if theoriginalbit has asked you to");

        if (config.hasChanged()) {
            config.save();
        }
    }

}