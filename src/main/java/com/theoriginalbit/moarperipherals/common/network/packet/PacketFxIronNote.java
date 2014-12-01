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

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.MoarPeripherals;
import cpw.mods.fml.common.network.Player;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 */
public class PacketFxIronNote extends PacketGeneric {
    private final ArrayList<PendingNote> notes = Lists.newArrayList();

    public PacketFxIronNote() {
        super(PacketType.IRON_NOTE.ordinal());
    }

    public void addNote(String instrument, int pitch) {
        notes.add(new PendingNote(instrument, pitch));
    }

    public PacketFxIronNote pack(int dimensionId, int xPos, int yPos, int zPos) {
        final int size = notes.size();
        intData = new int[]{dimensionId, size};
        doubleData = new double[]{xPos, yPos, zPos};

        floatData = new float[size];
        stringData = new String[size];
        for (int i = 0; i < size; ++i) {
            final PendingNote n = notes.get(i);
            floatData[i] = n.pitch;
            stringData[i] = n.instrument;
        }

        return this;
    }

    @Override
    public void handlePacket(byte[] bytes, Player player) throws Exception {
        super.handlePacket(bytes, player);
        final int dimId = intData[0];
        final int noteCount = intData[1];
        final double xPos = doubleData[0];
        final double yPos = doubleData[1];
        final double zPos = doubleData[2];

        final World world = MoarPeripherals.proxy.getClientWorld(dimId);

        if (world == null) {
            return;
        }

        for (int i = 0; i < noteCount; ++i) {
            final String name = stringData[i];
            final float pitch = floatData[i];
            playNote(world, xPos, yPos, zPos, name, pitch);
        }
    }

    private void playNote(World world, double xPos, double yPos, double zPos, String instrument, float pitch) {
        MoarPeripherals.proxy.playSound(
                xPos + 0.5d,
                yPos + 0.5d,
                zPos + 0.5d,
                instrument,
                3.0f,
                (float) Math.pow(2d, (double) (pitch - 12) / 12d),
                false
        );

        world.spawnParticle("note", xPos + 0.5d, yPos + 1.2d, zPos + 0.5d, pitch / 24d, 0f, 0f);
    }

    class PendingNote {
        public final String instrument;
        public final int pitch;

        public PendingNote(String instrument, int pitch) {
            this.instrument = instrument;
            this.pitch = pitch;
        }
    }
}
