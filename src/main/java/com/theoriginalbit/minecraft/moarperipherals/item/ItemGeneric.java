package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.ITooltipInformer;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.lookup.Tooltips;
import com.theoriginalbit.minecraft.moarperipherals.utils.KeyboardUtils;
import com.theoriginalbit.minecraft.moarperipherals.utils.NEIUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemGeneric extends Item {

    private final String name;

    public ItemGeneric(int itemId, String itemName) {
        super(itemId - 256);
        name = itemName;

        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + "." + name);
        setCreativeTab(MoarPeripherals.creativeTab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        itemIcon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        Item item = stack.getItem();
        if (item instanceof ITooltipInformer) {
            if (KeyboardUtils.isShiftKeyDown()) {
                ((ITooltipInformer) item).addInformativeTooltip(stack, player, list, bool);
            } else {
                list.add(Tooltips.SHIFT_INFO.getLocalised());
            }
        }
    }

    public ItemGeneric hideFromNEI() {
        NEIUtils.hideFromNEI(itemID);
        return this;
    }

}