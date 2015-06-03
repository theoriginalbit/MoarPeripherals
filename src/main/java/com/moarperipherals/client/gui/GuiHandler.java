/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.client.gui;

import com.moarperipherals.tile.TileInventory;
import com.moarperipherals.inventory.ContainerCrafter;
import com.moarperipherals.inventory.ContainerPrinter;
import com.moarperipherals.inventory.ContainerSingleSlot;
import com.moarperipherals.tile.TileComputerCrafter;
import com.moarperipherals.tile.TileKeyboard;
import com.moarperipherals.tile.TilePrinter;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        GuiType gui = GuiType.valueOf(id);
        if (gui != null) {
            switch (gui) {
                case CRAFTER:
                    return new ContainerCrafter(player, (TileComputerCrafter) world.getTileEntity(x, y, z));
                case PRINTER:
                    return new ContainerPrinter(player, (TilePrinter) world.getTileEntity(x, y, z));
                case SINGLE_SLOT:
                    return new ContainerSingleSlot(player, (TileInventory) world.getTileEntity(x, y, z));
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        GuiType gui = GuiType.valueOf(id);
        if (gui != null) {
            switch (gui) {
                case KEYBOARD:
                    return new GuiKeyboard((TileKeyboard) world.getTileEntity(x, y, z), player);
                case CRAFTER:
                    return new GuiCrafter((ContainerCrafter) getServerGuiElement(id, player, world, x, y, z));
                case PRINTER:
                    return new GuiPrinter((ContainerPrinter) getServerGuiElement(id, player, world, x, y, z));
                case SINGLE_SLOT:
                    return new GuiSingleSlot((ContainerSingleSlot) getServerGuiElement(id, player, world, x, y, z));
            }
        }
        return null;
    }

}