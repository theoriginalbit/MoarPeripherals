package com.theoriginalbit.minecraft.moarperipherals.registry;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.api.IBitNetTower;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;

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
public final class BitNetRegistry implements ITickHandler {

    private static final ArrayList<IBitNetTower> towers = Lists.newArrayList();
    private static final ArrayList<DelayedMessage> messageQueue = Lists.newArrayList();
    private static int nextId = 0;

    public static int registerTower(IBitNetTower tower) {
        if (!towers.contains(tower)) {
            towers.add(tower);
            return nextId++;
        }
        return -1;
    }

    public static void deregisterTower(IBitNetTower tower) {
        if (towers.contains(tower)) {
            towers.remove(tower);
        }
    }

    public static void transmit(IBitNetTower sender, Object payload) {
        final Vec3 sendLocation = sender.getWorldPosition();
        final World sendWorld = sender.getWorld();
        final int range = (sendWorld.isRaining() && sendWorld.isThundering()) ? Settings.antennaRangeStorm : Settings.antennaRange;


        for (IBitNetTower tower : towers) {
            if (tower.getWorld() == sendWorld) {
                final Vec3 towerLocation = tower.getWorldPosition();
                final double distance = Math.sqrt(sendLocation.squareDistanceTo(towerLocation.xCoord, towerLocation.yCoord, towerLocation.zCoord));
                if (distance > 0 && distance <= range) {
                    messageQueue.add(new DelayedMessage(tower, payload, distance));
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
        private final double distance;
        private final Object payload;
        private int sendDelay;

        public DelayedMessage(IBitNetTower tower, Object message, double dist) {
            receiver = tower;
            payload = message;
            distance = dist;
            // calculate the cost to send this message
            sendDelay = (int)(Math.ceil(distance / 100) * Settings.antennaMessageDelay);
        }

        public boolean tick() {
            if (--sendDelay <= 0) {
                receiver.receive(payload, distance);
                return true;
            }
            return false;
        }

    }

}
