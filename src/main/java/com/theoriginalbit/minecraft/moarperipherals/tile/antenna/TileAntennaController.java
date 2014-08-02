package com.theoriginalbit.minecraft.moarperipherals.tile.antenna;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.ComputerList;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ComputerCraftInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileMPBase;
import com.theoriginalbit.minecraft.moarperipherals.utils.BlockNotifyFlags;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

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
@LuaPeripheral("bitnet_tower")
public class TileAntennaController extends TileMPBase implements IPlaceAwareTile, IBreakAwareTile {

    private static final String EVENT_BITNET = "bitnet_message";

    private TileAntennaModem modemInterface;
    private final FauxComputer computer = new FauxComputer("top") {
        @Override
        public void queueEvent(String event, Object[] arguments) {
            // this message is coming from a modem attached to this structure
            if (isComplete() && arguments.length >= 3 && arguments[3] != null) {
                transmit(arguments[3]);
                queueFakeEvent(ArrayUtils.subarray(arguments, 1, arguments.length));
            }
        }
    };

    @ComputerList
    public ArrayList<IComputerAccess> computers;

    private boolean complete = false;

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z) {
        blockAdded();
    }

    @Override
    public void onBreak(int x, int y, int z) {
        blockRemoved();
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
        blockAdded();
        refreshModems();
    }

    public boolean isComplete() {
        return complete;
    }

    public void blockAdded() {
        System.out.println("Block added!");
        // check if the multi-block is complete
        complete = false;

        for (int y = 1; y < 13; ++y) {
            int id = worldObj.getBlockId(xCoord, yCoord + y, zCoord);
            if (id != Settings.blockIdAntenna) {
                return;
            }
        }

        if (worldObj.getBlockId(xCoord, yCoord + 13, zCoord) != Settings.blockIdAntennaModem) {
            return;
        } else {
            modemInterface = (TileAntennaModem) worldObj.getBlockTileEntity(xCoord, yCoord + 13, zCoord);
        }

        if (worldObj.getBlockId(xCoord, yCoord + 14, zCoord) != Settings.blockIdAntennaCell) {
            return;
        }
        if (worldObj.getBlockId(xCoord, yCoord + 15, zCoord) != Settings.blockIdAntennaCell) {
            return;
        }

        // tell all the blocks in the structure to become invisible
        for (int y = 1; y < 16; ++y) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 1, BlockNotifyFlags.ALL);
            // notify the TileEntity of change
            TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + y, zCoord);
            if (tile != null && tile instanceof TileAntenna) {
                ((TileAntenna) tile).connectToController(xCoord, yCoord, zCoord);
            }
        }

        // inform modem interface
        refreshModems();

        // mark this block for an update
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, BlockNotifyFlags.ALL);
        complete = true;
    }

    public void blockRemoved() {
        // tell all the blocks in the structure to become visible again
        for (int y = 1; y < 16; ++y) {
            // using the TileEntity to make sure only setting metadata of appropriate blocks
            TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + y, zCoord);
            if (tile != null && tile instanceof TileAntenna) {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 0, BlockNotifyFlags.ALL);
            }
        }

        // close all the modems if there are any
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 13, zCoord);
        if (tile != null && tile instanceof TileAntennaModem) {
            ((TileAntennaModem) tile).closeModems();
        }

        if (modemInterface != null) {
            modemInterface.structureDestroyed();
        }

        // mark this block for an update
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, BlockNotifyFlags.ALL);
        complete = false;
    }

    public void receiveMessage(Integer channel, Object payload, double distance) {
        // message received over bitnet
        for (IComputerAccess comp : computers) {
            comp.queueEvent(EVENT_BITNET, new Object[]{comp.getAttachmentName(), payload, distance});
        }
        // retransmit over CC modems
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 13, zCoord);
        if (tile != null && tile instanceof TileAntennaModem) {
            ((TileAntennaModem) tile).transmit(null, payload);
        }
    }

    private void refreshModems() {
        if (modemInterface != null) {
            modemInterface.structureComplete();
        }
    }

    @LuaFunction
    public boolean transmit(Object payload) {
        if (isComplete()) {
            // TODO: transmit the message to other antennas
            // TODO: transmit the message over the attached modems
        }
        return false;
    }

    public IComputerAccess getComputer() {
        return computer;
    }

    private void queueFakeEvent(Object[] args) {
        for (IComputerAccess computer : computers) {
            computer.queueEvent(ComputerCraftInfo.EVENT.MODEM, ArrayUtils.add(args, 0, "top"));
        }
    }

}
