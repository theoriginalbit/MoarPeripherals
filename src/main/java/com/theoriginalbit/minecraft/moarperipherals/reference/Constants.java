package com.theoriginalbit.minecraft.moarperipherals.reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

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
public final class Constants {

    public static final class RENDER_ID {
        public static int ANTENNA = -1;
        public static int ANTENNA_CTRLR = -1;
    }

    public static final class GUI {
        public static final LocalisationStore KEYBOARD_EXIT = new LocalisationStore("moarperipherals.gui.keyboard.exit");
    }

    public static final class TOOLTIPS {
        public static final LocalisationStore PAIRED = new LocalisationStore("moarperipherals.tooltip.pairable.paired");
        public static final LocalisationStore NOT_PAIRED = new LocalisationStore("moarperipherals.tooltip.pairable.notPaired");
        public static final LocalisationStore SHIFT_INFO = new LocalisationStore("moarperipherals.tooltip.generic.information");
        public static final LocalisationStore INK_CONTENTS = new LocalisationStore("moarperipherals.tooltip.inkCartridge.contents");
        public static final LocalisationStore INK_LEVEL = new LocalisationStore("moarperipherals.tooltip.inkCartridge.level");
        public static final LocalisationStore INK_CYAN = new LocalisationStore("moarperipherals.tooltip.printer.ink.cyan");
        public static final LocalisationStore INK_MAGENTA = new LocalisationStore("moarperipherals.tooltip.printer.ink.magenta");
        public static final LocalisationStore INK_YELLOW = new LocalisationStore("moarperipherals.tooltip.printer.ink.yellow");
        public static final LocalisationStore INK_BLACK = new LocalisationStore("moarperipherals.tooltip.printer.ink.black");
        public static final LocalisationStore INK_EMPTY = new LocalisationStore("moarperipherals.tooltip.printer.ink.empty");
    }

    public static final class UPGRADE {
        public static final LocalisationStore CHATBOX = new LocalisationStore("upgrade.moarperipherals.adjective.chatbox");
        public static final LocalisationStore IRONNOTE = new LocalisationStore("upgrade.moarperipherals.adjective.ironnote");
        public static final LocalisationStore DICTIONARY = new LocalisationStore("upgrade.moarperipherals.adjective.dictionary");
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
        public static final TextureStore INK_CARTRIDGE_C = new TextureStore("textures/models/items/inkCartridge/InkCartridgeC");
        public static final TextureStore INK_CARTRIDGE_M = new TextureStore("textures/models/items/inkCartridge/InkCartridgeM");
        public static final TextureStore INK_CARTRIDGE_Y = new TextureStore("textures/models/items/inkCartridge/InkCartridgeY");
        public static final TextureStore INK_CARTRIDGE_K = new TextureStore("textures/models/items/inkCartridge/InkCartridgeK");
        public static final TextureStore INK_CARTRIDGE_E = new TextureStore("textures/models/items/inkCartridge/InkCartridgeE");
        public static final TextureStore SONIC_10_0 = new TextureStore("textures/models/items/sonic/Sonic10_0");
        public static final TextureStore SONIC_10_1 = new TextureStore("textures/models/items/sonic/Sonic10_1");
        public static final TextureStore SONIC_11 = new TextureStore("textures/models/items/sonic/Sonic11");
        public static final TextureStore ANTENNA = new TextureStore("textures/models/blocks/antenna/Antenna");
    }

    public enum COLOR {
        BLACK(0x191919, new int[]{0, 0, 0, 90}),
        RED(0xCC4C4C, new int[]{0, 63, 63, 20}),
        GREEN(0x57A64E, new int[]{48, 0, 53, 35}),
        BROWN(0x7F664C, new int[]{0, 20, 40, 50}),
        BLUE(0x3366CC, new int[]{75, 50, 0, 20}),
        PURPLE(0xB266E5, new int[]{22, 55, 0, 10}),
        CYAN(0x4C99B2, new int[]{57, 14, 0, 30}),
        LIGHTGRAY(0x999999, new int[]{0, 0, 0, 40}),
        GRAY(0x4C4C4C, new int[]{0, 0, 0, 70}),
        PINK(0xF2B2CC, new int[]{0, 26, 16, 5}),
        LIME(0x7FCC19, new int[]{38, 0, 88, 20}),
        YELLOW(0xDEDE6C, new int[]{0, 0, 51, 13}),
        LIGHTBLUE(0x99B2F2, new int[]{37, 26, 0, 5}),
        MAGENTA(0xE57FD8, new int[]{0, 45, 6, 10}),
        ORANGE(0xF2B233, new int[]{0, 26, 79, 5}),
        WHITE(0xF0F0F0, new int[]{0, 0, 0, 6});

        private final int hex;
        private final int[] cmyk;

        private COLOR(int colorHex, int[] colorCMYK) {
            hex = colorHex;
            cmyk = colorCMYK;
        }

        public int getHex() {
            return hex;
        }

        public int[] getCMYK() {
            return cmyk;
        }

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
