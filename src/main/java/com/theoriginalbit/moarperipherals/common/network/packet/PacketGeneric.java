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
package com.theoriginalbit.moarperipherals.common.network.packet;

import cpw.mods.fml.common.network.Player;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.*;

public abstract class PacketGeneric {

    public int[] intData;
    public byte[] byteData;
    public char[] charData;
    public float[] floatData;
    public double[] doubleData;
    public String[] stringData;
    public NBTTagCompound nbtData;
    protected int packetType;
    protected int subPacketType;

    public PacketGeneric(int packetType) {
        this(packetType, 0);
    }

    public PacketGeneric(int packetType, int subPacketType) {
        this.packetType = packetType;
        this.subPacketType = subPacketType;
    }

    public void handlePacket(byte[] bytes, Player player) throws Exception {
        DataInputStream data = new DataInputStream(new ByteArrayInputStream(bytes));
        readData(data);
    }

    public Packet toPacket() {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(stream);

        try {
            writeData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        packet.channel = "moarp";
        packet.data = stream.toByteArray();
        packet.length = packet.data.length;
        return packet;
    }

    protected final void writeData(DataOutputStream stream) throws IOException {
        stream.writeByte(packetType);
        stream.writeByte(subPacketType);

        // Write any string data
        if (stringData != null) {
            stream.writeInt(stringData.length);
            for (String str : stringData) {
                stream.writeUTF(str);
            }
        } else {
            stream.writeInt(0);
        }

        // Write any integer data
        if (intData != null) {
            stream.writeInt(intData.length);
            for (int i : intData) {
                stream.writeInt(i);
            }
        } else {
            stream.writeInt(0);
        }

        // Write any byte data
        if (byteData != null) {
            stream.writeInt(byteData.length);
            stream.write(byteData);
        } else {
            stream.writeInt(0);
        }

        // Write any char data
        if (charData != null) {
            stream.writeInt(charData.length);
            for (char c : charData) {
                stream.writeChar(c);
            }
        } else {
            stream.writeInt(0);
        }

        // Write any float data
        if (floatData != null) {
            stream.writeInt(floatData.length);
            for (float f : floatData) {
                stream.writeFloat(f);
            }
        } else {
            stream.writeInt(0);
        }

        // Write any double data
        if (doubleData != null) {
            stream.writeInt(doubleData.length);
            for (double d : doubleData) {
                stream.writeDouble(d);
            }
        } else {
            stream.writeInt(0);
        }

        // Write any NBT data
        if (nbtData != null) {
            stream.writeBoolean(true);
            CompressedStreamTools.writeCompressed(nbtData, stream);
        } else {
            stream.writeBoolean(false);
        }
    }

    protected final void readData(DataInputStream stream) throws IOException {
        packetType = stream.readByte();
        subPacketType = stream.readByte();

        int nStr = stream.readInt();
        if (nStr == 0) {
            stringData = null;
        } else {
            stringData = new String[nStr];
            for (int i = 0; i < nStr; ++i) {
                stringData[i] = stream.readUTF();
            }
        }

        int nInt = stream.readInt();
        intData = new int[nInt];
        if (nInt > 0) {
            for (int i = 0; i < nInt; ++i) {
                intData[i] = stream.readInt();
            }
        }

        int nByte = stream.readInt();
        byteData = new byte[nByte];
        if (nByte > 0) {
            if (stream.read(byteData) != nByte) {
                throw new RuntimeException("Something bad happened, we didn't read the same amount of bytes we should have");
            }
        }

        int nChar = stream.readInt();
        charData = new char[nChar];
        if (nChar > 0) {
            for (int i = 0; i < nChar; ++i) {
                charData[i] = stream.readChar();
            }
        }

        int nFloat = stream.readInt();
        floatData = new float[nFloat];
        if (nFloat > 0) {
            for (int i = 0; i < nFloat; ++i) {
                floatData[i] = stream.readFloat();
            }
        }

        int nDouble = stream.readInt();
        doubleData = new double[nDouble];
        if (nDouble > 0) {
            for (int i = 0; i < nDouble; ++i) {
                doubleData[i] = stream.readDouble();
            }
        }

        boolean wasNBT = stream.readBoolean();
        if (wasNBT) {
            nbtData = CompressedStreamTools.readCompressed(stream);
        }
    }

}
