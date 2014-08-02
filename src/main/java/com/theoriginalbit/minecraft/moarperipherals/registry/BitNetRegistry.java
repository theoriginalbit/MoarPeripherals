package com.theoriginalbit.minecraft.moarperipherals.registry;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.api.IBitNetTower;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;

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
public final class BitNetRegistry {

    private static final ArrayList<IBitNetTower> towers = Lists.newArrayList();
    private static int nextId = 2048;

    public static int registerTower(IBitNetTower tower) {
        if (!towers.contains(tower)) {
            towers.add(tower);
            return nextId++;
        }
        return -1;
    }

    public static void deregisterTower(IBitNetTower tower) {
        if (towers.contains(tower)) {
            towers.remove(tower);
        }
    }

    public static void transmit(IBitNetTower sender, Object payload) {
        final ChunkCoordinates sendLocation = sender.getCoordinates();
        final World sendWorld = sender.getWorld();
        final int range = (sendWorld.isRaining() && sendWorld.isThundering()) ? Settings.antennaRangeStorm : Settings.antennaRange;


        for (IBitNetTower tower : towers) {
            if (tower.getWorld() == sendWorld) {
                final double distance = sendLocation.getDistanceSquaredToChunkCoordinates(tower.getCoordinates());
                if (distance <= range) {
                    tower.receive(payload, distance);
                }
            }
        }
    }

}
