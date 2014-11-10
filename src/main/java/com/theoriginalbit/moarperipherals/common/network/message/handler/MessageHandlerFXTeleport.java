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
        if (world == null) {
            return null;
        }

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
