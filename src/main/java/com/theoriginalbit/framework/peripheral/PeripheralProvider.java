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
package com.theoriginalbit.framework.peripheral;

import com.google.common.base.Preconditions;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.Requires;
import com.theoriginalbit.framework.peripheral.wrapper.WrapperComputer;
import cpw.mods.fml.common.Loader;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.WeakHashMap;

/**
 * This is the Peripheral Provider that will wrap valid TileEntities
 * and return them to ComputerCraft for usage. See IPeripheralProvider
 * for more information
 * <p/>
 * IMPORTANT:
 * This is a backend class that is very important for operation of this
 * framework, modifying it may have unexpected results.
 *
 * @author theoriginalbit
 */
public final class PeripheralProvider implements IPeripheralProvider {

    private final WeakHashMap<TileEntity, WrapperComputer> PERIPHERAL_CACHE = new WeakHashMap<>();

    /**
     * Provide ComputerCraft with an IPeripheral wrapper implementation
     * of a Peripheral-Framework peripheral.
     */
    @Override
    public final IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile == null || tile.isInvalid()) {
            return null;
        }

        // check if there is a cached peripheral for this TileEntity
        if (PERIPHERAL_CACHE.containsKey(tile)) {
            // return the cached value
            return PERIPHERAL_CACHE.get(tile);
        }

        // the potential peripheral wrapper
        WrapperComputer wrapper = null;

        // does the TileEntity specify that it provides an external peripheral
        if (tile instanceof ILuaPeripheralProvider) {
            // it was an ILuaPeripheralProvider, why is there a LuaPeripheral annotation present?
            Preconditions.checkArgument(!isLuaPeripheral(tile), "Peripherals cannot implement ILuaPeripheralProvider and have the LuaPeripheral annotation present");
            // get the peripheral from the ILuaPeripheralProvider
            final Object peripheral = ((ILuaPeripheralProvider) tile).getPeripheral();
            // make sure the provided peripheral is annotated
            Preconditions.checkArgument(isLuaPeripheral(peripheral), "The peripheral returned from the ILuaPeripheralProvider was not annotated with LuaPeripheral");
            // wrap the return
            if (isEnabledLuaPeripheral(peripheral)) {
                wrapper = new WrapperComputer(peripheral);
            }
        } else if (isEnabledLuaPeripheral(tile)) { // if the TileEntity is annotated as a LuaPeripheral
            wrapper = new WrapperComputer(tile);
        }

        // if there is a wrapper then this is a valid wrapper, cache and return it
        if (wrapper != null) {
            PERIPHERAL_CACHE.put(tile, wrapper);
            return wrapper;
        }

        return null;
    }

    private static boolean isLuaPeripheral(Object peripheral) {
        return peripheral.getClass().isAnnotationPresent(LuaPeripheral.class);
    }

    private static boolean isEnabledLuaPeripheral(Object peripheral) {
        final Class<?> clazz = peripheral.getClass();
        // if there is no annotation, it's not a valid peripheral
        if (!clazz.isAnnotationPresent(LuaPeripheral.class)) {
            return false;
        }
        // if there is no Requires annotation we can assume it should always be enabled
        if (!clazz.isAnnotationPresent(Requires.class)) {
            return true;
        }

        // get the mod IDs specified that this method should be enabled for
        final Requires requires = clazz.getAnnotation(Requires.class);
        final String[] modIds = requires.modIds();
        final boolean allRequired = requires.allRequired();

        // loop through the mod IDs and see if any are present
        for (final String mid : modIds) {
            final boolean loaded = Loader.isModLoaded(mid);
            if (loaded && !allRequired) { // if it is loaded, and not all are required, we can enable this peripheral
                return true;
            } else if (!loaded && allRequired) { // if it's not loaded, and all are required, we cannot enable this peripheral
                return false;
            }
        }

        // all mods were required, and all were loaded
        return true;
    }
}
