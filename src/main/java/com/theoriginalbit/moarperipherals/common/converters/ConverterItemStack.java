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
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ConverterItemStack implements ITypeConverter {

    @Override
    public Object fromLua(Object obj, Class<?> expected) throws LuaException {
        if (expected == ItemStack.class && obj instanceof Map) {
            Map<?, ?> m = (Map<?, ?>) obj;

            if (!m.containsKey("id")) {
                throw new LuaException("expected id for item");
            }
            String[] parts = ((String) m.get("id")).split(":");
            if (parts.length != 2) {
                throw new LuaException("invalid item id should be mod:name");
            }

            Item item = GameRegistry.findItem(parts[0], parts[1]);
            int quantity = getIntValue(m, "qty", 1);
            int dmg = getIntValue(m, "dmg", 0);

            return new ItemStack(item, quantity, dmg);
        }
        return null;
    }

    private static int getIntValue(Map<?, ?> map, String key, int _default) {
        final Object value = map.get(key);

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        return _default;
    }

    @Override
    public Object toLua(Object obj) throws LuaException {
        if (obj instanceof ItemStack) {
            return fillBasicProperties((ItemStack) obj);
        }
        return null;
    }

    private static Map<String, Object> fillBasicProperties(ItemStack stack) throws LuaException {
        final Map<String, Object> map = Maps.newHashMap();

        final GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());

        if (id == null) {
            throw new LuaException(String.format("Invalid item stack: %s", stack));
        }

        map.put("id", id.toString());
        map.put("name", id.name);
        map.put("mod_id", id.modId);
        map.put("display_name", getNameForItemStack(stack));
        map.put("raw_name", getRawNameForStack(stack));
        map.put("qty", stack.stackSize);
        map.put("dmg", stack.getItemDamage());
        map.put("max_dmg", stack.getMaxDamage());
        map.put("max_size", stack.getMaxStackSize());

        return map;
    }

    private static String getNameForItemStack(ItemStack is) {
        try {
            return is.getDisplayName();
        } catch (Exception ignored) {
        }

        try {
            return is.getUnlocalizedName();
        } catch (Exception ignored) {
        }

        return "unknown";
    }

    private static String getRawNameForStack(ItemStack is) {
        try {
            return is.getUnlocalizedName().toLowerCase();
        } catch (Exception ignored) {
        }

        return "unknown";
    }
}
