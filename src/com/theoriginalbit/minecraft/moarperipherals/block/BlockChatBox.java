package com.theoriginalbit.minecraft.moarperipherals.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileChatBox;

public class BlockChatBox extends BlockGeneric {
	
	public BlockChatBox() {
		super(Settings.blockIdChatBox, Material.iron, "chatbox", Block.soundMetalFootstep);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileChatBox();
	}
}