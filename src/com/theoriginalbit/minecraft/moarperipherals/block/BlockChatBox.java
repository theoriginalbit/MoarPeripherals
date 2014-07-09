package com.theoriginalbit.minecraft.moarperipherals.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileChatBox;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockChatBox extends BlockGeneric {
	
	public BlockChatBox() {
		super(Settings.blockIdChatBox, Material.iron, "chatbox", Block.soundMetalFootstep);
		
		GameRegistry.registerTileEntity(TileChatBox.class, ModInfo.ID + ":tileChatBox");
		GameRegistry.addRecipe(new ItemStack(this), "GGG", "GNG", "GRG", 'G', Item.ingotGold, 'N', Block.music, 'R', Item.redstone);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileChatBox();
	}
}