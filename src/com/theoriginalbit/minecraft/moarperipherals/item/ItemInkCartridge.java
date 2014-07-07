package com.theoriginalbit.minecraft.moarperipherals.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInkCartridge extends ItemGeneric {
	private static final int emptyColorIndex = 16;
	private static final String TOOLTIP_LOCALIZATION = "moarperipherals.tooltip.inkCartridge.";
	
	private final ItemStack emptyCartridge;
	private final ItemStack[] inkCartridges;
	private final Icon[] icons;
	
	public ItemInkCartridge() {
		super(Settings.itemIdInkCartridge, "inkCartridge");
		
		setMaxStackSize(1);
		
		icons = new Icon[17];
		
		// create the ItemStack for the empty cartridge
		emptyCartridge = new ItemStack(this);
		
		// create an ItemStack instance of each colour
		inkCartridges = new ItemStack[16];
		for (int i = 0; i < inkCartridges.length; ++i) {
			ItemStack stack = new ItemStack(this);
			NBTTagCompound tag = new NBTTagCompound("tag");
			tag.setInteger("inkColor", i);
			tag.setFloat("inkLevel", FluidContainerRegistry.BUCKET_VOLUME);
			stack.setTagCompound(tag);
			
			// if liquids aren't enabled, fallback recipe is needed
			if (!Settings.enableFluidInk) {
				ItemStack dye = new ItemStack(Item.dyePowder, 1, 15 - i);
				GameRegistry.addShapelessRecipe(stack, emptyCartridge, dye, dye, dye, dye, dye, dye, dye, dye);
			}
			
			// Add the cartridge cleaning recipe
			GameRegistry.addShapelessRecipe(emptyCartridge, stack, new ItemStack(Item.bucketWater.setContainerItem(Item.bucketEmpty)));
			
			inkCartridges[i] = stack;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		icons[emptyColorIndex] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridgeEmpty");
		
		for (int i = 0; i < icons.length-1; ++i) {
			icons[i] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":inkCartridge" + i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		int inkColor = getInkColor(stack);
		return icons[inkColor];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(int itemId, CreativeTabs creativeTab, List itemList) {
		// add the empty ink cartridge
		itemList.add(emptyCartridge);
		
		// add the ink cartridges from the list we generated before
		for (int i = 0; i < inkCartridges.length; ++i) {
			itemList.add(inkCartridges[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		// get the ink colour
		int inkColor = getInkColor(stack);
		// if it is not empty
		if (inkColor != emptyColorIndex) {
			String contents = StatCollector.translateToLocal(TOOLTIP_LOCALIZATION + "contents") + ": ";
			// translate the colour
			String inkName = StatCollector.translateToLocal(TOOLTIP_LOCALIZATION + "ink." + inkColor);
			list.add(contents + inkName);
			// there was a colour, so also get the percent
			String percent = getInkPercent(stack);
			if (percent != null) {
				String level = StatCollector.translateToLocal(TOOLTIP_LOCALIZATION + "level");
				list.add(level + ": " + percent);
			}
		} else {
			list.add("Empty");
		}
	}
	
	public static boolean isCartridgeEmpty(ItemStack stack) {
		return getInkColor(stack) == emptyColorIndex;
	}
	
	public static int getInkColor(ItemStack stack) {
		NBTTagCompound tag = getItemTag(stack);
		if (tag.hasKey("inkColor")) {
			return tag.getInteger("inkColor");
		}
		// wasn't a colour, return the index of the empty texture
		return emptyColorIndex;
	}
	
	public static String getInkPercent(ItemStack stack) {
		NBTTagCompound tag = getItemTag(stack);
		if (tag.hasKey("inkLevel")) {
			float level = tag.getFloat("inkLevel");
			return (int) (level / FluidContainerRegistry.BUCKET_VOLUME * 100) + "%";
		}
		return null;
	}
	
	private static NBTTagCompound getItemTag(ItemStack stack) {
		if (stack.stackTagCompound == null) stack.stackTagCompound = new NBTTagCompound("tag");
		return stack.stackTagCompound;
	}
}