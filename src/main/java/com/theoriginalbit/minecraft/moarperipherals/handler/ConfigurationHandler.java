package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public final class ConfigurationHandler {

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
    private static final String PLAYERDETECTOR = "Player Detector";
    private static final String ANTENNAPOLE = "Communications Tower (Pole Block)";
    private static final String ANTENNACELL = "Communications Tower (Cell Block)";
    private static final String ANTENNACTRLR = "Communications Tower (Controller)";
    private static final String ANTENNAMODEM = "Communications Tower (Modem Block)";

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
        Settings.blockIdChatBox = getBlockId(CHATBOX, Settings.blockIdChatBox);
        Settings.blockIdPrinter = getBlockId(PRINTER, Settings.blockIdPrinter);
        Settings.blockIdKeyboard = getBlockId(KEYBOARD, Settings.blockIdKeyboard);
        Settings.blockIdIronNote = getBlockId(IRONNOTE, Settings.blockIdIronNote);
        Settings.blockIdDictionary = getBlockId(DICTIONARY, Settings.blockIdDictionary);
        Settings.blockIdPlayerDetector = getBlockId(PLAYERDETECTOR, Settings.blockIdPlayerDetector);
        Settings.blockIdAntenna = getBlockId(ANTENNAPOLE, Settings.blockIdAntenna);
        Settings.blockIdAntennaCell = getBlockId(ANTENNACELL, Settings.blockIdAntennaCell);
        Settings.blockIdAntennaModem = getBlockId(ANTENNAMODEM, Settings.blockIdAntennaModem);
        Settings.blockIdAntennaController = getBlockId(ANTENNACTRLR, Settings.blockIdAntennaController);

        // Item ID
        Settings.itemIdSonic = getItemId(SONIC, Settings.itemIdSonic);
        Settings.itemIdInkCartridge = getItemId(INKCARTRIDGE, Settings.itemIdInkCartridge);
        Settings.itemIdKeyboardPart = getItemId(KEYBOARDPART, Settings.itemIdKeyboardPart);

        // Fluid ID
//		Settings.fluidInkCyanID = getFluidId("fluidInkCyanID", Settings.fluidInkCyanID);
//		Settings.fluidInkYellowID = getFluidId("fluidInkYellowID", Settings.fluidInkYellowID);
//		Settings.fluidInkMagentaID = getFluidId("fluidInkMagentaID", Settings.fluidInkMagentaID);
//		Settings.fluidInkBlackID = getFluidId("fluidInkBlackID", Settings.fluidInkBlackID);
//		Settings.fluidPlasticID = getFluidId("fluidPlasticID", Settings.fluidPlasticID, "Plastic");

        // Feature enabled
        Settings.enableSonic = getEnabled(SONIC);
        Settings.enableChatBox = getEnabled(CHATBOX);
        Settings.enablePrinter = getEnabled(PRINTER);
        Settings.enableKeyboard = getEnabled(KEYBOARD);
        Settings.enableIronNote = getEnabled(IRONNOTE);
        Settings.enableDictionary = getEnabled(DICTIONARY);
        Settings.enablePlayerDetector = getEnabled(PLAYERDETECTOR);
        Settings.enableAntenna = getEnabled(ANTENNA);

        // ChatBox settings
        Settings.displayChatBoxCoords = getBoolean(CHATBOX, "displayCoords", false, "Show the x, y, and z coordinates of the ChatBox in chat messages");
        Settings.chatRangeSay = getInt(CHATBOX, "sayRange", Settings.chatRangeSay, "Range for the ChatBox peripheral's say function, set to -1 for infinite");
        Settings.chatRangeTell = getInt(CHATBOX, "tellRange", Settings.chatRangeSay, "Range for the ChatBox peripheral's tell (private message) function, set to -1 for infinite");
        Settings.chatRangeRead = getInt(CHATBOX, "readRange", Settings.chatRangeRead, "Range for the ChatBox peripheral's ability to 'hear' the chat, set to -1 for infinite");
        Settings.chatSayRate = getInt(CHATBOX, "sayRate", Settings.chatSayRate, "Maximum number of messages per second a ChatBox peripheral can 'say'");

        // Iron Note Block
        Settings.noteRange = getInt(IRONNOTE, "noteRange", Settings.noteRange, "The range at which the note can be heard. Note: Does not seem to work for audio, yet (?).");

        // Printer settings
//		Settings.enableFluidInk = getBoolean(PRINTER, "fluidInk", false, "Enable inks, ink cartridges will need to be filled with ink, not dyes. Note: Not yet implemented");

        // Keyboard settings
        Settings.keyboardRange = getInt(KEYBOARD, "keyboardRange", Settings.keyboardRange, "The range that a keyboard can connect to a computer from. This cannot be infinite.");

        // Antenna settings
        Settings.antennaRange = getInt(ANTENNA, "towerRange", Settings.antennaRange, "The range in blocks the Cell Tower can transmit");
        Settings.antennaRangeStorm = getInt(ANTENNA, "towerRangeStorm", Settings.antennaRangeStorm, "The range in blocks the Cell Tower can transmit during a storm");
        Settings.antennaMessageDelay = getInt(ANTENNA, "towerMessageDelay", Settings.antennaMessageDelay, "The delay (in ticks) that the Cell Tower takes to send a message per 100 block distance (rounded up).");
        Settings.antennaKeepChunkLoaded = getBoolean(ANTENNA, "keepChunkLoaded", "Whether a cell tower should keep the chunk it resides in loaded");

        // Renderer enabled
        Settings.enablePrinterGfx = getBoolean(RENDERER, "printerModel", false, "Whether or not to render items and blocks, related to the printer, normally or as models.");
        Settings.enableSonicGfx = getBoolean(RENDERER, "sonicModel", "Whether or not to render the Sonic Screwdriver normally or as a model");

        // Security settings
        Settings.securityOpBreak = getBoolean(SECURITY, "canOpBreakSecurity", "Are OPs able to break blocks that they don't own (when applicable); It is suggested you have this set to false until needed e.g. griefing ");

        if (config.hasChanged()) {
            config.save();
        }
    }

}