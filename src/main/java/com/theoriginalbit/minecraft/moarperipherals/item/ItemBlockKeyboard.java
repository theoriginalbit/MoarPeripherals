package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.utils.ChatUtils;
import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;
import com.theoriginalbit.minecraft.moarperipherals.utils.KeyboardUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlockKeyboard extends ItemBlock {

    private static final String CHAT_PAIRED = "moarperipherals.gui.keyboard.paired";
    private static final String TOOLTIP_PAIRED = "moarperipherals.tooltip.keyboard.paired";
    private static final String TOOLTIP_NOT_PAIRED = "moarperipherals.tooltip.keyboard.notPaired";
    private static final String TOOLTIP_INFO = "moarperipherals.tooltip.generic.information";

    public ItemBlockKeyboard(int id) {
        super(id);
        setMaxStackSize(1);
        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + ".keyboard");
    }

    private static final NBTTagCompound getItemTag(ItemStack stack) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound("tag");
        }
        return stack.stackTagCompound;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        if (super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta)) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null && tile instanceof TileKeyboard) {
                setupKeyboard(stack, (TileKeyboard) tile);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && !player.isSneaking() && stack.getItem() instanceof ItemBlockKeyboard && ComputerUtils.getTileComputerBase(world, x, y, z) != null) {
            NBTTagCompound tag = new NBTTagCompound("tag");
            tag.setInteger("targetX", x);
            tag.setInteger("targetY", y);
            tag.setInteger("targetZ", z);
            stack.setTagCompound(tag);
            ChatUtils.sendChatToPlayer(player.username, StatCollector.translateToLocalFormatted(CHAT_PAIRED, x, y, z));
            return true;
        }
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    private void setupKeyboard(ItemStack stack, TileKeyboard tile) {
        NBTTagCompound tag = getItemTag(stack);
        if (tag != null) {
            int x = tag.getInteger("targetX");
            int y = tag.getInteger("targetY");
            int z = tag.getInteger("targetZ");
            tile.configureTarget(x, y, z);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        if (KeyboardUtils.isShiftKeyDown()) {
            NBTTagCompound tag = getItemTag(stack);
            if (tag != null && tag.hasKey("targetX") && tag.hasKey("targetY") && tag.hasKey("targetZ")) {
                int x = tag.getInteger("targetX");
                int y = tag.getInteger("targetY");
                int z = tag.getInteger("targetZ");
                list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal(TOOLTIP_PAIRED));
                list.add(EnumChatFormatting.ITALIC + "  X: " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + x);
                list.add(EnumChatFormatting.ITALIC + "  Y: " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + y);
                list.add(EnumChatFormatting.ITALIC + "  Z: " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + z);
            } else {
                list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal(TOOLTIP_NOT_PAIRED));
            }
        } else {
            list.add(StatCollector.translateToLocal(TOOLTIP_INFO));
        }
    }

}