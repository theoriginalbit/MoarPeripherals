/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.minecraft.moarperipherals.dictionary.ItemSearch;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

@LuaPeripheral("item_dictionary")
public class TileDictionary extends TileMoarP {

    @LuaFunction
    public ArrayList<ItemStack> search(String term) throws Exception {
        return ItemSearch.search(term);
    }

}
