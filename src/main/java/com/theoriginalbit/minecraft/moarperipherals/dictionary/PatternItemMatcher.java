package com.theoriginalbit.minecraft.moarperipherals.dictionary;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;

import java.util.regex.Pattern;

public class PatternItemMatcher {

    private final Pattern pattern;

    public PatternItemMatcher(Pattern patt) {
        pattern = patt;
    }

    public boolean matches(ItemStack stack) {
        return pattern.matcher(sanatise(getName(stack).toLowerCase())).find();
    }

    private String sanatise(String s) {
        return ChatAllowedCharacters.filerAllowedCharacters(s.replaceAll("§.", ""));
    }

    private static String getName(ItemStack stack) {
        String name;
        try {
            name = stack.getDisplayName();
        } catch (Exception e) {
            try {
                name = stack.getUnlocalizedName();
            } catch (Exception e2) {
                name = "Unknown";
            }
        }
        return name;
    }
}
