/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.reference;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public final class ComputerCraftInfo {

    public static final Double REDNET_BROADCAST = 65535.0d;
    public static Block cc_blockComputer;
    public static Block cc_blockPeripheral;
    public static Block cc_blockCable;
    public static ItemStack cc_cable;
    public static ItemStack cc_wiredModem;
    public static ItemStack cc_wirelessModem;

    public static class EVENT {

        public static final String MODEM = "modem_message";
        public static final String PASTE = "paste";
        public static final String KEY = "key";
        public static final String CHAR = "char";

    }

    public static void init() {
        cc_blockComputer = GameRegistry.findBlock(Mods.COMPUTERCRAFT, "CC-Computer");
        cc_blockPeripheral = GameRegistry.findBlock(Mods.COMPUTERCRAFT, "CC-Peripheral");
        cc_blockCable = GameRegistry.findBlock(Mods.COMPUTERCRAFT, "CC-Cable");
        cc_cable = new ItemStack(cc_blockCable, 1, 0);
        cc_wiredModem = new ItemStack(cc_blockCable, 1, 1);
        cc_wirelessModem = new ItemStack(cc_blockPeripheral, 1, 1);
    }

}
