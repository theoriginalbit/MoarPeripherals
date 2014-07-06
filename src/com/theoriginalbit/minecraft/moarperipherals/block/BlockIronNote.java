package com.theoriginalbit.minecraft.moarperipherals.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockIronNote extends BlockGeneric {

	public BlockIronNote() {
		super(Settings.blockIdIronNote, Material.iron, "ironnote", Block.soundMetalFootstep);
		
		GameRegistry.registerTileEntity(TileIronNote.class, "MoarPeripherals Iron Note Block");
		GameRegistry.addRecipe(new ItemStack(this), "III", "INI", "IRI", 'I', Item.ingotIron, 'N', Block.music, 'R', Item.redstone);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileIronNote();
	}
}