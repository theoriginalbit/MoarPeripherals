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
package com.moarperipherals.network;

import com.moarperipherals.MoarPeripherals;
import com.moarperipherals.ModInfo;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author theoriginalbit
 * @since 15/11/14
 */
public class MessageHandlerFxOreScanner implements IMessageHandler<MessageFxOreScanner, IMessage> {
    private static final String SOUND_EFFECT = ModInfo.RESOURCE_DOMAIN + ":densityScan";
    private static final int NUM_PARTICLES = 5;
    private final Random rand = new Random();

    @Override
    public IMessage onMessage(MessageFxOreScanner message, MessageContext ctx) {
        final int dimId = message.intData[0];
        final double xPos = message.doubleData[0];
        final double yPos = message.doubleData[1];
        final double zPos = message.doubleData[2];
        final World world = MoarPeripherals.proxy.getClientWorld(dimId);

        if (world == null) {
            return null;
        }

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                for (int i = 0; i < NUM_PARTICLES; ++i) {
                    double rX = x + rand.nextDouble();
                    double rZ = z + rand.nextDouble();
                    int y = (int) yPos - 1;

                    // get the texture for the particles
                    final Block block = world.getBlock((int) (xPos + x), y, (int) (zPos + z));
                    final String particle = "blockcrack_" + Block.getIdFromBlock(block) + "_" + world.getBlockMetadata(x, y, z);

                    // spawn the particle
                    world.spawnParticle(particle, xPos + rX, yPos, zPos + rZ, 0.0d, 0.5d, 0.0d);
                }
            }
        }

        MoarPeripherals.proxy.playSound(xPos, yPos, zPos, SOUND_EFFECT, 1f, 1f, false);
        return null;
    }
}
