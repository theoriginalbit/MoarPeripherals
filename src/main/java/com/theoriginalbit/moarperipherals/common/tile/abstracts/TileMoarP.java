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
package com.theoriginalbit.moarperipherals.common.tile.abstracts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileMoarP extends TileEntity {

    private String owner = "[NONE]";

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        owner = tag.getString("owner");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("owner", owner);
    }

    @Override
    public Packet getDescriptionPacket() {
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, getDescriptionNBT());
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        final NBTTagCompound tag = packet.data;
        readDescriptionNBT(tag);
    }

    protected NBTTagCompound getDescriptionNBT() {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString("owner", owner);
        return tag;
    }

    protected void readDescriptionNBT(NBTTagCompound tag) {
        owner = tag.getString("owner");
    }

    public final void setOwner(String username) {
        owner = username;
    }

    public final String getOwner() {
        return owner;
    }

}
