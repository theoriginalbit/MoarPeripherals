package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.gui.GuiKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.gui.GuiKeyboardModify;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public enum Gui {
		KEYBOARD,
		KEYBOARD_MODIFY;
		
		public static Gui valueOf(int id) {
			for (Gui gui : values()) {
				if (gui.ordinal() == id) {
					return gui;
				}
			}
			return null;
		}
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		Gui gui = Gui.valueOf(id);
		if (gui != null) {
			switch (gui) {
				case KEYBOARD:
					return new GuiKeyboard((TileKeyboard) world.getBlockTileEntity(x, y, z), player);
				case KEYBOARD_MODIFY:
					return new GuiKeyboardModify((TileKeyboard) world.getBlockTileEntity(x, y, z), player);
				default: return null;
			}
		}
		return null;
	}
}