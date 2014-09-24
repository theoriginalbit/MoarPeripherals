/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.handler;

import com.theoriginalbit.moarperipherals.reference.Settings;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class ConfigHandler {
    private static final String ENABLED = "enabled";
    private static final String ENABLEFORMAT = "Enable the %s";

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

    private static boolean getEnabled(String key) {
        return getBoolean(ENABLED, key, String.format(ENABLEFORMAT, key));
    }

    public static void init(File c) {
        if (config == null) {
            config = new Configuration(c);
            load();
        }
    }

    private static void load() {
        config.load();

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
        // TODO: printer settings

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

        Settings.debug = getBoolean("debug", "debugMessages", false, "Print debugging messages to the console. WARNING: Spammy, only enable this if theoriginalbit has asked you to");

        if (config.hasChanged()) {
            config.save();
        }
    }

}