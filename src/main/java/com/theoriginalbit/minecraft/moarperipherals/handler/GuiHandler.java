package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.theoriginalbit.minecraft.moarperipherals.gui.GuiKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.gui.GuiType;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

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
                    return new GuiKeyboard(tile, player);
                default:
                    return null;
            }
        }
        return null;
    }

}