/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.network.message;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.common.tile.TileIronNote;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

public class MessageIronNote extends MessageGeneric implements IMessageHandler<MessageIronNote, IMessage> {

    @Override
    public IMessage onMessage(MessageIronNote message, MessageContext ctx) {
        final World world = MoarPeripherals.proxy.getClientWorld(message.intData[0]);
        TileIronNote.play(world, message.intData[1], message.intData[2], message.intData[3], message.intData[4], message.intData[5]);
        return null;
    }

}
