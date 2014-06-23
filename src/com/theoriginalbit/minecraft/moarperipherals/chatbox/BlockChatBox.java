package com.theoriginalbit.minecraft.moarperipherals.chatbox;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.theoriginalbit.minecraft.moarperipherals.generic.BlockGeneric;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

public class BlockChatBox extends BlockGeneric {
	
	public BlockChatBox() {
		super(Settings.blockChatBoxID, Material.iron, "chatbox");
		setStepSound(Block.soundMetalFootstep);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileChatBox(world);
	}
}
