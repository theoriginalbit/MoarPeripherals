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
