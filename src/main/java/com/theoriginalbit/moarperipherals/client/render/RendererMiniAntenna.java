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

import com.theoriginalbit.moarperipherals.client.model.ModelMiniAntenna;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TileMiniAntenna;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class RendererMiniAntenna extends TileEntitySpecialRenderer {
    private static final ResourceLocation texture = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/models/blocks/antenna/MiniAntenna.png");
    private ModelMiniAntenna model = new ModelMiniAntenna();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
        final TileMiniAntenna antenna = (TileMiniAntenna) tile;
        glPushMatrix();
        glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        glRotatef(180, 1, 0, 0);
        bindTexture(texture);
        glPushMatrix();
        model.render(antenna.getRotation(), antenna.getBob());
        glPopMatrix();
        glPopMatrix();

    }

}
