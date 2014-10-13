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
import com.theoriginalbit.moarperipherals.common.network.message.MessageSoundEffect;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class MessageHandlerSoundEffect implements IMessageHandler<MessageSoundEffect, IMessage> {
    @Override
    public IMessage onMessage(MessageSoundEffect message, MessageContext ctx) {
        final int dimId = message.intData[0];
        final double xPos = message.doubleData[0];
        final double yPos = message.doubleData[1];
        final double zPos = message.doubleData[2];
        final float volume = message.floatData[0];
        final float pitch = message.floatData[1];
        final String name = message.stringData[0];
        final World world = MoarPeripherals.proxy.getClientWorld(dimId);
        world.playSoundEffect(xPos, yPos, zPos, name, volume, pitch);
        return null;
    }
}
