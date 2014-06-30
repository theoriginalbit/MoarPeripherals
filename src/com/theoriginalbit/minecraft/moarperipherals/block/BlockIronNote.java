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
		super(Settings.blockIronNoteID, Material.iron, "ironnote");
	}
	
	@Override
	protected void init() {
		setStepSound(Block.soundMetalFootstep);
		GameRegistry.registerBlock(this, getUnlocalizedName());
		GameRegistry.registerTileEntity(TileIronNote.class, "MoarPeripherals Iron Note Block");
		GameRegistry.addRecipe(new ItemStack(this), "III", "INI", "IRI", 'I', Item.ingotIron, 'N', Block.music, 'R', Item.redstone);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileIronNote();
	}
	
	@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int inst, int pitch) {
		float f = (float) Math.pow(2.0D, (double) (pitch - 12) / 12.0D);
		String s = null;
		switch (inst) {
			case 0:
				s = "harp";
				break;
			case 1:
				s = "bd";
				break;
			case 2:
				s = "snare";
				break;
			case 3:
				s = "hat";
				break;
			case 4:
				s = "bassattack";
				break;
		}
		
		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "note." + s, 3.0F, f);
		world.spawnParticle("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, (double) pitch / 24.0D, 0.0D, 0.0D);
		return true;
	}
}