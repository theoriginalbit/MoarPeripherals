/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelMiniAntenna;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class RendererTileMiniAntenna extends TileEntitySpecialRenderer {
    ModelMiniAntenna model = new ModelMiniAntenna();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float t) {
        TileAntennaController tile = (TileAntennaController) tileEntity;
        if (tile.isTowerComplete()) {
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
