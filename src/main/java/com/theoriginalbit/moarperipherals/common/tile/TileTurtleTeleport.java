/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.MultiReturn;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxTeleport;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.ComputerUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("turtle_teleport")
public class TileTurtleTeleport extends TileMoarP {

    @LuaFunction
    @MultiReturn
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

    @LuaFunction
    @MultiReturn
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
        if (!worldObj.isRemote) {
            final int dimId = worldObj.provider.dimensionId;
            final MessageFxTeleport sourceMessage = new MessageFxTeleport(dimId, xCoord, yCoord, zCoord);
            final MessageFxTeleport targetMessage = new MessageFxTeleport(dimId, target.posX, target.posY, target.posZ);
            PacketHandler.INSTANCE.sendToAllAround(sourceMessage, new NetworkRegistry.TargetPoint(dimId, xCoord, yCoord + 1, zCoord, 64d));
            PacketHandler.INSTANCE.sendToAllAround(targetMessage, new NetworkRegistry.TargetPoint(dimId, target.posX, target.posY, target.posZ, 64d));
        }
    }
}
