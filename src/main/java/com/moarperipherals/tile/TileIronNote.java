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
package com.moarperipherals.tile;

import com.google.common.base.Preconditions;
import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.moarperipherals.api.note.IIronNoteRegistry;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.integration.mount.MountMoarP;
import com.moarperipherals.network.PacketHandler;
import com.moarperipherals.network.MessageFxIronNote;
import com.moarperipherals.registry.IronNoteRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;

@LuaPeripheral("iron_note")
@Computers.Mount(MountMoarP.class)
public class TileIronNote extends TileMoarP {
    private static final int MIN_PITCH = 0;
    private static final int MAX_PITCH = 24;
    private static final int MAX_NOTES = 5; // this is 5 notes per tick, allowing for 5 note chords
    private int notesCount = 0;
    private MessageFxIronNote message;

    @LuaFunction
    public void playNote(int instrument, int pitch) throws Exception {
        addSound(IronNoteRegistry.NOTE, instrument, pitch);
    }

    @LuaFunction
    public void playSfx(int soundEffect, int pitch) throws Exception {
        addSound(IronNoteRegistry.SFX, soundEffect, pitch);
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

    private void addSound(IIronNoteRegistry registry, int selection, int pitch) {
        Preconditions.checkArgument(registry.contains(selection), "Expected number %s-%s", 0, registry.size() - 1);
        Preconditions.checkArgument(
                pitch >= MIN_PITCH && pitch <= MAX_PITCH, "Expected pitch %s-%s", MIN_PITCH, MAX_PITCH
        );
        Preconditions.checkArgument(notesCount++ < MAX_NOTES, "Too many sounds (over %s per tick)", MAX_NOTES);
        Preconditions.checkArgument(
                ConfigData.noteRange > 0, "Iron Note blocks range has been disabled, please contact the server owner"
        );

        if (message == null) {
            message = new MessageFxIronNote();
        }

        message.addSound(registry.getSound(selection), pitch);
    }
}