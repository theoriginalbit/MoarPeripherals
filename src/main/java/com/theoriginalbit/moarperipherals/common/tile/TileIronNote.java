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
import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.mount.MountMoarP;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxIronNote;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import cpw.mods.fml.common.network.NetworkRegistry;

@LuaPeripheral("iron_note")
@Computers.Mount(MountMoarP.class)
public class TileIronNote extends TileMoarP {
    private static ImmutableList<String> INSTRUMENTS = ImmutableList.of("harp", "bd", "snare", "hat", "bassattack");
    private static final int MIN_INST = 0;
    private static final int MAX_INST = 4;
    private static final int MIN_PITCH = 0;
    private static final int MAX_PITCH = 24;
    private static final int MAX_NOTES = 5; // this is 5 notes per tick, allowing for 5 note chords
    private int notesCount = 0;
    private MessageFxIronNote message;

    @LuaFunction
    public void playNote(int instrument, int pitch) throws Exception {
        Preconditions.checkArgument(instrument >= MIN_INST && instrument <= MAX_INST, "Expected instrument %d-%d", MIN_INST, MAX_INST);
        Preconditions.checkArgument(pitch >= MIN_PITCH && pitch <= MAX_PITCH, "Expected pitch %d-%d", MIN_PITCH, MAX_PITCH);
        Preconditions.checkArgument(notesCount++ < MAX_NOTES, "Too many notes (over %d per tick)", MAX_NOTES);
        Preconditions.checkArgument(ConfigData.noteRange > 0, "The Iron Note blocks range has been disabled, please contact your server owner");

        if (message == null) {
            message = new MessageFxIronNote();
        }

        message.addNote("note." + INSTRUMENTS.get(instrument), pitch);
    }

    @Override
    public void updateEntity() {
        notesCount = 0;

        // if there is a message, send it
        if (message != null) {
            // packs all the notes into the message
            message.pack(worldObj.provider.dimensionId, xCoord, yCoord, zCoord);
            PacketHandler.INSTANCE.sendToAllAround(message, new NetworkRegistry.TargetPoint(
                    worldObj.provider.dimensionId,
                    xCoord,
                    yCoord,
                    zCoord,
                    ConfigData.noteRange
            ));
            message = null;
        }
    }
}