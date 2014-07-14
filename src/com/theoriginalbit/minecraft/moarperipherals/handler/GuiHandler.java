package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.gui.GuiKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.gui.GuiType;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.utils.ChatUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	private static final String MESSAGE_CONFIGURE = "moarperipherals.gui.keyboard.configure";
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		GuiType gui = GuiType.valueOf(id);
		if (gui != null) {
			switch (gui) {
				case KEYBOARD:
					TileKeyboard tile = (TileKeyboard) world.getBlockTileEntity(x, y, z);
					if (tile.hasConnection()) {
						return new GuiKeyboard(tile, player);
					}
					if (world.isRemote) {
						ChatUtils.sendChatToPlayer(player.username, StatCollector.translateToLocal(MESSAGE_CONFIGURE));
					}
					return null;
				default: return null;
			}
		}
		return null;
	}
}