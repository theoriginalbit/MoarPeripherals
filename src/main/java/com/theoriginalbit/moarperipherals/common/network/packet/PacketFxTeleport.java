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
import cpw.mods.fml.common.network.Player;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author theoriginalbit
 */
public class PacketFxTeleport extends PacketGeneric {
    private static final Random rand = new Random();
    private static final int PARTICLE_COUNT = 64;

    public PacketFxTeleport() {
        super(PacketType.TELEPORT_FX.ordinal());
    }

    public PacketFxTeleport(int dimensionId, int xPos, int yPos, int zPos) {
        this();
        intData = new int[]{dimensionId};
        doubleData = new double[]{xPos, yPos, zPos};
    }

    @Override
    public void handlePacket(byte[] bytes, Player player) throws Exception {
        final int dimId = intData[0];
        final double xPos = doubleData[0];
        final double yPos = doubleData[1];
        final double zPos = doubleData[2];
        final World world = MoarPeripherals.proxy.getClientWorld(dimId);

        if (world == null) {
            return;
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

        MoarPeripherals.proxy.playSound(
                xPos + 0.5d,
                yPos + 0.5d,
                zPos + 0.5d,
                "mob.endermen.portal",
                0.4f, // volume
                1f, // pitch
                false // delayed
        );
    }
}
