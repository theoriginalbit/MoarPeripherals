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
public class MessageSoundEffect extends MessageGeneric {

    public MessageSoundEffect() {
        // required empty constructor
    }

    public MessageSoundEffect(World world, double x, double y, double z, String name, float volume, float pitch) {
        // make the packet for the clients
        stringData = new String[]{name};
        intData = new int[]{world.provider.dimensionId};
        doubleData = new double[]{x, y, z};
        floatData = new float[]{volume, pitch};
        // play the sound in case the source was a client
        world.playSoundEffect(x, y, z, name, volume, pitch);
    }

}
