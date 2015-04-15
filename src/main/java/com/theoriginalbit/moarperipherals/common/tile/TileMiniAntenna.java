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
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.collect.Lists;
import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetNode;
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
public class TileMiniAntenna extends TileMoarP implements IBitNetNode {
    private static final String EVENT_BITNET = "bitnet_message";
    private static final float ROTATION_SPEED = 1.0f;
    private static final float BOB_MULTIPLIER = 0.02f;
    private static final float BOB_SPEED = 16.0f;
    private final ArrayList<UUID> receivedMessages = Lists.newArrayList();
    private boolean registered = false;
    private float rotation = 0.0f;
    private float bob = 0.0f;
    private int tick = 0;

    public float getRotation() {
        return rotation;
    }

    public float getBob() {
        return bob;
    }

    @Override
    public void updateEntity() {
        if (!registered) {
            registerTower();
        }
        rotation = (rotation + ROTATION_SPEED) % 360f;
        bob = BOB_MULTIPLIER * (float) Math.sin(++tick / BOB_SPEED);
    }

    @Computers.List
    public ArrayList<IComputerAccess> computers;

    @LuaFunction
    public void transmit(Object payload) {
        BitNetRegistry.INSTANCE.transmit(this, new BitNetMessage(payload));
    }

    @Override
    public World getWorld() {
        return worldObj;
    }

    @Override
    public Vec3 getPosition() {
        return Vec3.createVectorHelper(xCoord, yCoord, zCoord);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.MINI_ANTENNA;
    }

    @Override
    public void receive(BitNetMessage payload) {
        if (!receivedMessages.contains(payload.getId())) {
            if (computers != null && computers.size() > 0) {
                LogUtils.debug(String.format("BitNet Mini Antenna at %d %d %d has computer(s) connected, queueing BitNet message.", xCoord, yCoord, zCoord));
                for (IComputerAccess comp : computers) {
                    comp.queueEvent(EVENT_BITNET, new Object[]{comp.getAttachmentName(), payload.getPayload(), payload.getDistanceTravelled()});
                }
            }
            receivedMessages.add(payload.getId());
        } else {
            LogUtils.debug(String.format("BitNet Mini Antenna at %d %d %d received a previously received message.",
                    xCoord, yCoord, zCoord));
        }
    }

    @Override
    public void blockBroken(int x, int y, int z) {
        if (!worldObj.isRemote) {
            BitNetRegistry.INSTANCE.removeNode(this);
        }
        registered = false;
    }

    private void registerTower() {
        if (!worldObj.isRemote) {
            BitNetRegistry.INSTANCE.addNode(this);
        }
        registered = true;
    }
}
