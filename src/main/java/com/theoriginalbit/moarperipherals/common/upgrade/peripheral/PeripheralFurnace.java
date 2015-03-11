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

import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
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
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author theoriginalbit
 * @since 27/10/14
 */
@LuaPeripheral("furnace")
public class PeripheralFurnace {
    private static final int FURNACE_SLOT_INPUT = 0;
    private static final int FURNACE_SLOT_OUTPUT = 2;
    private final IInventory inv;
    private final ITurtleAccess turtle;
    private final boolean upgradeOnLeft;
    private final TileEntityFurnace furnace;

    public PeripheralFurnace(ITurtleAccess access, TurtleSide periphSide) {
        turtle = access;
        upgradeOnLeft = periphSide == TurtleSide.Left;
        inv = turtle.getInventory();
        furnace = new TileEntityFurnace();
    }

    @LuaFunction
    public Object[] smelt(int slot, int amount) {
        // convert from Lua indexes to Java indexes
        --slot;

        // make sure the supplied slot is valid
        if (slot < 0 || slot >= inv.getSizeInventory()) {
            return new Object[]{false, "slot number " + (slot + 1) + " out of range"};
        }

        // make sure the supplied amount is valid
        if (amount < 1 || amount > 64) {
            return new Object[]{false, "invalid amount, should be between 1 and 64"};
        }

        // make sure there's an item in the Turtle's inventory
        final ItemStack input = inv.getStackInSlot(slot);
        if (input == null || input.stackSize == 0) {
            return new Object[]{false, "nothing to smelt"};
        }

        // find how many we CAN smelt
        amount = Math.min(amount, input.stackSize);

        // Put the items in the furnace
        final ItemStack furnaceInput = input.copy();
        furnaceInput.stackSize = amount;
        furnace.setInventorySlotContents(FURNACE_SLOT_INPUT, furnaceInput);

        // make sure the item can be smelted
        if (!canSmelt()) {
            return new Object[]{false, "item in slot number " + (slot + 1) + " cannot be smelted"};
        }

        // make sure there is enough fuel in the furnace
        final int fuelNeeded = amount * ConfigHandler.upgradeFurnaceFuelConsumption;
        if (turtle.isFuelNeeded() && turtle.getFuelLevel() < fuelNeeded) {
            return new Object[]{false, "not enough fuel to smelt", fuelNeeded - turtle.getFuelLevel()};
        }

        // get the result from the 'smelt' of the item
        for (int i = 0; i < amount; ++i) {
            furnace.smeltItem();
        }
        final ItemStack result = furnace.getStackInSlot(FURNACE_SLOT_OUTPUT);
        furnace.setInventorySlotContents(FURNACE_SLOT_OUTPUT, null);

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
        final ChunkCoordinates position = turtle.getPosition();

        // store the item, it should never drop it, we've made sure there WAS space
        InventoryUtils.storeOrDropItemStack(inv, result, world, position);

        // smelting effects
        doSmeltFX(world, position, ForgeDirection.getOrientation(turtle.getDirection()));

        // return success and how many items were smelted
        return new Object[]{true, amount};
    }

    private boolean canSmelt() {
        final ItemStack input = furnace.getStackInSlot(FURNACE_SLOT_INPUT);
        return input != null && FurnaceRecipes.smelting().getSmeltingResult(input) != null;
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
