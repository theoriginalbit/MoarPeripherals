/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.gui;

public enum GuiType {

    KEYBOARD, CRAFTER;

    public static GuiType valueOf(int id) {
        for (GuiType gui : values()) {
            if (gui.ordinal() == id) {
                return gui;
            }
        }
        return null;
    }

}