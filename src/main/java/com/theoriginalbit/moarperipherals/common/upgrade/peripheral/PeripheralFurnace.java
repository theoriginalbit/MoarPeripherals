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

import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxSmelt;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author theoriginalbit
 * @since 27/10/14
 */
@LuaPeripheral("furnace")
public class PeripheralFurnace {
    private final ITurtleAccess turtle;
    private final boolean upgradeOnLeft;
    private final IInventory inv;

    public PeripheralFurnace(ITurtleAccess access, TurtleSide periphSide) {
        turtle = access;
        upgradeOnLeft = periphSide == TurtleSide.Left;
        inv = turtle.getInventory();
    }

    @LuaFunction
    public Object[] smelt(int slot, int amount) {
        if (slot < 1 || slot > inv.getSizeInventory()) {
            return new Object[]{false, "slot number " + slot + " out of range"};
        }
        // convert from Lua indexes that start at 1
        --slot;
        final ItemStack input = inv.getStackInSlot(slot);
        // make sure there was an item
        if (input == null || input.stackSize == 0) {
            return new Object[]{false, "nothing to smelt"};
        }
        // find how many we CAN smelt
        amount = Math.min(amount, input.stackSize);
        // make sure that it can smelt to something
        final ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
        if (result == null) {
            return new Object[]{false, "item in slot number " + slot + " cannot be smelted"};
        }
        // make sure there is enough fuel in the furnace
        int fuelNeeded = amount * ConfigHandler.upgradeFurnaceFuelConsumption;
        if (turtle.isFuelNeeded() && turtle.getFuelLevel() < fuelNeeded) {
            return new Object[]{false, "not enough fuel to smelt", fuelNeeded - turtle.getFuelLevel()};
        }
        // update how many there will be after the smelt
        result.stackSize = Math.max(1, result.stackSize); // makes sure it's never 0
        result.stackSize *= amount;
        // make sure the result can be stored in the inventory
        if (!InventoryUtils.canStoreItem(inv, result)) {
            return new Object[]{false, "not enough space in inventory"};
        }
        // perform the 'smelt' by removing the item(s) and consuming the fuel
        input.stackSize -= amount;
        if (input.stackSize == 0) {
            inv.setInventorySlotContents(slot, null);
        }
        turtle.consumeFuel(fuelNeeded);

        final World world = turtle.getWorld();
        final ChunkCoordinates coords = turtle.getPosition();
        // store the item, it should never drop it, we've made sure there WAS space
        InventoryUtils.storeOrDropItemStack(inv, result, world, coords);
        // smelting effects
        doSmeltFX(world, coords, ForgeDirection.getOrientation(turtle.getDirection()));
        return new Object[]{true, amount};
    }

    private void doSmeltFX(World worldObj, ChunkCoordinates coords, ForgeDirection dir) {
        if (!worldObj.isRemote) {
            final int dimId = worldObj.provider.dimensionId;
            // offset the x and z so it's in the middle of the Turtle
            double x = coords.posX + 0.5d, y = coords.posY + 0.4d, z = coords.posZ + 0.5d;
            // move the x or z so that it aligns with the furnace
            switch (dir) {
                case NORTH:
                    x += upgradeOnLeft ? -0.5d : 0.5d;
                    break;
                case SOUTH:
                    x += upgradeOnLeft ? 0.5d : -0.5d;
                    break;
                case EAST:
                    z += upgradeOnLeft ? -0.5d : 0.5d;
                    break;
                case WEST:
                    z += upgradeOnLeft ? 0.5d : -0.5d;
                    break;
            }
            // send the FX packet
            PacketHandler.INSTANCE.sendToAllAround(
                    new MessageFxSmelt(dimId, x, y, z),
                    new NetworkRegistry.TargetPoint(dimId, x, y, z, 64d)
            );
        }
    }
}
