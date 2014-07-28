package com.theoriginalbit.minecraft.framework.peripheral;

import com.google.common.base.Preconditions;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.framework.peripheral.wrapper.PeripheralWrapper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

import java.util.WeakHashMap;

/**
 * Peripheral Framework is an open-source framework that has the aim of
 * allowing developers to implement their ComputerCraft peripherals faster,
 * easier, and cleaner; allowing them to focus more on developing their
 * content.
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

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
		TileEntity tile = world.getBlockTileEntity(x, y, z);

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