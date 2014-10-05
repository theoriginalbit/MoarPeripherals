/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.network.message;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageGeneric implements IMessage {

    public int[] intData;
    public byte[] byteData;
    public char[] charData;
    public String[] stringData;
    public NBTTagCompound nbtData;

    @Override
    public void toBytes(ByteBuf buf) {
        // Write any string data
        if (stringData != null) {
            buf.writeInt(stringData.length);
            for (String str : stringData) {
                ByteBufUtils.writeUTF8String(buf, str);
            }
        } else {
            buf.writeInt(0);
        }

        // Write any integer data
        if (intData != null) {
            buf.writeInt(intData.length);
            for (int i : intData) {
                buf.writeInt(i);
            }
        } else {
            buf.writeInt(0);
        }

        // Write any byte data
        if (byteData != null) {
            buf.writeInt(byteData.length);
            buf.writeBytes(byteData);
        } else {
            buf.writeInt(0);
        }

        // Write any char data
        if (charData != null) {
            buf.writeInt(charData.length);
            for (char c : charData) {
                buf.writeChar(c);
            }
        } else {
            buf.writeInt(0);
        }

        // Write any NBT data
        if (nbtData != null) {
            buf.writeBoolean(true);
            ByteBufUtils.writeTag(buf, nbtData);
        } else {
            buf.writeBoolean(false);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int nStr = buf.readInt();
        if (nStr == 0) {
            stringData = null;
        } else {
            stringData = new String[nStr];
            for (int i = 0; i < nStr; ++i) {
                stringData[i] = ByteBufUtils.readUTF8String(buf);
            }
        }

        int nInt = buf.readInt();
        if (nInt == 0) {
            intData = null;
        } else {
            intData = new int[nInt];
            for (int i = 0; i < nInt; ++i) {
                intData[i] = buf.readInt();
            }
        }

        int nByte = buf.readInt();
        if (nByte == 0) {
            byteData = null;
        } else {
            byteData = new byte[nByte];
            buf.readBytes(byteData);
        }

        int nChar = buf.readInt();
        if (nChar == 0) {
            charData = null;
        } else {
            charData = new char[nChar];
            for (int i = 0; i < nChar; ++i) {
                charData[i] = buf.readChar();
            }
        }

        boolean wasNBT = buf.readBoolean();
        if (wasNBT) {
            nbtData = ByteBufUtils.readTag(buf);
        }
    }

}
