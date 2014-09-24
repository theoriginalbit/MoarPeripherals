/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.handler;

import com.theoriginalbit.moarperipherals.client.gui.GuiKeyboard;
import com.theoriginalbit.moarperipherals.client.gui.GuiType;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
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
                    TileKeyboard tile = (TileKeyboard) world.getTileEntity(x, y, z);
                    return new GuiKeyboard(tile, player);
                default:
                    return null;
            }
        }
        return null;
    }

}