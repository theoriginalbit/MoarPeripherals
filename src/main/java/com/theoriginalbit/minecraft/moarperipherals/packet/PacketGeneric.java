package com.theoriginalbit.minecraft.moarperipherals.packet;

import cpw.mods.fml.common.network.Player;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.*;

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
public abstract class PacketGeneric {

    public int[] intData;
    public byte[] byteData;
    public char[] charData;
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
        if (nInt == 0) {
            intData = null;
        } else {
            intData = new int[nInt];
            for (int i = 0; i < nInt; ++i) {
                intData[i] = stream.readInt();
            }
        }

        int nByte = stream.readInt();
        if (nByte == 0) {
            byteData = null;
        } else {
            byteData = new byte[nByte];
            stream.read(byteData);
        }

        int nChar = stream.readInt();
        if (nChar == 0) {
            charData = null;
        } else {
            charData = new char[nChar];
            for (int i = 0; i < nChar; ++i) {
                charData[i] = stream.readChar();
            }
        }

        boolean wasNBT = stream.readBoolean();
        if (!wasNBT) {
            nbtData = null;
        } else {
            nbtData = CompressedStreamTools.readCompressed(stream);
        }
    }

}
