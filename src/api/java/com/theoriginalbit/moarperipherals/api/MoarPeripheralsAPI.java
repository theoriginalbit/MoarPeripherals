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

    /**
     * Registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower}, interface
     * with the BitNet network so that it may receive BitNet messages.
     *
     * @param tower the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower} to register
     *              with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetMessage
     */
    public static void registerBitNetTower(IBitNetTower tower) {
        findBitNet();
        try {
            bitNetRegisterTower.invoke(null, tower);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to register BitNet tower");
        }
    }

    /**
     * De-registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower}, interface
     * with the BitNet network so that it no longer receives BitNet messages.
     *
     * @param tower the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower} to register
     *              with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetTower
     */
    public static void deregisterBitNetTower(IBitNetTower tower) {
        findBitNet();
        try {
            bitNetDeregisterTower.invoke(null, tower);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to register BitNet tower");
        }
    }

    /**
     * Sends a BitNet message across the network
     *
     * @param tower   the sending tower
     * @param payload the object to send
     */
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
                bitNetRegistry = Class.forName("com.theoriginalbit.moarperipherals.common.registry.BitNetRegistry");
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
