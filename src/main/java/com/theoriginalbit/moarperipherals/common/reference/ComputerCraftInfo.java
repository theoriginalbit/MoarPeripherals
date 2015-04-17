/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.reference;

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
    public static ItemStack cc_turtle;
    public static ItemStack cc_turtle_adv;

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
        cc_turtle = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleExpanded", 1);
        cc_turtle_adv = GameRegistry.findItemStack("ComputerCraft", "CC-TurtleAdvanced", 1);

        cc_cable = new ItemStack(cc_blockCable, 1, 0);
        cc_wiredModem = new ItemStack(cc_blockCable, 1, 1);
        cc_wirelessModem = new ItemStack(cc_blockPeripheral, 1, 1);
    }

}
