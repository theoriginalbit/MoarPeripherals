package com.theoriginalbit.minecraft.moarperipherals.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;

public class GuiKeyboardModify extends GuiScreen {
	private final EntityPlayer player;
	private final TileKeyboard tile;

	public GuiKeyboardModify(TileKeyboard tile, EntityPlayer player) {
		this.tile = tile;
		this.player = player;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {
		drawDefaultBackground();
	}
}