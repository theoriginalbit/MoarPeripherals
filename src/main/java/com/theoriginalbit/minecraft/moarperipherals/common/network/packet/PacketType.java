/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.network.packet;

public enum PacketType {

    IRON_NOTE;

    public static PacketType valueOf(int id) {
        for (PacketType type : values()) {
            if (type.ordinal() == id) {
                return type;
            }
        }
        return null;
    }

}