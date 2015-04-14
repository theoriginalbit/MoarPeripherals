/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.bitnet;

import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public interface IBitNetCompliant {
    World getWorld();

    Vec3 getWorldPosition();

    void receive(BitNetMessage payload);

    int getReceiveRange();

    int getReceiveRangeDuringStorm();
}
