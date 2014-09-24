/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.reference;

public final class Settings {
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

    // Printer settings
    public static boolean enableFluidInk;

    // Keyboard settings
    public static int keyboardRange = 16;

    // Antenna settings
    public static int antennaRange = 1000;
    public static int antennaRangeStorm = 800;
    public static boolean antennaKeepChunkLoaded;
    public static int antennaMessageDelay = 5;

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
}
