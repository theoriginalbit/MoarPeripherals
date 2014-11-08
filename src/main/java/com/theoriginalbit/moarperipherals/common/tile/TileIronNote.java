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
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageParticle;
import com.theoriginalbit.moarperipherals.common.network.message.MessageSoundEffect;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import cpw.mods.fml.common.network.NetworkRegistry;

@LuaPeripheral("iron_note")
public class TileIronNote extends TileMoarP {

    private static ImmutableList<String> INSTRUMENTS = ImmutableList.of("harp", "bd", "snare", "hat", "bassattack");
    private static final int MIN_INST = 0;
    private static final int MAX_INST = 4;
    private static final int MIN_PITCH = 0;
    private static final int MAX_PITCH = 24;
    private static final int MAX_TICK = 5; // this is 5 notes per tick, allowing for 5 note chords
    private int ticker = 0;

    @LuaFunction
    public void playNote(int instrument, int pitch) throws Exception {
        Preconditions.checkArgument(instrument >= MIN_INST && instrument <= MAX_INST, "Expected instrument 0-4");
        Preconditions.checkArgument(pitch >= MIN_PITCH && pitch <= MAX_PITCH, "Expected pitch 0-24");
        Preconditions.checkArgument(ticker++ < MAX_TICK, "Too many notes (over " + MAX_TICK + " per tick)");
        Preconditions.checkArgument(ConfigHandler.noteRange > 0, "The Iron Note blocks range has been disabled, please contact your server owner");

        int dimId = worldObj.provider.dimensionId;
        final NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(dimId, xCoord, yCoord, zCoord, ConfigHandler.noteRange);

        // construct the sound packet
        final MessageSoundEffect soundEffect = new MessageSoundEffect(
                worldObj,
                xCoord + 0.5d,
                yCoord + 0.5d,
                zCoord + 0.5d,
                "note." + INSTRUMENTS.get(instrument),
                3.0f,
                (float) Math.pow(2d, (double) (pitch - 12) / 12d)
        );

        // construct the particle packet
        final MessageParticle particle = new MessageParticle(
                worldObj,
                "note",
                xCoord + 0.5d,
                yCoord + 1.2d,
                zCoord + 0.5d,
                pitch / 24d
        );

        // send the packets
        PacketHandler.INSTANCE.sendToAllAround(soundEffect, target);
        PacketHandler.INSTANCE.sendToAllAround(particle, target);
    }

    @Override
    public void updateEntity() {
        ticker = 0;
    }

}