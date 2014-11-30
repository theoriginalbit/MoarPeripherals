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
package com.theoriginalbit.moarperipherals.common.item;

import com.theoriginalbit.moarperipherals.api.ITooltipInformer;
import com.theoriginalbit.moarperipherals.common.item.abstracts.ItemMoarP;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.utils.NBTUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.List;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public class ItemInkCartridge extends ItemMoarP implements ITooltipInformer {
    private IIcon iconInkC;
    private IIcon iconInkM;
    private IIcon iconInkY;
    private IIcon iconInkK;
    private IIcon iconInkE;

    public ItemInkCartridge() {
        super("inkCartridge");

        setHasSubtypes(true);
        setMaxStackSize(1);
        setCreativeTab(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        iconInkC = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeC");
        iconInkM = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeM");
        iconInkY = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeY");
        iconInkK = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeK");
        iconInkE = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeE");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        switch (meta) {
            case 0:
                return iconInkC;
            case 1:
                return iconInkM;
            case 2:
                return iconInkY;
            case 3:
                return iconInkK;
        }
        return iconInkE;
    }

    private static final String CONTENTS = "%s: %s";
    private static final String PERCENT = "%s: %s";

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformativeTooltip(ItemStack stack, EntityPlayer player, List<String> list, boolean bool) {
        final int inkColor = getInkColor(stack);
        final String inkName = getInkName(inkColor);

        // if the ink cartridge has ink
        if (inkColor > -1) {
            // add the contents string
            list.add(String.format(
                    CONTENTS,
                    Constants.TOOLTIPS.INK_CONTENTS.getLocalised(),
                    inkName
            ));
            // add the ink level string when needed
            final String percent = getInkPercent(stack);
            if (percent != null) {
                list.add(String.format(
                        PERCENT,
                        Constants.TOOLTIPS.INK_LEVEL.getLocalised(),
                        percent
                ));
            }
        } else {
            list.add(inkName);
        }
    }

    private static String getInkName(int inkColor) {
        final Constants.LocalisationStore name;
        switch (inkColor) {
            case 0:
                name = Constants.TOOLTIPS.INK_CYAN;
                break;
            case 1:
                name = Constants.TOOLTIPS.INK_MAGENTA;
                break;
            case 2:
                name = Constants.TOOLTIPS.INK_YELLOW;
                break;
            case 3:
                name = Constants.TOOLTIPS.INK_BLACK;
                break;
            default:
                name = Constants.TOOLTIPS.INK_EMPTY;
        }
        return name.getLocalised();
    }

    public static int getInkColor(ItemStack stack) {
        final NBTTagCompound tag = NBTUtils.getItemTag(stack);
        if (tag.hasKey("inkColor")) {
            return tag.getInteger("inkColor");
        }
        // wasn't a colour, therefore we assume it's empty
        return -1;
    }

    public static String getInkPercent(ItemStack stack) {
        final NBTTagCompound tag = NBTUtils.getItemTag(stack);
        if (tag.hasKey("inkLevel")) {
            float level = tag.getFloat("inkLevel");
            return (int) (level / FluidContainerRegistry.BUCKET_VOLUME * 100) + "%";
        }
        return null;
    }
}
