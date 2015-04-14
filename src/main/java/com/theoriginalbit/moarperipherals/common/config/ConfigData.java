/*
 * Copyright 2015 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.config;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConfigData {
    /*
     * These don't go in the config, they need to be static
     */
    private static int startUpgradeID = 16384;
    public static int upgradeIdChatBox = startUpgradeID++;
    public static int upgradeIdIronNote = startUpgradeID++;
    public static int upgradeIdCompass = startUpgradeID++;
    public static int upgradeIdShears = startUpgradeID++;
    public static int upgradeIdFurnace = startUpgradeID++;
    public static int upgradeIdSolar = startUpgradeID++;
    public static int upgradeIdIgniter = startUpgradeID++;
    public static int upgradeIdFeeder = startUpgradeID++;
    public static int upgradeIdOreScanner = startUpgradeID++;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Sonic Screwdriver")
    public static boolean enableSonic;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the ChatBox")
    public static boolean enableChatBox;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Printer")
    public static boolean enablePrinter;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Keyboard")
    public static boolean enableKeyboard;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Iron Note block")
    public static boolean enableIronNote;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Player Detector")
    public static boolean enablePlayerDetector;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the BitNet Antenna")
    public static boolean enableAntenna;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the BitNet Mini-Antenna")
    public static boolean enableMiniAntenna;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Turtle Teleport")
    public static boolean enableTurtleTeleport;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Firework Launcher")
    public static boolean enableFireworkLauncher;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Computer Crafter")
    public static boolean enableComputerCrafter;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Shears Turtle Upgrade")
    public static boolean enableUpgradeShears;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Compass Turtle Upgrade")
    public static boolean enableUpgradeCompass;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Solar Turtle Upgrade")
    public static boolean enableUpgradeSolar;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Igniter Turtle Upgrade")
    public static boolean enableUpgradeIgniter;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Furnace Turtle Upgrade")
    public static boolean enableUpgradeFurnace;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Feeder Turtle Upgrade")
    public static boolean enableUpgradeFeeder;

    @ConfigPropertyBoolean(category = "enabled", comment = "Enable the Ore Scanner Turtle Upgrade")
    public static boolean enableUpgradeOreScanner;

    @ConfigPropertyBoolean(comment = "Show the x, y, and z coordinates of the ChatBox in chat messages")
    public static boolean displayChatBoxCoordinate;

    @ConfigPropertyInteger(value = 64, comment = "Range for the ChatBox peripheral's say function, set to -1 for infinite")
    public static int chatRangeSay;

    @ConfigPropertyInteger(value = 64, comment = "Range for the ChatBox peripheral's tell (private message) function, set to -1 for infinite")
    public static int chatRangeTell;

    @ConfigPropertyInteger(value = -1, comment = "Range for the ChatBox peripheral's ability to 'hear' the chat, set to -1 for infinite")
    public static int chatRangeRead;

    @ConfigPropertyInteger(value = 1, comment = "Maximum number of messages per second a ChatBox peripheral can 'say'")
    public static int chatSayRate;

    @ConfigPropertyInteger(value = 64, comment = "The range at which the note can be heard. Note: Does not seem to work for audio, yet (?).")
    public static int noteRange;

    @ConfigPropertyInteger(value = 16, comment = "The range that a keyboard can connect to a computer from. This cannot be infinite.")
    public static int keyboardRange;

    @ConfigPropertyBoolean(comment = "Whether a BitNet Communications Tower should keep the chunk it resides in loaded")
    public static boolean antennaKeepsChunkLoaded;

    @ConfigPropertyInteger(value = 3, comment = "The delay (in ticks) that the BitNet network takes to send a message per 100 block distance (rounded up).")
    public static int bitNetMessageDelay;

    @ConfigPropertyInteger(value = 3000, comment = "The range in blocks the BitNet Communications Tower can transmit")
    public static int antennaRange;

    @ConfigPropertyInteger(value = 2400, comment = "The range in blocks the BitNet Communications Tower can transmit during a storm")
    public static int antennaRangeStorm;

    @ConfigPropertyInteger(value = 650, comment = "The range in blocks the BitNet Mini Antenna can transmit")
    public static int miniAntennaRange;

    @ConfigPropertyInteger(value = 400, comment = "The range in blocks the BitNet Mini Antenna can transmit during a storm")
    public static int miniAntennaRangeStorm;

    @ConfigPropertyDouble(value = 1.5d, comment = "The multiplier for the fuel consumption to teleport the Turtle. Formula: Euclidean distance * multiplier")
    public static double teleportFuelMultiplier;

    @ConfigPropertyInteger(value = 20, minValue = 10, comment = "The fuel consumption per item to smelt items in the Furnace Turtle Upgrade (Minimum value: 10)")
    public static int upgradeFurnaceFuelConsumption;

    @ConfigPropertyString(value = "", comment = "Add density mappings (semi-colon separated) for mod blocks to the Density Scanner in the format [modId]:[blockName]@[density];\nSingle mapping example: MoarPeripherals:blockIronNote@5.45\nMulti-mapping example:  MoarPeripherals:blockIronNote@5.45;MoarPeripherals:blockChatBox@4.45")
    public static String userDensityMappings;

    @ConfigPropertyBoolean(comment = "Whether or not to render items and blocks, related to the printer, normally or as models.")
    public static boolean enablePrinterGfx;

    @ConfigPropertyBoolean(comment = "Whether or not to render the Sonic Screwdriver normally or as a model")
    public static boolean enableSonicGfx;

    @ConfigPropertyBoolean(value = false, comment = "Print debugging messages to the console. WARNING: Spammy, only enable this if theoriginalbit has asked you to")
    public static boolean debug;

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
}
