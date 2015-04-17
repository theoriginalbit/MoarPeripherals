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
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelAntenna;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class RendererTileAntenna extends TileEntitySpecialRenderer {

    ModelAntenna model = new ModelAntenna();

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
