/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.client.gui;

import com.theoriginalbit.moarperipherals.common.block.container.ContainerCrafter;
import com.theoriginalbit.moarperipherals.common.tile.TileComputerCrafter;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
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
                    return new ContainerCrafter(player, (TileComputerCrafter) world.getTileEntity(x,y, z));
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
                    return new GuiComputerCrafter(new ContainerCrafter(player, (TileComputerCrafter) world.getTileEntity(x,y, z)));
            }
        }
        return null;
    }

}