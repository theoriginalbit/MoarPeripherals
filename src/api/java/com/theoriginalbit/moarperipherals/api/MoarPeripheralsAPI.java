/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api;

import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower;

import java.lang.reflect.Method;

public class MoarPeripheralsAPI {

    private static boolean searched = false;

    private static Class<?> bitNetRegistry;
    private static Method bitNetRegisterTower;
    private static Method bitNetDeregisterTower;
    private static Method bitNetTransmit;

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

    public static void sendBitNetMessage(IBitNetTower tower, Object payload) {
        findBitNet();
        try {
            bitNetTransmit.invoke(null, tower, payload);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to transmit message via BitNet");
        }
    }

    private static void findBitNet() {
        if (!searched) {
            try {
                bitNetRegistry = Class.forName("com.theoriginalbit.minecraft.moarperipherals.registry.BitNetRegistry");
                bitNetRegisterTower = findBitNetMethod("registerTower", new Class[]{IBitNetTower.class});
                bitNetDeregisterTower = findBitNetMethod("deregisterTower", new Class[]{IBitNetTower.class});
                bitNetTransmit = findBitNetMethod("transmit", new Class[]{IBitNetTower.class, Object.class});
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
