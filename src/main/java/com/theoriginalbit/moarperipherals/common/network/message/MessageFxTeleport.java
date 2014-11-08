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
 * @since 8/11/14
 */
@SuppressWarnings("unused")
public class MessageFxTeleport extends MessageGeneric {

    public MessageFxTeleport() {
        // required empty constructor
    }

    public MessageFxTeleport(World world, int dimensionId, int xPos, int yPos, int zPos) {
        intData = new int[]{dimensionId};
        doubleData = new double[]{xPos, yPos, zPos};
        world.playSoundEffect(xPos, yPos, zPos, "mob.endermen.portal", 0.4f, 1f);
    }
}
