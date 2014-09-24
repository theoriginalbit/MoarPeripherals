/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower;
import com.theoriginalbit.moarperipherals.reference.Settings;
import com.theoriginalbit.moarperipherals.utils.LogUtils;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class BitNetRegistry implements ITickHandler {

    private static final ArrayList<IBitNetTower> towers = Lists.newArrayList();
    private static final ConcurrentLinkedQueue<DelayedMessage> messageQueue = new ConcurrentLinkedQueue<DelayedMessage>();
    private static int nextId = 0;

    public static int registerTower(IBitNetTower tower) {
        LogUtils.debug("BitNet registerTower invoked, already contains tower: " + towers.contains(tower));
        if (!towers.contains(tower)) {
            towers.add(tower);
            return nextId++;
        }
        return -1;
    }

    public static void deregisterTower(IBitNetTower tower) {
        LogUtils.debug("BitNet deregisterTower invoked, tower registered: " + towers.contains(tower));
        if (towers.contains(tower)) {
            towers.remove(tower);
        }
    }

    public static void transmit(IBitNetTower sender, BitNetMessage payload) {
        final Vec3 sendLocation = sender.getWorldPosition();
        final World sendWorld = sender.getWorld();
        final int range = (sendWorld.isRaining() && sendWorld.isThundering()) ? Settings.antennaRangeStorm : Settings.antennaRange;

        for (IBitNetTower tower : towers) {
            if (tower.getWorld() == sendWorld) {
                final Vec3 towerLocation = tower.getWorldPosition();
                final double distance = Math.sqrt(sendLocation.squareDistanceTo(towerLocation.xCoord, towerLocation.yCoord, towerLocation.zCoord));
                if (distance > 0 && distance <= range) {
                    /*
                     * create a new instance of the message from the current one so that the distance updates correctly
                     * especially when its a repeated message; we build it from the current one so it still identifies
                     * as the same message for detection against message propagation
                     */
                    messageQueue.add(new DelayedMessage(tower, (new BitNetMessage(payload)).addDistance(distance), distance));
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

    private static final class DelayedMessage {

        private final IBitNetTower receiver;
        private final BitNetMessage payload;
        private int sendDelay;

        public DelayedMessage(IBitNetTower tower, BitNetMessage message, double distance) {
            receiver = tower;
            payload = message;
            // calculate the cost to send this message
            sendDelay = (int) (Math.ceil(distance / 100) * Settings.antennaMessageDelay);
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
