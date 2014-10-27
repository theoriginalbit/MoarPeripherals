/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public final class Constants {

    public static final class RENDER_ID {
        public static int ANTENNA = -1;
        public static int ANTENNA_CTRLR = -1;
    }

    public static final class GUI {
        public static final LocalisationStore KEYBOARD_EXIT = new LocalisationStore("moarperipherals.gui.keyboard.exit");
        public static final LocalisationStore FIREWORKS = new LocalisationStore("tile.moarperipherals:fireworks.name");
        public static final LocalisationStore CRAFTER = new LocalisationStore("tile.moarperipherals:crafter.name");
    }

    public static final class TOOLTIPS {
        public static final LocalisationStore PAIRED = new LocalisationStore("tooltip.moarperipherals.pairable.paired");
        public static final LocalisationStore NOT_PAIRED = new LocalisationStore("tooltip.moarperipherals.pairable.notPaired");
        public static final LocalisationStore SHIFT_INFO = new LocalisationStore("tooltip.moarperipherals.generic.information");
    }

    public static final class UPGRADE {
        public static final LocalisationStore CHATBOX = new LocalisationStore("upgrade.moarperipherals:adjective.chatbox");
        public static final LocalisationStore IRONNOTE = new LocalisationStore("upgrade.moarperipherals:adjective.ironnote");
        public static final LocalisationStore DICTIONARY = new LocalisationStore("upgrade.moarperipherals:adjective.dictionary");
        public static final LocalisationStore SHEARS = new LocalisationStore("upgrade.moarperipherals:adjective.shears");
        public static final LocalisationStore COMPASS = new LocalisationStore("upgrade.moarperipherals:adjective.compass");
        public static final LocalisationStore FURNACE = new LocalisationStore("upgrade.moarperipherals:adjective.furnace");
        public static final LocalisationStore SOLAR = new LocalisationStore("upgrade.moarperipherals:adjective.solar");
        public static final LocalisationStore IGNITER = new LocalisationStore("upgrade.moarperipherals:adjective.pyro");
    }

    public static final class CHAT {
        public static final LocalisationStore CHAT_PAIRED = new LocalisationStore("moarperipherals.chat.paired");
        public static final LocalisationStore CHAT_NOT_PAIRED = new LocalisationStore("moarperipherals.chat.notPaired");
        public static final LocalisationStore SONIC_WOOD = new LocalisationStore("moarperipherals.chat.sonic.wood");
    }

    public static final class NBT {
        public static final String TARGET_X = "targetX";
        public static final String TARGET_Y = "targetY";
        public static final String TARGET_Z = "targetZ";
    }

    public static final class TEXTURES_MODEL {
        public static final TextureStore KEYBOARD = new TextureStore("textures/models/blocks/keyboard/Keyboard");
        public static final TextureStore KEYBOARD_ON = new TextureStore("textures/models/blocks/keyboard/Keyboard_On");
        public static final TextureStore KEYBOARD_LOST = new TextureStore("textures/models/blocks/keyboard/Keyboard_Lost");
        public static final TextureStore PRINTER = new TextureStore("textures/models/blocks/printer/Printer_Printing");
        public static final TextureStore PRINTER_IDLE = new TextureStore("textures/models/blocks/printer/Printer_Idle");
        public static final TextureStore PRINTER_ERROR = new TextureStore("textures/models/blocks/printer/Printer_Error");
        public static final TextureStore PRINTER_PRINT_ERROR = new TextureStore("textures/models/blocks/printer/Printer_Printing_Error");
        public static final TextureStore SONIC_10_0 = new TextureStore("textures/models/items/sonic/Sonic10_0");
        public static final TextureStore SONIC_10_1 = new TextureStore("textures/models/items/sonic/Sonic10_1");
        public static final TextureStore SONIC_10_TIPON = new TextureStore("textures/models/items/sonic/Sonic10_On");
        public static final TextureStore SONIC_10_TIPOFF = new TextureStore("textures/models/items/sonic/Sonic10_Off");
        public static final TextureStore SONIC_11_0 = new TextureStore("textures/models/items/sonic/Sonic11_0");
        public static final TextureStore SONIC_11_1 = new TextureStore("textures/models/items/sonic/Sonic11_1");
        public static final TextureStore SONIC_11_TIPON = new TextureStore("textures/models/items/sonic/Sonic11_On");
        public static final TextureStore SONIC_11_TIPOFF = new TextureStore("textures/models/items/sonic/Sonic11_Off");
        public static final TextureStore ANTENNA = new TextureStore("textures/models/blocks/antenna/Antenna");
        public static final TextureStore MINI_ANTENNA = new TextureStore("textures/models/blocks/antenna/MiniAntenna");
    }

    public static final class LocalisationStore {

        private final String rawName;

        private LocalisationStore(String name) {
            rawName = name;
        }

        public String getLocalised() {
            return StatCollector.translateToLocal(rawName);
        }

        public String getFormattedLocalised(Object... args) {
            return StatCollector.translateToLocalFormatted(rawName, args);
        }

    }

    public static final class TextureStore {

        private final String pathToResource;
        private final ResourceLocation resource;

        private TextureStore(String path) {
            this(path, true);
        }

        private TextureStore(String path, boolean load) {
            pathToResource = path + ".png";
            resource = load ? new ResourceLocation(ModInfo.RESOURCE_DOMAIN, pathToResource) : null;
        }

        public final ResourceLocation getResourceLocation() {
            return resource;
        }

        public final String getPath() {
            return pathToResource;
        }
    }

}
