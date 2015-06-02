/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.network.message.handler;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxIronNote;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 11/11/14
 */
public class MessageHandlerFxIronNote implements IMessageHandler<MessageFxIronNote, IMessage> {
    @Override
    public IMessage onMessage(MessageFxIronNote message, MessageContext ctx) {
        final int dimId = message.intData[0];
        final int soundCount = message.intData[1];
        final double xPos = message.doubleData[0];
        final double yPos = message.doubleData[1];
        final double zPos = message.doubleData[2];

        final World world = MoarPeripherals.proxy.getClientWorld(dimId);

        if (world == null) {
            return null;
        }

        for (int i = 0; i < soundCount; ++i) {
            final String name = message.stringData[i];
            final float pitch = message.floatData[i];
            playSound(world, xPos, yPos, zPos, name, pitch);
        }

        return null;
    }

    private void playSound(World world, double xPos, double yPos, double zPos, String name, float pitch) {
        MoarPeripherals.proxy.playSound(
                xPos + 0.5d,
                yPos + 0.5d,
                zPos + 0.5d,
                name,
                3.0f,
                (float) Math.pow(2d, (double) (pitch - 12) / 12d),
                false
        );

        world.spawnParticle("note", xPos + 0.5d, yPos + 1.2d, zPos + 0.5d, pitch / 24d, 0f, 0f);
    }
}
