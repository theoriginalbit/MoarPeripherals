/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api;

import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetNode;
import com.theoriginalbit.moarperipherals.api.bitnet.IBitNetUniverse;

/**
 * The main API access for MoarPeripherals. Use this to get access to various systems of MoarPeripherals at runtime.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public class MoarPeripheralsAPI {
    private static IBitNetUniverse bitNetUniverse = null;

    static {
        try {
            Class<?> clazz = Class.forName("package com.theoriginalbit.moarperipherals.common.bitnet.BitNetUniverse");
            bitNetUniverse = (IBitNetUniverse) clazz.getField("UNIVERSE").get(clazz);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the {@link IBitNetUniverse} instance so that {@link IBitNetNode}s can be added/removed from the network
     * in order to be able to send and receive {@link com.theoriginalbit.moarperipherals.api.bitnet.BitNetMessage}s
     *
     * @return the BitNetUniverse instance
     */
    public static IBitNetUniverse getBitNetRegistry() {
        return bitNetUniverse;
    }
}
