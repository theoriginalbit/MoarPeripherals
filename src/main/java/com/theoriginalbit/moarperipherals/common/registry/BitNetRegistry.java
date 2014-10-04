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
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class BitNetRegistry {

    private static final ArrayList<IBitNetCompliant> towers = Lists.newArrayList();
    private static final ConcurrentLinkedQueue<DelayedMessage> messageQueue = new ConcurrentLinkedQueue<DelayedMessage>();
    private static int nextId = 0;

    @SubscribeEvent
    public void onServerTick(ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            final Iterator<DelayedMessage> it = messageQueue.iterator();
            while (it.hasNext()) {
                DelayedMessage message = it.next();
                if (message.tick()) {
                    it.remove();
                }
            }
        }
    }

    /**
     * Registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}, interface
     * with the BitNet network so that it may receive BitNet messages.
     *
     * @param tile the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}
     *             {@link net.minecraft.tileentity.TileEntity} to register with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetMessage
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
        final int range = (sendWorld.isRaining() && sendWorld.isThundering()) ? ConfigHandler.antennaRangeStorm : ConfigHandler.antennaRange;

        for (IBitNetCompliant receiver : towers) {
            if (receiver.getWorld() == sendWorld) {
                final Vec3 towerLocation = receiver.getWorldPosition();
                final double distance = Math.sqrt(sendLocation.squareDistanceTo(towerLocation.xCoord, towerLocation.yCoord, towerLocation.zCoord));
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
