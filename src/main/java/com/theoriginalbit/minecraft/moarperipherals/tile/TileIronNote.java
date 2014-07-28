package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketIronNote;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.PacketUtils;
import net.minecraft.world.World;
import openperipheral.api.Ignore;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
@Ignore
@LuaPeripheral("iron_note")
public class TileIronNote extends TileMPBase {

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
        Preconditions.checkArgument(ticker++ <= MAX_TICK, "Too many notes (over " + MAX_TICK + " per tick)");
        Preconditions.checkArgument(Settings.noteRange > 0, "The Iron Note blocks range has been disabled, please contact your server owner");

        play(worldObj, xCoord, yCoord, zCoord, instrument, pitch);

        int dimId = worldObj.provider.dimensionId;
        PacketIronNote packet = new PacketIronNote();
        packet.intData = new int[]{dimId, xCoord, yCoord, zCoord, instrument, pitch};
        PacketUtils.sendToPlayersAround(packet, xCoord, yCoord, zCoord, Settings.noteRange, dimId);
    }

    @Override
    public void updateEntity() {
        ticker = 0;
    }

    public static void play(World world, double x, double y, double z, int instrument, int pitch) {
        float f = (float) Math.pow(2.0D, (double) (pitch - 12) / 12.0D);
        world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "note." + INSTRUMENTS.get(instrument), 3.0F, f);
        world.spawnParticle("note", x + 0.5D, y + 1.2D, z + 0.5D, (double) pitch / 24.0D, 0.0D, 0.0D);
    }

}