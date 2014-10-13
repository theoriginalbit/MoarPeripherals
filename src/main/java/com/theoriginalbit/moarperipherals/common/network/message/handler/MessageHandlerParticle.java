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
import com.theoriginalbit.moarperipherals.common.network.message.MessageParticle;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class MessageHandlerParticle implements IMessageHandler<MessageParticle, IMessage> {

    @Override
    public IMessage onMessage(MessageParticle message, MessageContext ctx) {
        final String name = message.stringData[0];
        final int dimId = message.intData[0];
        final double xPos = message.doubleData[0];
        final double yPos = message.doubleData[1];
        final double zPos = message.doubleData[2];
        final double xVel = message.doubleData[3];
        final double yVel = message.doubleData[4];
        final double zVel = message.doubleData[5];
        final World world = MoarPeripherals.proxy.getClientWorld(dimId);
        world.spawnParticle(name, xPos, yPos, zPos, xVel, yVel, zVel);
        return null;
    }

}
