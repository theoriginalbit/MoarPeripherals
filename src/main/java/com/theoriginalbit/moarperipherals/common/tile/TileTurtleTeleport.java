/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageParticle;
import com.theoriginalbit.moarperipherals.common.network.message.MessageSoundEffect;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.ComputerUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.util.ChunkCoordinates;

import java.util.Random;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("turtle_teleport")
public class TileTurtleTeleport extends TileMoarP {
    private final Random rand = new Random();

    @LuaFunction(isMultiReturn = true)
    public Object[] getTurtleLocation() {
        // look for the Turtle above
        ITurtleAccess turtle = ComputerUtils.getITurtle(worldObj, xCoord, yCoord + 1, zCoord);
        // if there was a turtle
        if (turtle != null) {
            // get the turtle's location
            final ChunkCoordinates coords = turtle.getPosition();
            // return the turtle's location
            return new Object[]{true, new Integer[]{coords.posX, coords.posY, coords.posZ}};
        }
        // no turtle
        return new Object[]{false, "No Turtle found above the teleport"};
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] teleportTo(int x, int y, int z) {
        // look for the Turtle above
        final ITurtleAccess turtle = ComputerUtils.getITurtle(worldObj, xCoord, yCoord + 1, zCoord);
        // if there was a turtle
        if (turtle != null) {
            // make sure it can teleport there
            if (!worldObj.isAirBlock(x, y, z)) {
                return new Object[]{false, "Teleport failed"};
            }
            // check there is enough fuel to teleport
            int requiredFuel = requiredFuelInternal(turtle, x, y, z);
            if (requiredFuel > turtle.getFuelLevel()) {
                return new Object[]{false, "Not enough fuel"};
            }
            // consume the fuel and teleport the Turtle
            turtle.consumeFuel(requiredFuel);
            turtle.teleportTo(worldObj, x, y, z);
            // show teleport particles and play teleport sounds at both sides
            doTeleportFX(new ChunkCoordinates(x, y, z));
            return new Object[]{true};
        }
        // no turtle
        return new Object[]{false, "No Turtle found above the teleport"};
    }

    @LuaFunction
    public int requiredFuel(int x, int y, int z) {
        // look for the Turtle above
        final ITurtleAccess turtle = ComputerUtils.getITurtle(worldObj, xCoord, yCoord + 1, zCoord);
        // return the required fuel
        return requiredFuelInternal(turtle, x, y, z);
    }

    private int requiredFuelInternal(ITurtleAccess turtle, int x, int y, int z) {
        if (turtle == null) return 0;
        // find out how far they wish to teleport
        final ChunkCoordinates coords = turtle.getPosition();
        final double distance = Math.sqrt(coords.getDistanceSquared(x, y, z));
        // calculate the fuel required to get to this location
        return (int) Math.ceil(distance * ConfigHandler.fuelMultiplier);
    }

    private void doTeleportFX(ChunkCoordinates target) {
        // the locations
        final int dimId = worldObj.provider.dimensionId;
        final NetworkRegistry.TargetPoint sourcePoint = new NetworkRegistry.TargetPoint(dimId, xCoord, yCoord, zCoord, 64d);
        final NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(dimId, target.posX, target.posY, target.posZ, 64d);

        final short short1 = 8;

        for (int i = 0; i < short1; ++i) {
            double rX = rand.nextDouble();
            double rY = rand.nextDouble() - 0.5d;
            double rZ = rand.nextDouble();
            float vX = (rand.nextFloat() - 0.5f) * 0.1f;
            float vY = (rand.nextFloat() - 0.5f) * 0.1f;
            float vZ = (rand.nextFloat() - 0.5f) * 0.1f;

            // construct the particle packets
            final MessageParticle sourceParticle = new MessageParticle(worldObj, "portal", xCoord + rX, yCoord + 1 + rY, zCoord + rZ, vX, vY, vZ);
            final MessageParticle targetParticle = new MessageParticle(worldObj, "portal", target.posX + rX, target.posY + rY, target.posZ + rZ, vX, vY, vZ);

            // send the packets
            PacketHandler.INSTANCE.sendToAllAround(sourceParticle, sourcePoint);
            PacketHandler.INSTANCE.sendToAllAround(targetParticle, targetPoint);
        }

        // construct the sound packets
        final MessageSoundEffect sourceSound = new MessageSoundEffect(worldObj, xCoord, yCoord, zCoord, "mob.endermen.portal", 0.4f);
        final MessageSoundEffect targetSound = new MessageSoundEffect(worldObj, target.posX, target.posY, target.posZ, "mob.endermen.portal", 0.4f);

        // send the packets
        PacketHandler.INSTANCE.sendToAllAround(sourceSound, sourcePoint);
        PacketHandler.INSTANCE.sendToAllAround(targetSound, targetPoint);
    }
}
