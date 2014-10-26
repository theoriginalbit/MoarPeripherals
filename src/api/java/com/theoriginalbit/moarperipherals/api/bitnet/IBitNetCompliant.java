/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api.bitnet;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IBitNetCompliant {

    public World getWorld();

    public Vec3 getWorldPosition();

    public void receive(BitNetMessage payload);

    public int getReceiveRange();

    public int getReceiveRangeDuringStorm();

}
