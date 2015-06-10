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
package com.moarperipherals.tile;

import com.moarperipherals.block.IBlockEventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileMoarP extends TileEntity implements IBlockEventHandler {
    private int anim;
    private boolean changed;
    private String owner = "[NONE]";

    @Override
    public synchronized void updateEntity() {
        if(changed) {
            updateBlock();
            changed = false;
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("owner")) {
            owner = tag.getString("owner");
        }
        if (tag.hasKey("anim")) {
            anim = tag.getInteger("anim");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setString("owner", owner);
        tag.setInteger("anim", anim);
    }

    @Override
    public final Packet getDescriptionPacket() {
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, getDescriptionNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        final NBTTagCompound tag = packet.func_148857_g();
        readDescriptionNBT(tag);
    }

    @Override
    public void blockPlaced() {
        // no-op
    }

    @Override
    public void blockBroken(int x, int y, int z) {
        // no-op
    }

    @Override
    public void neighborChanged() {
        // no-op
    }

    @Override
    public boolean blockActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        return false; // no-op
    }

    public final synchronized int getAnim() {
        return anim;
    }

    protected final synchronized void setAnim(int anim) {
        if (this.anim != anim) {
            this.anim = anim;
            changed = true;
        }
    }

    protected void updateAnim() {
    }

    protected final void updateBlock() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this);
    }

    protected NBTTagCompound getDescriptionNBT() {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setString("owner", owner);
        tag.setInteger("anim", anim);
        return tag;
    }

    protected void readDescriptionNBT(NBTTagCompound tag) {
        if (tag.hasKey("owner")) {
            owner = tag.getString("owner");
        }
        if (tag.hasKey("anim")) {
            anim = tag.getInteger("anim");
        }
    }

    public final String getOwner() {
        return owner;
    }

    public final void setOwner(String username) {
        owner = username;
    }
}
