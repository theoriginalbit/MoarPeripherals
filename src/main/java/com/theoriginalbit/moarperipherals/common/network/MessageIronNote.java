/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.network;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.common.tile.TileIronNote;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

public class MessageIronNote extends MessageGeneric implements IMessageHandler<MessageIronNote, IMessage> {

    public MessageIronNote() {
        super(MessageType.IRON_NOTE.ordinal());
    }

    @Override
    public IMessage onMessage(MessageIronNote message, MessageContext ctx) {
        final World world = MoarPeripherals.proxy.getClientWorld(intData[0]);
        TileIronNote.play(world, intData[1], intData[2], intData[3], intData[4], intData[5]);
        return null;
    }

}
