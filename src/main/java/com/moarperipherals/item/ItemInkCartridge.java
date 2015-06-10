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
package com.moarperipherals.item;

import com.moarperipherals.Constants;
import com.moarperipherals.block.ITooltipHook;
import com.moarperipherals.tile.printer.CartridgeContents;
import com.moarperipherals.util.NBTUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.List;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public class ItemInkCartridge extends ItemMoarP implements ITooltipHook {
    private static final String CONTENTS = "%s: %s";
    private static final String PERCENT = "%s: %.1f%%";

    public ItemInkCartridge() {
        super("inkCartridge");

        setHasSubtypes(true);
        setMaxStackSize(1);
        setMaxDamage(1000);
    }

    public static float getInkPercent(ItemStack stack) {
        return stack.getItemDamage() / FluidContainerRegistry.BUCKET_VOLUME * 100;
    }

    public static CartridgeContents getCartridgeContents(ItemStack stack) {
        int inkColor = NBTUtil.getInteger(stack, "inkColor");
        return CartridgeContents.valueOf(inkColor);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        for (CartridgeContents color : CartridgeContents.values()) {
            color.registerIcons(registry);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 5; ++i) {
            list.add(NBTUtil.setFloat(new ItemStack(item, 1), "inkColor", i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return CartridgeContents.EMPTY.getIcon();
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack) {
        return getCartridgeContents(stack).getIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addToTooltip(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {
        final CartridgeContents contents = getCartridgeContents(stack);
        final String inkName = contents.getLocalisedName();

        if (contents != CartridgeContents.EMPTY) {
            // add the contents string
            list.add(String.format(
                    CONTENTS,
                    Constants.TOOLTIPS.INK_CONTENTS.getLocalised(),
                    inkName
            ));
            // add the ink level string when needed
            final float percent = getInkPercent(stack);
            list.add(String.format(
                    PERCENT,
                    Constants.TOOLTIPS.INK_LEVEL.getLocalised(),
                    percent
            ));
        } else {
            list.add(inkName);
        }
    }
}
