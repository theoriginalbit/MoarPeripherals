/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.config;

import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class ConfigHandler {
    private static final String ENABLED = "enabled";
    private static final String ENABLEFORMAT = "Enable the %s";

    public static final String CATEGORY_SONIC = "Sonic Screwdriver";
    public static final String CATEGORY_CHAT_BOX = "ChatBox";
    public static final String CATEGORY_PRINTER = "Printer";
    public static final String CATEGORY_RENDERER = "Render";
    public static final String CATEGORY_KEYBOARD = "Keyboard";
    public static final String CATEGORY_SECURITY = "Security";
    public static final String CATEGORY_IRON_NOTE = "Iron Note";
    public static final String CATEGORY_ANTENNA = "Communications Tower";
    public static final String CATEGORY_DICTIONARY = "Item Dictionary";
    public static final String CATEGORY_PLAYER_DETECTOR = "Player Detector";

    public static Configuration config;

    // Turtle Upgrade ID
    private static int startUpgradeID = 16384;
    public static int upgradeIdChatBox = startUpgradeID++;
    public static int upgradeIdIronNote = startUpgradeID++;
    public static int upgradeIdDictionary = startUpgradeID++;

    // Feature enabled
    public static boolean enableSonic;
    public static boolean enableChatBox;
    public static boolean enablePrinter;
    public static boolean enableKeyboard;
    public static boolean enableIronNote;
    public static boolean enableDictionary;
    public static boolean enablePlayerDetector;
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
    public static int antennaRange = 1000;
    public static int antennaRangeStorm = 800;
    public static int antennaMessageDelay = 5;
    public static boolean antennaKeepsChunkLoaded;

    // Renderer enabled
    public static boolean enablePrinterGfx, enableSonicGfx;

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
        return antennaKeepsChunkLoaded;
    }

    public static boolean debug;

    public static void init(File c) {
        if (config == null) {
            config = new Configuration(c);
            doConfiguration();
        }
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(ModInfo.ID)) {
            LogUtils.info("Refreshing the config file...");
            doConfiguration();
        }
    }

    private static void doConfiguration() {
        config.load();

        // Feature enabled
        enableSonic = getEnabled(CATEGORY_SONIC);
        enableChatBox = getEnabled(CATEGORY_CHAT_BOX);
        enablePrinter = getEnabled(CATEGORY_PRINTER);
        enableKeyboard = getEnabled(CATEGORY_KEYBOARD);
        enableIronNote = getEnabled(CATEGORY_IRON_NOTE);
        enableDictionary = getEnabled(CATEGORY_DICTIONARY);
        enablePlayerDetector = getEnabled(CATEGORY_PLAYER_DETECTOR);
        enableAntenna = getEnabled(CATEGORY_ANTENNA);

        // ChatBox settings
        displayChatBoxCoords = getBoolean(CATEGORY_CHAT_BOX, "displayCoords", false, "Show the x, y, and z coordinates of the ChatBox in chat messages");
        chatRangeSay = getInt(CATEGORY_CHAT_BOX, "sayRange", chatRangeSay, "Range for the ChatBox peripheral's say function, set to -1 for infinite");
        chatRangeTell = getInt(CATEGORY_CHAT_BOX, "tellRange", chatRangeSay, "Range for the ChatBox peripheral's tell (private message) function, set to -1 for infinite");
        chatRangeRead = getInt(CATEGORY_CHAT_BOX, "readRange", chatRangeRead, "Range for the ChatBox peripheral's ability to 'hear' the chat, set to -1 for infinite");
        chatSayRate = getInt(CATEGORY_CHAT_BOX, "sayRate", chatSayRate, "Maximum number of messages per second a ChatBox peripheral can 'say'");

        // Iron Note Block
        noteRange = getInt(CATEGORY_IRON_NOTE, "noteRange", noteRange, "The range at which the note can be heard. Note: Does not seem to work for audio, yet (?).");

        // Printer settings
        // TODO: printer settings

        // Keyboard settings
        keyboardRange = getInt(CATEGORY_KEYBOARD, "keyboardRange", keyboardRange, "The range that a keyboard can connect to a computer from. This cannot be infinite.");

        // Antenna settings
        antennaRange = getInt(CATEGORY_ANTENNA, "towerRange", antennaRange, "The range in blocks the Cell Tower can transmit");
        antennaRangeStorm = getInt(CATEGORY_ANTENNA, "towerRangeStorm", antennaRangeStorm, "The range in blocks the Cell Tower can transmit during a storm");
        antennaMessageDelay = getInt(CATEGORY_ANTENNA, "towerMessageDelay", antennaMessageDelay, "The delay (in ticks) that the Cell Tower takes to send a message per 100 block distance (rounded up).");
        antennaKeepsChunkLoaded = getBoolean(CATEGORY_ANTENNA, "keepChunkLoaded", "Whether a cell tower should keep the chunk it resides in loaded");

        // Renderer enabled
        enablePrinterGfx = getBoolean(CATEGORY_RENDERER, "printerModel", false, "Whether or not to render items and blocks, related to the printer, normally or as models.");
        enableSonicGfx = getBoolean(CATEGORY_RENDERER, "sonicModel", "Whether or not to render the Sonic Screwdriver normally or as a model");

        // Security settings
//        securityOpBreak = getBoolean(CATEGORY_SECURITY, "canOpBreakSecurity", "Are OPs able to break blocks that they don't own (when applicable); It is suggested you have this set to false until needed e.g. griefing");

        debug = getBoolean("debug", "debugMessages", false, "Print debugging messages to the console. WARNING: Spammy, only enable this if theoriginalbit has asked you to");

        if (config.hasChanged()) {
            config.save();
        }
    }

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

}