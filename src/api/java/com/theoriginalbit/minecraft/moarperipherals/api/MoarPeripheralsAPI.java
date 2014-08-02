package com.theoriginalbit.minecraft.moarperipherals.api;

import java.lang.reflect.Method;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
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
public class MoarPeripheralsAPI {

    private static boolean searched = false;

    private static Class<?> bitNetRegistry;
    private static Method bitNetRegisterTower;
    private static Method bitNetDeregisterTower;

    public static void registerBitNetTower(IBitNetTower tower) {
        findBitNet();
        try {
            bitNetRegisterTower.invoke(null, tower);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to register BitNet tower");
        }
    }

    public static void deregisterBitNetTower(IBitNetTower tower) {
        findBitNet();
        try {
            bitNetDeregisterTower.invoke(null, tower);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to register BitNet tower");
        }
    }

    private static void findBitNet() {
        if (!searched) {
            try {
                bitNetRegistry = Class.forName("com.theoriginalbit.minecraft.moarperipherals.registry.BitNetRegistry");
                bitNetRegisterTower = findBitNetMethod("registerTower", new Class[]{IBitNetTower.class});
                bitNetDeregisterTower = findBitNetMethod("deregisterTower", new Class[]{IBitNetTower.class});
            } catch (Exception e) {
                System.err.println("MoarPeripheralsAPI: BitNetRegistry not found.");
            } finally {
                searched = true;
            }
        }
    }

    private static Method findBitNetMethod(String name, Class[] args) {
        try {
            return bitNetRegistry.getMethod(name, args);
        } catch (Exception e) {
            System.err.println("MoarPeripheralsAPI: BitNet Method " + name + " not found.");
            return null;
        }
    }

}
