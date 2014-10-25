/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
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
    private int tick = 0;
    private final double xOffset, yOffset, zOffset;
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
