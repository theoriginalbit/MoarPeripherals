/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile.monitor;

import net.minecraft.network.Packet;

public class Terminal {

    private int width;
    private int height;
    private int cursorX = 0;
    private int cursorY = 0;

    public Terminal(int terminalWidth, int terminalHeight) {
        width = terminalWidth;
        height = terminalHeight;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public Packet getDiffPacket() {
        return null;
    }

}