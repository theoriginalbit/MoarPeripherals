/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

class MessageGeneric implements IMessage {

    public int[] intData;
    public byte[] byteData;
    public char[] charData;
    public float[] floatData;
    public double[] doubleData;
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

        // Write any float data
        if (floatData != null) {
            buf.writeInt(floatData.length);
            for (float f : floatData) {
                buf.writeFloat(f);
            }
        } else {
            buf.writeInt(0);
        }

        // Write any double data
        if (doubleData != null) {
            buf.writeInt(doubleData.length);
            for (double d : doubleData) {
                buf.writeDouble(d);
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
        stringData = new String[nStr];
        if (nStr > 0) {
            for (int i = 0; i < nStr; ++i) {
                stringData[i] = ByteBufUtils.readUTF8String(buf);
            }
        }

        int nInt = buf.readInt();
        intData = new int[nInt];
        if (nInt > 0) {
            for (int i = 0; i < nInt; ++i) {
                intData[i] = buf.readInt();
            }
        }

        int nByte = buf.readInt();
        byteData = new byte[nByte];
        if (nByte > 0) {
            buf.readBytes(byteData);
        }

        int nChar = buf.readInt();
        charData = new char[nChar];
        if (nChar > 0) {
            for (int i = 0; i < nChar; ++i) {
                charData[i] = buf.readChar();
            }
        }

        int nFloat = buf.readInt();
        floatData = new float[nFloat];
        if (nFloat > 0) {
            for (int i = 0; i < nFloat; ++i) {
                floatData[i] = buf.readFloat();
            }
        }

        int nDouble = buf.readInt();
        doubleData = new double[nDouble];
        if (nDouble > 0) {
            for (int i = 0; i < nDouble; ++i) {
                doubleData[i] = buf.readDouble();
            }
        }

        boolean wasNBT = buf.readBoolean();
        if (wasNBT) {
            nbtData = ByteBufUtils.readTag(buf);
        }
    }

}
