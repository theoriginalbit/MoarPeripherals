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
package com.theoriginalbit.moarperipherals.common.network.packet;

import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.common.network.Player;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author theoriginalbit
 */
public class PacketFxOreScanner extends PacketGeneric {
    private static final String SOUND_EFFECT = ModInfo.RESOURCE_DOMAIN + ":densityScan";
    private static final int NUM_PARTICLES = 5;
    private final Random rand = new Random();

    public PacketFxOreScanner() {
        super(PacketType.DENSITY_SCAN.ordinal());
    }

    public PacketFxOreScanner(int dimensionId, double xPos, double yPos, double zPos) {
        this();
        intData = new int[]{dimensionId};
        doubleData = new double[]{xPos, yPos, zPos};
    }

    @Override
    public void handlePacket(byte[] bytes, Player player) throws Exception {
        super.handlePacket(bytes, player);
        final int dimId = intData[0];
        final double xPos = doubleData[0];
        final double yPos = doubleData[1];
        final double zPos = doubleData[2];
        final World world = MoarPeripherals.proxy.getClientWorld(dimId);

        if (world == null) {
            return;
        }

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                for (int i = 0; i < NUM_PARTICLES; ++i) {
                    double rX = x + rand.nextDouble();
                    double rZ = z + rand.nextDouble();
                    int y = (int) yPos - 1;

                    // get the texture for the particles
                    final int blockId = world.getBlockId((int) (xPos + x), y, (int) (zPos + z));
                    final String particle = "blockcrack_" + blockId + "_" + world.getBlockMetadata(x, y, z);

                    // spawn the particle
                    world.spawnParticle(particle, xPos + rX, yPos, zPos + rZ, 0.0d, 0.5d, 0.0d);
                }
            }
        }

        MoarPeripherals.proxy.playSound(xPos, yPos, zPos, SOUND_EFFECT, 1f, 1f, false);
    }
}
