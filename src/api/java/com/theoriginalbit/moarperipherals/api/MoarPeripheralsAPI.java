/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api;

import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant;

import java.lang.reflect.Method;

public class MoarPeripheralsAPI {

    private static boolean searched = false;

    private static Class<?> bitNetRegistry;
    private static Method bitNetRegisterCompliance;
    private static Method bitNetDeregisterCompliance;
    private static Method bitNetTransmit;

    /**
     * Registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}, interface
     * with the BitNet network so that it may receive BitNet messages.
     *
     * @param tile the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}
     *              {@link net.minecraft.tileentity.TileEntity} to register with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetMessage
     */
    public static void registerBitNetCompliance(IBitNetCompliant tile) {
        findBitNet();
        try {
            bitNetRegisterCompliance.invoke(null, tile);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to register BitNet compliant TileEntity");
        }
    }

    /**
     * De-registers a {@link net.minecraft.tileentity.TileEntity}, which implements the
     * {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}, interface
     * with the BitNet network so that it no longer receives BitNet messages.
     *
     * @param tile the {@link com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant}
     *              {@link net.minecraft.tileentity.TileEntity} to de-register with the BitNet network
     * @see com.theoriginalbit.moarperipherals.api.bitnet.IBitNetCompliant
     */
    public static void deregisterBitNetCompliance(IBitNetCompliant tile) {
        findBitNet();
        try {
            bitNetDeregisterCompliance.invoke(null, tile);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to register BitNet compliant TileEntity");
        }
    }

    /**
     * Sends a BitNet message across the network
     *
     * @param tile   the sending tower
     * @param payload the object to send
     */
    public static void sendBitNetMessage(IBitNetCompliant tile, Object payload) {
        findBitNet();
        try {
            bitNetTransmit.invoke(null, tile, payload);
        } catch (Exception e) {
            System.out.println("MoarPeripheralsAPI: Failed to transmit message via BitNet");
        }
    }

    private static void findBitNet() {
        if (!searched) {
            try {
                bitNetRegistry = Class.forName("com.theoriginalbit.moarperipherals.common.registry.BitNetRegistry");
                bitNetRegisterCompliance = findBitNetMethod("registerCompliance", new Class[]{IBitNetCompliant.class});
                bitNetDeregisterCompliance = findBitNetMethod("deregisterCompliance", new Class[]{IBitNetCompliant.class});
                bitNetTransmit = findBitNetMethod("transmit", new Class[]{IBitNetCompliant.class, Object.class});
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
