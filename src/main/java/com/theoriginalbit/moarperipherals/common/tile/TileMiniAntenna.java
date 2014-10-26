/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.collect.Lists;
import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.registry.BitNetRegistry;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("bitnet_antenna")
public class TileMiniAntenna extends TileMoarP implements IBitNetCompliant {
    private static final String EVENT_BITNET = "bitnet_message";
    private final ArrayList<UUID> receivedMessages = Lists.newArrayList();
    private boolean registered = false;

    @Override
    public void updateEntity() {
        if (!registered) {
            registerTower();
        }
    }

    @Computers.List
    public ArrayList<IComputerAccess> computers;

    @LuaFunction(isMultiReturn = true)
    public void transmit(Object payload) {
        BitNetRegistry.transmit(this, new BitNetMessage(payload));
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public Vec3 getWorldPosition() {
        return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
    }

    @Override
    public void receive(BitNetMessage payload) {
        if (!receivedMessages.contains(payload.getMessageId())) {
            if (computers != null && computers.size() > 0) {
                LogUtils.debug(String.format("BitNet Mini Antenna at %d %d %d has computer(s) connected, queueing BitNet message.", xCoord, yCoord, zCoord));
                for (IComputerAccess comp : computers) {
                    comp.queueEvent(EVENT_BITNET, new Object[]{comp.getAttachmentName(), payload.getPayload(), payload.getDistanceTravelled()});
                }
            }
            receivedMessages.add(payload.getMessageId());
        } else {
            LogUtils.debug(String.format("BitNet Mini Antenna at %d %d %d received a previously received message.", xCoord, yCoord, zCoord));
        }
    }

    @Override
    public int getReceiveRange() {
        return ConfigHandler.miniAntennaRange;
    }

    @Override
    public int getReceiveRangeDuringStorm() {
        return ConfigHandler.miniAntennaRangeStorm;
    }

    private void registerTower() {
        if (!worldObj.isRemote) {
            BitNetRegistry.registerCompliance(this);
        }
        registered = true;
    }
}
