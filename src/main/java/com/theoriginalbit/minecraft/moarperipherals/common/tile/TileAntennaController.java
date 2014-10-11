/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.ComputerList;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.api.BitNetMessage;
import com.theoriginalbit.minecraft.moarperipherals.api.IBitNetTower;
import com.theoriginalbit.minecraft.moarperipherals.common.block.BlockAntenna;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.minecraft.moarperipherals.server.chunk.ChunkLoadingCallback;
import com.theoriginalbit.minecraft.moarperipherals.server.chunk.TicketManager;
import com.theoriginalbit.minecraft.moarperipherals.server.chunk.IChunkLoader;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.common.registry.BitNetRegistry;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.BlockNotifyFlags;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.LogUtils;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.ArrayList;
import java.util.UUID;

@LuaPeripheral("bitnet_tower")
public class TileAntennaController extends TileMoarP implements IPlaceAwareTile, IBreakAwareTile, IBitNetTower, IChunkLoader {

    private static final String EVENT_BITNET = "bitnet_message";
    private final ArrayList<UUID> receivedMessages = Lists.newArrayList();
    private ForgeChunkManager.Ticket chunkTicket;
    private boolean complete = false;
    private boolean registered = false;

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
    public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
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
            final int id = worldObj.getBlockId(xCoord, yCoord + y, zCoord);
            // only notify antenna blocks
            if (Block.blocksList[id] instanceof BlockAntenna) {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 0, BlockNotifyFlags.ALL);
            }
        }

        // mark this block for an update too
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, BlockNotifyFlags.ALL);

        unregisterTower();

        complete = false;
    }

    /*
     * @LuaPeripheral implementation
     */

    @ComputerList
    public ArrayList<IComputerAccess> computers;

    @LuaFunction
    @SuppressWarnings("unused")
    public boolean isTowerComplete() {
        return complete;
    }

    @LuaFunction(isMultiReturn = true)
    @SuppressWarnings("unused")
    public Object[] transmit(Object payload) {
        if (isTowerComplete()) {
            BitNetRegistry.transmit(this, new BitNetMessage(payload));
            return new Object[]{true};
        }
        return new Object[]{false, "BitNet Communications Tower incomplete."};
    }

    /*
     * IPlaceAwareTile implementation
     */

    @Override
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z) {
        onBlockAdded();
    }

    /*
     * IBreakAwareTile implementation
     */

    @Override
    public void onBreak(int x, int y, int z) {
        onBlockRemoved();
    }

    /*
     * IChunkLoader implementation
     */

    @Override
    public ChunkCoordIntPair getChunkCoord() {
        return new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4);
    }

    /*
     * IBitNetTower implementation
     */

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public Vec3 getWorldPosition() {
        return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
    }

    /**
     * Invoked when this tower is in range of a BitNet message
     */
    @Override
    public void receive(BitNetMessage message) {
        if (!receivedMessages.contains(message.getMessageId())) {
            // there is a computer connected, let it handle the message
            if (computers != null && computers.size() > 0) {
                LogUtils.debug("BitNet Comms Tower at %d %d %d has computer(s) connected, queueing BitNet message for them to handle...", xCoord, yCoord, zCoord);
                for (IComputerAccess comp : computers) {
                    comp.queueEvent(EVENT_BITNET, new Object[]{comp.getAttachmentName(), message.getPayload(), message.getDistanceTravelled()});
                }
            // there was no connected computer, this is now a repeating tower
            } else {
                LogUtils.debug("BitNet Comms Tower at %d %d %d has no computer(s) connected, acting as a repeating tower...", xCoord, yCoord, zCoord);
                BitNetRegistry.transmit(this, message);
            }
            receivedMessages.add(message.getMessageId());
        } else {
            LogUtils.debug("BitNet Communications Tower at %d %d %d received a previously received message...", xCoord, yCoord, zCoord);
        }
    }

    /*
     * Private members
     */

    private void checkStructure() {
        for (int y = 1; y < 13; ++y) {
            int id = worldObj.getBlockId(xCoord, yCoord + y, zCoord);
            if (id != ConfigHandler.blockIdAntenna) {
                return;
            }
        }

        if (worldObj.getBlockId(xCoord, yCoord + 13, zCoord) != ConfigHandler.blockIdAntennaMiniCell) {
            return;
        }

        if (worldObj.getBlockId(xCoord, yCoord + 14, zCoord) != ConfigHandler.blockIdAntennaCell) {
            return;
        }
        if (worldObj.getBlockId(xCoord, yCoord + 15, zCoord) != ConfigHandler.blockIdAntennaCell) {
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
            BitNetRegistry.registerTower(this);
            if (ConfigHandler.antennaKeepChunkLoaded && chunkTicket == null) {
                chunkTicket = ChunkLoadingCallback.ticketList.remove(this);
                if (chunkTicket == null) {
                    LogUtils.info(String.format("Requesting chunk loading ticket for BitNet Communications Tower at %d %d %d", xCoord, yCoord, zCoord));
                    chunkTicket = TicketManager.requestTicket(worldObj, xCoord, yCoord, zCoord);
                    if (chunkTicket.isPlayerTicket()) {
                        LogUtils.warning(String.format("The returned ticket is a player ticket for player %s", chunkTicket.getPlayerName()));
                    }
                    ForgeChunkManager.forceChunk(chunkTicket, getChunkCoord());
                } else {
                    LogUtils.info(String.format("A chunk loading ticket was found from server start for the BitNet Communications Tower at %d %d %d", xCoord, yCoord, zCoord));
                }
            }
        }
        registered = true;
    }

    private void unregisterTower() {
        if (!worldObj.isRemote) {
            BitNetRegistry.deregisterTower(this);
            // if there was a chunk loading ticket and the server isn't just stopping
            if (ConfigHandler.antennaKeepChunkLoaded && chunkTicket != null && !MoarPeripherals.isServerStopping) {
                LogUtils.info(String.format("Releasing Ticket for the BitNet Communications Tower at %d %d %d", xCoord, yCoord, zCoord));
                ForgeChunkManager.unforceChunk(chunkTicket, getChunkCoord());
                TicketManager.releaseTicket(chunkTicket);
                chunkTicket = null;
            }
        }
        registered = false;
    }

}
