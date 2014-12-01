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
package com.theoriginalbit.moarperipherals.common.registry;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class BitNetRegistry implements ITickHandler {

    private static final ArrayList<IBitNetCompliant> towers = Lists.newArrayList();
    private static final ConcurrentLinkedQueue<DelayedMessage> messageQueue = new ConcurrentLinkedQueue<DelayedMessage>();
    private static int nextId = 0;

    /**
     * Registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}, interface
     * with the BitNet network so that it may receive BitNet messages.
     *
     * @param tile the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}
     *             {@link net.minecraft.tileentity.TileEntity} to register with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant
     * @see com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage
     */
    public static int registerCompliance(IBitNetCompliant tile) {
        LogUtils.debug("BitNet registerTower invoked, already contains tower: " + towers.contains(tile));
        if (!towers.contains(tile)) {
            towers.add(tile);
            return nextId++;
        }
        return -1;
    }

    /**
     * De-registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}, interface
     * with the BitNet network so that it no longer receives BitNet messages.
     *
     * @param tile the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}
     *             {@link net.minecraft.tileentity.TileEntity} to de-register with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant
     */
    public static void deregisterCompliance(IBitNetCompliant tile) {
        LogUtils.debug("BitNet deregisterTower invoked, tower registered: " + towers.contains(tile));
        if (towers.contains(tile)) {
            towers.remove(tile);
        }
    }

    /**
     * Sends a {@link com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage} across the network
     *
     * @param sender  the sending tower
     * @param payload the object to send
     */
    public static void transmit(IBitNetCompliant sender, BitNetMessage payload) {
        final Vec3 sendLocation = sender.getWorldPosition();
        final World sendWorld = sender.getWorld();
        final boolean isStorming = (sendWorld.isRaining() && sendWorld.isThundering());

        for (IBitNetCompliant receiver : towers) {
            if (receiver.getWorld() == sendWorld) {
                final Vec3 towerLocation = receiver.getWorldPosition();

                // distance from A to B
                final double distance = Math.sqrt(sendLocation.squareDistanceTo(towerLocation.xCoord, towerLocation.yCoord, towerLocation.zCoord));

                // range specified by the tower
                final int range = isStorming ? receiver.getReceiveRangeDuringStorm() : receiver.getReceiveRange();

                if (distance > 0 && distance <= range) {
                    /*
                     * create a new instance of the message from the current one so that the distance updates correctly
                     * especially when its a repeated message; we build it from the current one so it still identifies
                     * as the same message for detection against message propagation
                     */
                    messageQueue.add(new DelayedMessage(receiver, (new BitNetMessage(payload)).addDistance(distance), distance));
                }
            }
        }
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        Iterator<DelayedMessage> iter = messageQueue.iterator();
        while (iter.hasNext()) {
            DelayedMessage message = iter.next();
            if (message.tick()) {
                iter.remove();
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return "MoarPeripheral|BitNet";
    }

    /**
     * A container class for a {@link com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage}, tracking how
     * many ticks remain before the message payload should be delivered to the receiving tower
     *
     * @author theoriginalbit
     */
    private static final class DelayedMessage {

        private final IBitNetCompliant receiver;
        private final BitNetMessage payload;
        private int sendDelay;

        public DelayedMessage(IBitNetCompliant tower, BitNetMessage message, double distance) {
            receiver = tower;
            payload = message;
            // calculate the cost to send this message
            sendDelay = (int) (Math.ceil(distance / 100) * ConfigHandler.antennaMessageDelay);
            LogUtils.debug(String.format("Created %02d tick delayed message, payload=%s", sendDelay, payload.toString()));
        }

        public boolean tick() {
            if (--sendDelay <= 0) {
                LogUtils.debug(String.format("Ticks expired for %s sending message", payload.getMessageId()));
                receiver.receive(payload);
                return true;
            }
            return false;
        }

    }

}
