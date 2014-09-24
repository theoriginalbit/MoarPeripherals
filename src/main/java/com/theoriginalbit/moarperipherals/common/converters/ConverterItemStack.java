/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.converters;

import com.google.common.collect.Maps;
import com.theoriginalbit.framework.peripheral.converter.ITypeConverter;
import com.theoriginalbit.moarperipherals.utils.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ConverterItemStack implements ITypeConverter {

    @Override
    public Object fromLua(Object obj, Class<?> expected) {
        if (expected == ItemStack.class && obj instanceof Map) {
            int quantity = 1;
            int dmg = 0;

            @SuppressWarnings({"unchecked"})
            Map<Object, Object> m = (Map<Object, Object>) obj;

            if (!m.containsKey("id")) {
                return null;
            }
            final Block block = Block.getBlockFromName((String) m.get("name"));
            if (m.containsKey("qty")) {
                quantity = (int) (double) (Double) m.get("qty");
            }
            if (m.containsKey("dmg")) {
                dmg = (int) (double) (Double) m.get("dmg");
            }
            return new ItemStack(block, quantity, dmg);
        }
        return null;
    }

    @Override
    public Object toLua(Object obj) {
        if (obj instanceof ItemStack) {
            ItemStack stack = (ItemStack) obj;
            Map<String, Object> result = Maps.newHashMap();

            result.put("name", stack.getUnlocalizedName());
            result.put("rawName", stack.getUnlocalizedName());
            result.put("qty", stack.stackSize);
            result.put("dmg", stack.getItemDamage());
            result.put("maxdmg", stack.getMaxDamage());
            result.put("maxSize", stack.getMaxStackSize());

            return result;
        }
        return null;
    }
}
