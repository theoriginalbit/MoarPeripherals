package com.theoriginalbit.minecraft.moarperipherals.reference;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

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
