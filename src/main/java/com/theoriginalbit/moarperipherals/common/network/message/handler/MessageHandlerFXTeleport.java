/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.network.message.handler;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxTeleport;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author theoriginalbit
 * @since 8/11/14
 */
public class MessageHandlerFXTeleport implements IMessageHandler<MessageFxTeleport, IMessage> {
    private static final Random rand = new Random();
    private static final int PARTICLE_COUNT = 64;

    @Override
    public IMessage onMessage(MessageFxTeleport message, MessageContext ctx) {
        final int dimId = message.intData[0];
        final double xPos = message.doubleData[0];
        final double yPos = message.doubleData[1];
        final double zPos = message.doubleData[2];
        final World world = MoarPeripherals.proxy.getClientWorld(dimId);

        for (int i = 0; i < PARTICLE_COUNT; ++i) {
            double rX = rand.nextDouble();
            double rY = rand.nextDouble() - 0.5d;
            double rZ = rand.nextDouble();
            float vX = (rand.nextFloat() - 0.5f) * 0.1f;
            float vY = (rand.nextFloat() - 0.5f) * 0.1f;
            float vZ = (rand.nextFloat() - 0.5f) * 0.1f;

            world.spawnParticle("portal", xPos + rX, yPos + rY, zPos + rZ, vX, vY, vZ);
        }

        world.playSoundEffect(xPos, yPos, zPos, "mob.endermen.portal", 0.4f, 1f);
        return null;
    }

}
