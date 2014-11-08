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
package com.theoriginalbit.moarperipherals.common.utils;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.Method;

public final class PeripheralUtils {

    private static final Class<?> CLAZZ_TILEMODEMBASE = ReflectionUtils.getClass("dan200.computercraft.shared.peripheral.modem.TileModemBase");
    private static final Method METHOD_GETPERIPHERAL = ReflectionUtils.getMethod(CLAZZ_TILEMODEMBASE, "getPeripheral", new Class[]{int.class});

    public static IPeripheral getIPeripheral(TileEntity tile) {
        if (tile != null && CLAZZ_TILEMODEMBASE.isAssignableFrom(tile.getClass())) {
            for (int i = 0; i < 6; ++i) {
                Object obj = ReflectionUtils.callMethod(tile, METHOD_GETPERIPHERAL, i);
                if (obj != null && obj instanceof IPeripheral) {
                    return (IPeripheral) obj;
                }
            }
        }
        return null;
    }

}
