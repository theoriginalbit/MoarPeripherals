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
package com.theoriginalbit.moarperipherals.common.upgrade.peripheral;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxOreScanner;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 15/11/14
 */
@LuaPeripheral("ore_scanner")
public class PeripheralOreScanner {
    private static final ArrayList<Class<? extends Block>> BLACKLIST = Lists.newArrayList();
    private static final int SCAN_RADIUS = 5;
    private static final int MAX_DEPTH = 40;
    private final ITurtleAccess turtle;

    public PeripheralOreScanner(ITurtleAccess access) {
        turtle = access;
    }

    @LuaFunction
    public double getOreDensity() throws LuaException {
        // get the turtle world and location
        final World world = turtle.getWorld();
        final ChunkCoordinates coords = turtle.getPosition();
        final int x = coords.posX, y = coords.posY, z = coords.posZ;

        // make sure the Turtle is on the ground-ish
        final Block blockBelow = world.getBlock(x, y - 1, z);
        if (blockBelow.getMaterial() == Material.air) {
            throw new LuaException("Turtle not on the ground");
        }

        // send the effects packet
        PacketHandler.INSTANCE.sendToAllAround(
                new MessageFxOreScanner(world.provider.dimensionId, x, y, z),
                new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 64d)
        );

        // figure out the scan area
        int diameter = (int) Math.floor(SCAN_RADIUS / 2);
        int maxX = x + diameter, minX = x - diameter;
        int maxY = y - 1, minY = Math.max(maxY - MAX_DEPTH, 0);
        int maxZ = z + diameter, minZ = z - diameter;

        // scan the area
        int count = 0;
        for (int xPos = minX; xPos <= maxX; ++xPos) {
            for (int zPos = minZ; zPos <= maxZ; ++zPos) {
                for (int yPos = minY; yPos <= maxY; ++yPos) {
                    final Block block = world.getBlock(xPos, yPos, zPos);
                    final float hardness = block.getBlockHardness(world, xPos, yPos, zPos);
                    // if the block is breakable, and not what we want to ignore, count it
                    if (hardness > -1f && !BLACKLIST.contains(block.getClass())) {
                        ++count;
                    }
                }
            }
        }
        return count / (double) ((maxY - minY) * SCAN_RADIUS * SCAN_RADIUS);
    }

    static {
        BLACKLIST.add(BlockAir.class);
        BLACKLIST.add(BlockGrass.class);
        BLACKLIST.add(BlockStone.class);
        BLACKLIST.add(BlockDirt.class);
        BLACKLIST.add(BlockGravel.class);
        BLACKLIST.add(BlockSand.class);
        BLACKLIST.add(BlockClay.class);
    }
}
