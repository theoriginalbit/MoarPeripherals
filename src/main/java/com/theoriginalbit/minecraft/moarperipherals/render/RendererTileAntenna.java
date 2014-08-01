package com.theoriginalbit.minecraft.moarperipherals.render;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelAntenna;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.tile.antenna.TileAntennaController;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class RendererTileAntenna extends TileEntitySpecialRenderer {

    ModelAntenna model = new ModelAntenna();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float t) {
        TileAntennaController tile = (TileAntennaController) tileEntity;
        if (tile.isComplete()) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) x + 0.5f, (float) y + 0.5f, (float) z + 0.5f);
            GL11.glRotatef(180, 0, 0, 1);
            GL11.glTranslatef(0.0f, -1.0f, 0.0f);
            GL11.glPushMatrix();
            bindTexture(Constants.TEXTURES_MODEL.ANTENNA.getResourceLocation());
            model.render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
}
