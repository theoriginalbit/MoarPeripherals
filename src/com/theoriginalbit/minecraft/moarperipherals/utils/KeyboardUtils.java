package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

public final class KeyboardUtils {
	public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }

    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
}