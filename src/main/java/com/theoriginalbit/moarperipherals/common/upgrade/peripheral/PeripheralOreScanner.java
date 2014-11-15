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
import com.theoriginalbit.moarperipherals.common.network.message.MessageSoundEffect;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
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
    private static final String SOUND_EFFECT = ModInfo.RESOURCE_DOMAIN + ":densityScan";
    private static final int SCAN_RADIUS = 5;
    private static final int MAX_DEPTH = 40;
    private final ITurtleAccess turtle;

    public PeripheralOreScanner(ITurtleAccess access) {
        turtle = access;
    }

    @LuaFunction
    public double getOreDensity() throws LuaException {
        final World world = turtle.getWorld();
        final ChunkCoordinates coords = turtle.getPosition();
        if (world.isAirBlock(coords.posX, coords.posY - 1, coords.posZ)) {
            throw new LuaException("Turtle not on the ground");
        }
        PacketHandler.INSTANCE.sendToAllAround(
                new MessageSoundEffect(coords.posX, coords.posY, coords.posZ, SOUND_EFFECT),
                new NetworkRegistry.TargetPoint(world.provider.dimensionId, coords.posX, coords.posY, coords.posZ, 64d)
        );
        int diameter = (int) Math.floor(SCAN_RADIUS / 2);
        int minX = coords.posX - diameter;
        int maxX = coords.posX + diameter;
        int maxY = coords.posY - 1;
        int minY = Math.max(maxY - MAX_DEPTH, 0);
        int minZ = coords.posZ - diameter;
        int maxZ = coords.posZ + diameter;
        int count = 0;
        for (int x = minX; x <= maxX; ++x) {
            for (int z = minZ; z <= maxZ; ++z) {
                for (int y = minY; y <= maxY; ++y) {
                    final Block block = world.getBlock(x, y, z);
                    if (block != Blocks.bedrock && !BLACKLIST.contains(block.getClass())) {
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
