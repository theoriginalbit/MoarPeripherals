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
