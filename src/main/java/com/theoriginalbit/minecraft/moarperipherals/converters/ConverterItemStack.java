package com.theoriginalbit.minecraft.moarperipherals.converters;

import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.framework.peripheral.converter.ITypeConverter;
import com.theoriginalbit.minecraft.moarperipherals.utils.InventoryUtils;
import net.minecraft.item.ItemStack;

import java.util.Map;

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
public class ConverterItemStack implements ITypeConverter {

    @Override
    public Object fromLua(Object obj, Class<?> expected) {
        if (expected == ItemStack.class && obj instanceof Map) {
            int quantity = 1;
            int dmg = 0;

            @SuppressWarnings({ "unchecked" })
            Map<Object, Object> m = (Map<Object, Object>) obj;

            if (!m.containsKey("id")) { return null; }
            int id = (int)(double)(Double)m.get("id");
            if (m.containsKey("qty")) {
                quantity = (int)(double)(Double)m.get("qty");
            }
            if (m.containsKey("dmg")) {
                dmg = (int)(double)(Double)m.get("dmg");
            }
            return new ItemStack(id, quantity, dmg);
        }
        return null;
    }

    @Override
    public Object toLua(Object obj) {
        if (obj instanceof ItemStack) {
            ItemStack stack = (ItemStack) obj;
            Map<String, Object> result = Maps.newHashMap();

            result.put("id", stack.itemID);
            result.put("name", InventoryUtils.getItemName(stack));
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
