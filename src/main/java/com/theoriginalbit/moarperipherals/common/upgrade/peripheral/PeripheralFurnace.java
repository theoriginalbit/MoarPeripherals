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
package com.theoriginalbit.moarperipherals.common.upgrade.peripheral;

import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChunkCoordinates;

/**
 * @author theoriginalbit
 * @since 27/10/14
 */
@LuaPeripheral("furnace")
public class PeripheralFurnace {
    private final ITurtleAccess turtle;
    private final TileEntityFurnace furnace;

    public PeripheralFurnace(ITurtleAccess access, TurtleSide side) {
        turtle = access;
        furnace = new TileEntityFurnace();
        furnace.readFromNBT(access.getUpgradeNBTData(side));
    }

    @LuaFunction
    public Object[] smelt(int slot, int amount) {
        return new Object[]{false, "nothing to smelt"};
    }

    public void update(ITurtleAccess turtle, TurtleSide side) {
        furnace.setWorldObj(turtle.getWorld());
        final ChunkCoordinates coordinates = turtle.getPosition();
        furnace.xCoord = coordinates.posX;
        furnace.yCoord = coordinates.posY;
        furnace.zCoord = coordinates.posZ;

        furnace.writeToNBT(turtle.getUpgradeNBTData(side));
        // I assume this is needed?
        turtle.updateUpgradeNBTData(side);
    }

    public boolean isBurning() {
        return furnace.isBurning();
    }
}
