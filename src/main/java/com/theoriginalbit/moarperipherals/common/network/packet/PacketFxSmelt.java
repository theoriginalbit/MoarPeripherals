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

/**
 * @author theoriginalbit
 */
public class PacketFxSmelt extends PacketGeneric {
    public PacketFxSmelt() {
        super(PacketType.SMELT_FX.ordinal());
    }

    public PacketFxSmelt(int dimensionId, double xPos, double yPos, double zPos) {
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

        world.spawnParticle("smoke", xPos, yPos, zPos, 0d, 0d, 0d);
        world.spawnParticle("flame", xPos, yPos, zPos, 0d, 0d, 0d);
    }
}
