package com.theoriginalbit.minecraft.moarperipherals.reference;

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
public final class Settings {

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
    public static int blockIdAntennaModem = startBlockID++;
    public static int blockIdAntennaController = startBlockID++;

    // Item ID
    private static int startItemID = 4470;
    public static int itemIdInkCartridge = startItemID++;
    public static int itemIdKeyboardPart = startItemID++;
    public static int itemIdSonic = startItemID++;

    // Fluid ID
    private static int startFluidID = 5470;
    public static int fluidInkCyanID = startFluidID++;
    public static int fluidInkYellowID = startFluidID++;
    public static int fluidInkMagentaID = startFluidID++;
    public static int fluidInkBlackID = startFluidID++;
    public static int fluidPlasticID = startFluidID++;

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
    public static boolean antennaKeepChunkLoaded = false;
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
}