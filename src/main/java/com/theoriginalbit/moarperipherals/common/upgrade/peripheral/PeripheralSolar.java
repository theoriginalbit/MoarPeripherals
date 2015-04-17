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
package com.theoriginalbit.moarperipherals.common.upgrade.peripheral;

import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 9/11/14
 */
@LuaPeripheral("solar")
public class PeripheralSolar {
    private static final int TICK_ADD_FUEL = 100; // 5 seconds
    private ITurtleAccess turtle;
    private boolean active;
    private int tick = 0;

    public PeripheralSolar(ITurtleAccess access) {
        turtle = access;
    }

    @LuaFunction
    public void turnOn() {
        active = true;
    }

    @LuaFunction
    public void turnOff() {
        active = false;
    }

    @LuaFunction
    public int getNaturalLightLevel() {
        final World world = turtle.getWorld();
        final ChunkCoordinates coordinates = turtle.getPosition();
        int light = world.getSavedLightValue(EnumSkyBlock.Sky, coordinates.posX, coordinates.posY, coordinates.posZ) - world.skylightSubtracted;
        float angle = world.getCelestialAngleRadians(1f);

        if (angle < (float) Math.PI) {
            angle += (0f - angle) * 0.2f;
        } else {
            angle += (((float) Math.PI * 2f) - angle) * 0.2f;
        }

        return Math.round((float) light * MathHelper.cos(angle));
    }

    public void update() {
        if (!active) {
            return;
        }
        final World world = turtle.getWorld();
        // if the world has a sky, this code is being run on a server, the turtle needs fuel, and 20 ticks have passed
        if (!world.provider.hasNoSky && !world.isRemote && turtleNeedsFuel() && ++tick > TICK_ADD_FUEL) {
            final ChunkCoordinates coordinates = turtle.getPosition();
            if (!world.canBlockSeeTheSky(coordinates.posX, coordinates.posY, coordinates.posZ)) {
                return;
            }
            final int light = getNaturalLightLevel();
            if (light > 7) {
                turtle.addFuel(1);
            }
            tick = 0;
        }
    }

    private boolean turtleNeedsFuel() {
        return turtle.isFuelNeeded() && turtle.getFuelLevel() < turtle.getFuelLimit();
    }
}
