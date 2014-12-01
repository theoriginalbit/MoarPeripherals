/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.converters;

import com.google.common.collect.Maps;
import com.theoriginalbit.moarperipherals.api.peripheral.converter.ITypeConverter;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
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
            int id = (int) (double) (Double) m.get("id");
            if (m.containsKey("qty")) {
                quantity = (int) (double) (Double) m.get("qty");
            }
            if (m.containsKey("dmg")) {
                dmg = (int) (double) (Double) m.get("dmg");
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
