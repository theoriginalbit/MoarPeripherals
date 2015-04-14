/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.hook;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Used by devices that wish to be able to control a computer without a peripheral. This interface allows for custom
 * drops so the block can be re-placed with the same pairing info, as well as allowing it to be configured from the
 * {@link net.minecraft.item.ItemBlock}.
 */
public interface IPairedDeviceHook extends IBlockDropHook {
    /**
     * When invoked the device should configure its pairing information based off the supplied NBT tag
     *
     * @param tag the pairing information
     * @return successfully configured
     */
    boolean configureTargetFromNbt(NBTTagCompound tag);
}
