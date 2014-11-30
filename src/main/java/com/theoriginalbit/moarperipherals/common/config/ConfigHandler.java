/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
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

import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public final class ConfigHandler {
    private static final String ENABLEFORMAT = "Enable the %s";

    public static final String CATEGORY_ENABLED = "Enabled";
    public static final String CATEGORY_UPGRADE = "Upgrades";
    public static final String CATEGORY_CHAT_BOX = "ChatBox";
    public static final String CATEGORY_PRINTER = "Printer";
    public static final String CATEGORY_RENDERER = "Render";
    public static final String CATEGORY_KEYBOARD = "Keyboard";
    public static final String CATEGORY_SECURITY = "Security";
    public static final String CATEGORY_IRON_NOTE = "Iron Note";
    public static final String CATEGORY_SONIC = "Sonic Screwdriver";
    public static final String CATEGORY_FIREWORK = "Firework Launcher";
    public static final String CATEGORY_ANTENNA = "Communications Tower";
    public static final String CATEGORY_PLAYER_DETECTOR = "Player Detector";
    public static final String CATEGORY_TURTLE_TELEPORT = "Turtle Teleport";
    public static final String CATEGORY_MINI_ANTENNA = "Communications Antenna";
    public static final String CATEGORY_UPGRADE_FURNACE = "Furnace Turtle";
    public static final String CATEGORY_UPGRADE_DENSITY = "Density Scanning Turtle";

    // Turtle Upgrade ID
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

    // Feature enabled
    public static boolean enableSonic;
    public static boolean enableChatBox;
    public static boolean enablePrinter;
    public static boolean enableKeyboard;
    public static boolean enableIronNote;
    public static boolean enablePlayerDetector;
    public static boolean enableAntenna;
    public static boolean enableTurtleTeleport;
    public static boolean enableMiniAntenna;
    public static boolean enableFireworkLauncher;
    public static boolean enableComputerCrafter;

    public static boolean enableUpgradeShears;
    public static boolean enableUpgradeCompass;
    public static boolean enableUpgradeSolar;
    public static boolean enableUpgradeIgniter;
    public static boolean enableUpgradeFurnace;
    public static boolean enableUpgradeFeeder;
    public static boolean enableUpgradeOreScanner;

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
    public static boolean antennaKeepsChunkLoaded;

    // Turtle Teleport settings
    public static double fuelMultiplier = 1.5;

    // Mini Antenna settings
    public static int miniAntennaRange = 650;
    public static int miniAntennaRangeStorm = 400;
    public static int miniAntennaMessageDelay = 3;

    // Upgrade Furnace Turtle
    public static int upgradeFurnaceFuelConsumption = 20;

    public static String userDensityMappings = "";

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

    public static Configuration config;

    private static ConfigHandler INSTANCE;

    public static ConfigHandler instance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("ConfigHandler must be initialised");
        }
        return INSTANCE;
    }

    private ConfigHandler(File file) {
        config = new Configuration(file);
        doConfiguration();
    }

    public static ConfigHandler init(File file) {
        if (INSTANCE != null) {
            throw new IllegalStateException("ConfigHandler already initialised");
        }

        return INSTANCE = new ConfigHandler(file);
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(ModInfo.ID)) {
            LogUtils.info("Refreshing the config file...");
            doConfiguration();
        }
    }

    private void doConfiguration() {
        LogUtils.info("Loading MoarPeripherals configuration file");

        // Feature enabled
        enableSonic = getEnabled(CATEGORY_SONIC);
        enableChatBox = getEnabled(CATEGORY_CHAT_BOX);
        enablePrinter = false; // getEnabled(CATEGORY_PRINTER);
        enableKeyboard = getEnabled(CATEGORY_KEYBOARD);
        enableIronNote = getEnabled(CATEGORY_IRON_NOTE);
        enablePlayerDetector = getEnabled(CATEGORY_PLAYER_DETECTOR);
        enableAntenna = getEnabled(CATEGORY_ANTENNA);
        enableTurtleTeleport = getEnabled(CATEGORY_TURTLE_TELEPORT);
        enableMiniAntenna = getEnabled(CATEGORY_MINI_ANTENNA);
        enableFireworkLauncher = getEnabled(CATEGORY_FIREWORK);
        enableComputerCrafter = getEnabled("Computer Crafter");

        // Turtle upgrade settings
        enableUpgradeShears = getUpgradeEnabled("Shears Turtle");
        enableUpgradeCompass = getUpgradeEnabled("Compass Turtle");
        enableUpgradeSolar = getUpgradeEnabled("Solar Turtle");
        enableUpgradeIgniter = getUpgradeEnabled("Igniter Turtle");
        enableUpgradeFurnace = getUpgradeEnabled(CATEGORY_UPGRADE_FURNACE);
        enableUpgradeFeeder = getUpgradeEnabled("Feeding Turtle");
        enableUpgradeOreScanner = getUpgradeEnabled(CATEGORY_UPGRADE_DENSITY);

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
        antennaRange = getInt(CATEGORY_ANTENNA, "towerRange", antennaRange, "The range in blocks the BitNet Communications Tower can transmit");
        antennaRangeStorm = getInt(CATEGORY_ANTENNA, "towerRangeStorm", antennaRangeStorm, "The range in blocks the BitNet Communications Tower can transmit during a storm");
        antennaMessageDelay = getInt(CATEGORY_ANTENNA, "towerMessageDelay", antennaMessageDelay, "The delay (in ticks) that the BitNet Communications Tower takes to send a message per 100 block distance (rounded up).");
        antennaKeepsChunkLoaded = getBoolean(CATEGORY_ANTENNA, "keepChunkLoaded", "Whether a BitNet Communications Tower should keep the chunk it resides in loaded");

        // Turtle Teleport settings
        fuelMultiplier = getDouble(CATEGORY_TURTLE_TELEPORT, "fuelMultiplier", fuelMultiplier, "The multiplier for the fuel consumption to teleport the Turtle. Formula: Euclidean distance * multiplier");

        // Upgrade Furnace Turtle settings
        upgradeFurnaceFuelConsumption = getInt(CATEGORY_UPGRADE_FURNACE, "fuelConsumption", upgradeFurnaceFuelConsumption, "The fuel consumption per item to smelt items in the Furnace Turtle Upgrade (Minimum value: 10)");
        upgradeFurnaceFuelConsumption = Math.max(upgradeFurnaceFuelConsumption, 10);
        config.get(CATEGORY_UPGRADE_FURNACE, "fuelConsumption", upgradeFurnaceFuelConsumption).setToDefault();

        // Mini Antenna settings
        miniAntennaRange = getInt(CATEGORY_MINI_ANTENNA, "miniAntennaRange", miniAntennaRange, "The range in blocks the BitNet Mini Antenna can transmit");
        miniAntennaRangeStorm = getInt(CATEGORY_MINI_ANTENNA, "miniAntennaRangeStorm", miniAntennaRange, "The range in blocks the BitNet Mini Antenna can transmit during a storm");
        miniAntennaMessageDelay = getInt(CATEGORY_MINI_ANTENNA, "miniAntennaMessageDelay", miniAntennaMessageDelay, "The delay (in ticks) that the BitNet Mini Antenna takes to send a message per 100 block distance (rounded up).");

        // Density Scanning settings
        userDensityMappings = config.getString("customDensity", CATEGORY_UPGRADE_DENSITY, userDensityMappings,
                "Add density mappings (semi-colon separated) for mod items to the Density Scanner in the format [modId]:[blockName]@[density]; " +
                        "\nsingle mapping example: MoarPeripherals:blockIronNote@5.45" +
                        "\nmulti-mapping example:  MoarPeripherals:blockIronNote@5.45;MoarPeripherals:blockChatBox@4.45"
        ).trim();
        if (userDensityMappings.toLowerCase().contains("minecraft:")) {
            throw new RuntimeException("Minecraft blocks cannot have a custom density mapping");
        }

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

    private static double getDouble(String cat, String key, double defDbl, String desc) {
        return config.get(cat, key, defDbl, desc).getDouble();
    }

    private static boolean getEnabled(String key) {
        return getBoolean(CATEGORY_ENABLED, key, String.format(ENABLEFORMAT, key));
    }

    private static boolean getUpgradeEnabled(String key) {
        return getBoolean(CATEGORY_UPGRADE, key, String.format(ENABLEFORMAT, key));
    }

}