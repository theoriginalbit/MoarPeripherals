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
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetRelay;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetWorld;
import com.theoriginalbit.moarperipherals.common.bitnet.BitNetUniverse;
import com.theoriginalbit.moarperipherals.common.block.BlockAntenna;
import com.theoriginalbit.moarperipherals.common.block.BlockAntennaCell;
import com.theoriginalbit.moarperipherals.common.block.BlockAntennaController;
import com.theoriginalbit.moarperipherals.common.block.BlockAntennaMiniCell;
import com.theoriginalbit.moarperipherals.common.chunk.ChunkLoadingCallback;
import com.theoriginalbit.moarperipherals.common.chunk.IChunkLoader;
import com.theoriginalbit.moarperipherals.common.chunk.TicketManager;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.base.TileMoarP;
import com.theoriginalbit.moarperipherals.common.util.BlockNotifyFlags;
import com.theoriginalbit.moarperipherals.common.util.LogUtil;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.ArrayList;

@LuaPeripheral("bitnet_tower")
public class TileAntennaController extends TileMoarP implements IBitNetRelay, IChunkLoader {
    private static final String EVENT_BITNET = "bitnet_message";
    private ForgeChunkManager.Ticket chunkTicket;
    private boolean registered = false;
    private boolean complete = false;
    private IBitNetWorld network;

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        complete = tag.getBoolean("structureComplete");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("structureComplete", complete);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        checkStructure();
    }

    @Override
    public void updateEntity() {
        if (complete && !registered) {
            registerTower();
        }
    }

    public void onBlockAdded() {
        // check if the multi-block is complete
        complete = false;
        registered = false;
        checkStructure();
    }

    public void onBlockRemoved() {
        // tell all the blocks in the structure to become visible again
        for (int y = 1; y < 16; ++y) {
            // only notify antenna blocks
            if (worldObj.getBlock(xCoord, yCoord + y, zCoord) instanceof BlockAntenna) {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 0, BlockNotifyFlags.ALL);
            }
        }

        // mark this block for an update too
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, BlockNotifyFlags.ALL);

        unregisterTower();

        complete = false;
    }

    @Computers.List
    public ArrayList<IComputerAccess> computers;

    @LuaFunction
    public boolean isTowerComplete() {
        return complete;
    }

    @LuaFunction
    public boolean isOpen(int channel) throws LuaException {
        return isTowerComplete() && network.isChannelOpen(this, channel);
    }

    @LuaFunction
    public boolean open(int channel) throws LuaException {
        return isTowerComplete() && network.openChannel(this, channel);
    }

    @LuaFunction
    public boolean close(int channel) throws LuaException {
        return isTowerComplete() && network.closeChannel(this, channel);
    }

    @LuaFunction
    public boolean transmit(int sendChannel, int replyChannel, Object payload) throws LuaException {
        if (isTowerComplete()) {
            network.transmit(this, new BitNetMessage(sendChannel, replyChannel, payload));
            return true;
        }
        return false;
    }

    @Override
    public void blockPlaced() {
        onBlockAdded();
    }

    @Override
    public void blockBroken(int x, int y, int z) {
        onBlockRemoved();
    }

    @Override
    public ChunkCoordIntPair getChunkCoord() {
        return new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4);
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public Vec3 getPosition() {
        return Vec3.createVectorHelper(xCoord, yCoord + 15, zCoord);
    }

    @Override
    public RelayType getRelayType() {
        return RelayType.LONG_RANGE;
    }

    @Override
    public void receive(BitNetMessage payload) {
        LogUtil.debug(String.format("BitNet Comms Tower at %d %d %d queueing message.", xCoord, yCoord, zCoord));
        for (IComputerAccess c : computers) {
            c.queueEvent(EVENT_BITNET, payload.getEventData(c));
        }
    }

    private void checkStructure() {
        // make sure that there are only 13 pole blocks
        for (int y = 1; y < 13; ++y) {
            final Block block = worldObj.getBlock(xCoord, yCoord + y, zCoord);
            if (!(block instanceof BlockAntenna) || block instanceof BlockAntennaCell ||
                    block instanceof BlockAntennaController || block instanceof BlockAntennaMiniCell) {
                return;
            }
        }

        if (!(worldObj.getBlock(xCoord, yCoord + 13, zCoord) instanceof BlockAntennaMiniCell)) {
            return;
        }

        if (!(worldObj.getBlock(xCoord, yCoord + 14, zCoord) instanceof BlockAntennaCell)) {
            return;
        }
        if (!(worldObj.getBlock(xCoord, yCoord + 15, zCoord) instanceof BlockAntennaCell)) {
            return;
        }

        // tell all the blocks in the structure to become invisible
        for (int y = 1; y < 16; ++y) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 1, BlockNotifyFlags.ALL);
        }

        // mark this block for an update
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, BlockNotifyFlags.ALL);

        complete = true;
    }

    private void registerTower() {
        if (!worldObj.isRemote) {
            network = BitNetUniverse.UNIVERSE.getBitNetWorld(worldObj);
            network.addRelay(this);
            if (ConfigData.antennaKeepsChunkLoaded && chunkTicket == null) {
                chunkTicket = ChunkLoadingCallback.ticketList.remove(this);
                if (chunkTicket == null) {
                    LogUtil.info(String.format("Requesting chunk loading ticket for BitNet Communications Tower at " +
                            "%d %d %d", xCoord, yCoord, zCoord));
                    chunkTicket = TicketManager.requestTicket(worldObj, xCoord, yCoord, zCoord);
                    if (chunkTicket.isPlayerTicket()) {
                        LogUtil.warn(String.format("The returned ticket is a player ticket for player %s",
                                chunkTicket.getPlayerName()));
                    }
                    ForgeChunkManager.forceChunk(chunkTicket, getChunkCoord());
                } else {
                    LogUtil.info(String.format("A chunk loading ticket was found from server start for the BitNet " +
                            "Communications Tower at %d %d %d", xCoord, yCoord, zCoord));
                }
            }
        }
        registered = true;
    }

    private void unregisterTower() {
        if (!worldObj.isRemote) {
            if (network != null) {
                network.removeRelay(this);
            }
            // if there was a chunk loading ticket and the server isn't just stopping
            if (ConfigData.antennaKeepsChunkLoaded && chunkTicket != null && !MoarPeripherals.isServerStopping) {
                LogUtil.info(String.format("Releasing Ticket for the BitNet Communications Tower at %d %d %d",
                        xCoord, yCoord, zCoord));
                ForgeChunkManager.unforceChunk(chunkTicket, getChunkCoord());
                TicketManager.releaseTicket(chunkTicket);
                chunkTicket = null;
            }
        }
        registered = false;
    }
}
