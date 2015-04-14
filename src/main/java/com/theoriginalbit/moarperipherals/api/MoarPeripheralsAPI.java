/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api;

import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetNode;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetRegistry;

/**
 * The main API access for MoarPeripherals. Use this to get access to various systems of MoarPeripherals at runtime.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
public class MoarPeripheralsAPI {
    private static IBitNetRegistry bitNetRegistry = null;

    /**
     * Gets the {@link IBitNetRegistry} instance so that {@link IBitNetNode}s can be added/removed from the network
     * in order to be able to send and receive {@link com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage}s
     *
     * @return the BitNetRegistry instance
     */
    public static IBitNetRegistry getBitNetRegistry() {
        return bitNetRegistry;
    }

    static {
        try {
            Class<?> clazz = Class.forName("com.theoriginalbit.moarperipherals.common.registry.BitNetRegistry");
            bitNetRegistry = (IBitNetRegistry) clazz.getField("INSTANCE").get(clazz);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
