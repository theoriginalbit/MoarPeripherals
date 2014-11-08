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
package com.theoriginalbit.moarperipherals.api.peripheral;

import com.google.common.base.Preconditions;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.wrapper.PeripheralWrapper;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.WeakHashMap;

/**
 * This is the Peripheral Provider that will wrap valid TileEntities
 * and return them to ComputerCraft for usage. See IPeripheralProvider
 * for more information
 *
 * IMPORTANT:
 * This is a backend class that is very important for operation of this
 * framework, modifying it may have unexpected results.
 *
 * @author theoriginalbit
 */
public final class PeripheralProvider implements IPeripheralProvider {

    private final WeakHashMap<TileEntity, PeripheralWrapper> PERIPHERAL_CACHE = new WeakHashMap<TileEntity, PeripheralWrapper>();

    /**
     * Provide ComputerCraft with an IPeripheral wrapper implementation
     * of a Peripheral-Framework peripheral.
     */
	@Override
	public final IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getTileEntity(x, y, z);

        if (tile.isInvalid()) {
            return null;
        }

        // check if there is a cached peripheral for this TileEntity
        if (PERIPHERAL_CACHE.containsKey(tile)) {
            // return the cached value
            return PERIPHERAL_CACHE.get(tile);
        }

        // the potential peripheral wrapper
        PeripheralWrapper wrapper = null;

        // does the TileEntity specify that it provides an external peripheral
        if (tile instanceof ILuaPeripheralProvider) {
            // it was an ILuaPeripheralProvider, why is there a LuaPeripheral annotation present?
            Preconditions.checkArgument(!isLuaPeripheral(tile), "Peripherals cannot implement ILuaPeripheralProvider and have the LuaPeripheral annotation present");
            // get the peripheral from the ILuaPeripheralProvider
            final Object peripheral = ((ILuaPeripheralProvider) tile).getPeripheral();
            // make sure the provided peripheral is annotated
            Preconditions.checkArgument(isLuaPeripheral(peripheral), "The peripheral returned from the ILuaPeripheralProvider was not annotated with LuaPeripheral");
            // wrap the return
            wrapper = new PeripheralWrapper(peripheral);
        // if the TileEntity is annotated as a LuaPeripheral
        } else if (isLuaPeripheral(tile)) {
            wrapper = new PeripheralWrapper(tile);
        }

        // if there is a wrapper then this is a valid wrapper, cache and return it
        if (wrapper != null) {
            PERIPHERAL_CACHE.put(tile, wrapper);
            return wrapper;
        }

        return null;
	}

    private static boolean isLuaPeripheral(Object instance) {
        return instance.getClass().isAnnotationPresent(LuaPeripheral.class);
    }
}