/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts;

import net.minecraft.entity.player.EntityPlayer;
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
        setOwnerFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("owner", owner);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("owner", owner);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
        setOwnerFromNBT(packet.data);
    }

    public final void setOwner(String username) {
        owner = username;
    }

    public final String getOwner() {
        return owner;
    }

    public boolean canPlayerAccess(EntityPlayer player) {
        return player != null;
    }

    private void setOwnerFromNBT(NBTTagCompound tag) {
        owner = tag.getString("owner");
    }

}
