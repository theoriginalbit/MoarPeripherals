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
package com.theoriginalbit.moarperipherals.common.tile.firework;

import com.google.common.collect.ImmutableList;
import com.theoriginalbit.moarperipherals.common.handler.TickHandler;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.concurrent.Callable;

/**
 * @author theoriginalbit
 * @since 22/10/14
 */
public class LauncherTube {
    private static final ImmutableList<Double> OFFSETS = ImmutableList.of(0.15d, 0.5d, 0.85d);
    private static final int TICKS_COOL_DOWN = 20;

    private final QueueBuffer rocketBuffer;
    private final double xOffset, yOffset, zOffset;
    private int tick = 0;
    private boolean coolDownActive;

    public LauncherTube(QueueBuffer buffer, int position) {
        rocketBuffer = buffer;
        xOffset = OFFSETS.get(position % 3);
        yOffset = 0.8d;
        zOffset = OFFSETS.get((int) Math.ceil(position / 3));
    }

    public void update() {
        if (coolDownActive && ++tick >= TICKS_COOL_DOWN) {
            tick = 0;
            coolDownActive = false;
        }
    }

    public boolean canLaunch() {
        return !coolDownActive;
    }

    public Object[] launch(final World world, double x, double y, double z) {
        final ItemStack firework = rocketBuffer.getNextItemStack();
        if (firework != null) {
            final EntityFireworkRocket rocket = new EntityFireworkRocket(world, x + xOffset, y + yOffset, z + zOffset, firework);

            TickHandler.addTickCallback(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    world.spawnEntityInWorld(rocket);
                    coolDownActive = true;
                    return null;
                }
            });
            return new Object[]{true};
        }
        return new Object[]{false, "no firework to launch"};
    }

}
