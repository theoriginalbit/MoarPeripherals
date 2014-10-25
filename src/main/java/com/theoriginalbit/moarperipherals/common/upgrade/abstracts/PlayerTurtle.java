/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade.abstracts;

import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class PlayerTurtle extends FakePlayer {

    public PlayerTurtle(ITurtleAccess turtle) {
        super((WorldServer) turtle.getWorld(), new GameProfile(null, "MoarPeripheralsTurtle"));
        final ChunkCoordinates coordinates = turtle.getPosition();
        setPosition(coordinates.posX + 0.5d, coordinates.posY + 0.5d, coordinates.posZ + 0.5d);
    }

}
