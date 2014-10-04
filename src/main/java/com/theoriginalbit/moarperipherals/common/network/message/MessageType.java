/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.network.message;

public enum MessageType {

    IRON_NOTE;

    public static MessageType valueOf(int id) {
        for (MessageType type : values()) {
            if (type.ordinal() == id) {
                return type;
            }
        }
        return null;
    }

}