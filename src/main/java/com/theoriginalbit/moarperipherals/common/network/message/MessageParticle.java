/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.network.message;

import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class MessageParticle extends MessageGeneric {

    public MessageParticle() {
        // required empty constructor
    }

    public MessageParticle(World world, String name, double x, double y, double z) {
        this(world, name, x, y, z, 1f);
    }

    public MessageParticle(World world, String name, double x, double y, double z, double velX) {
        this(world, name, x, y, z, velX, 0f, 0f);
    }

    public MessageParticle(World world, String name, double x, double y, double z, double velX, double velY, double velZ) {
        // make the packet for the clients
        stringData = new String[]{name};
        intData = new int[]{world.provider.dimensionId};
        doubleData = new double[]{x, y, z, velX, velY, velZ};
        // spawn the particle in case the source was a client
        world.spawnParticle(name, x, y, z, velX, velY, velZ);
    }

}
