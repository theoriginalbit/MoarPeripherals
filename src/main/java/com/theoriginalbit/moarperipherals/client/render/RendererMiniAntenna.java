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
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelMiniAntenna;
import com.theoriginalbit.moarperipherals.client.render.abstracts.CustomTileRenderer;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class RendererMiniAntenna extends CustomTileRenderer {

    public RendererMiniAntenna() {
        super(new ModelMiniAntenna());
    }

    @Override
    protected void manipulateTileRender(TileEntity tile) {
        GL11.glRotatef(180, 1, 0, 0);
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -1.5f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        float scale = 0.7f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -0.75f, 0f);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        GL11.glRotatef(200, 0, 0, 1);
        GL11.glRotatef(300, 0, 1, 0);
        GL11.glTranslatef(0.8f, -2f, 1.1f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        GL11.glRotatef(170, 1, 0, 0);
        GL11.glTranslatef(0.5f, -1.5f, 0.5f);
    }

    @Override
    protected float translateOffsetX() {
        return 0.5f;
    }

    @Override
    protected float translateOffsetY() {
        return 1.5f;
    }

    @Override
    protected float translateOffsetZ() {
        return 0.5f;
    }

    @Override
    protected ResourceLocation getTexture(TileEntity tile) {
        return Constants.TEXTURES_MODEL.MINI_ANTENNA.getResourceLocation();
    }

    @Override
    protected ResourceLocation getTexture(ItemStack stack) {
        return Constants.TEXTURES_MODEL.MINI_ANTENNA.getResourceLocation();
    }

}
