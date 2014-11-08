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

import com.theoriginalbit.moarperipherals.client.model.ModelKeyboard;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.client.render.abstracts.CustomTileRenderer;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RendererKeyboard extends CustomTileRenderer {

    public RendererKeyboard() {
        super(new ModelKeyboard());
    }

    @Override
    protected void manipulateTileRender(TileEntity tile) {
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glTranslatef(-0.06f, 0f, 0.03f);
        adjustRotatePivotViaMeta(tile);
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        GL11.glRotatef(180, 1, 0, 0);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        float scale = 0.7f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        GL11.glRotatef(200, 0, 0, 1);
        GL11.glRotatef(300, 0, 1, 0);
        GL11.glRotatef(50, 1, 0, 0);
        GL11.glTranslatef(0.8f, 0.2f, 1.3f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        GL11.glRotatef(160, 0, 1, 0);
        GL11.glRotatef(190, 1, 0, 0);
        GL11.glRotatef(75, 0, 0, 1);
        GL11.glTranslatef(-1f, 1.5f, 0.2f);
    }

    @Override
    protected float translateOffsetX() {
        return 0.485f;
    }

    @Override
    protected float translateOffsetY() {
        return 0.06f;
    }

    @Override
    protected float translateOffsetZ() {
        return 0.5f;
    }

    @Override
    protected ResourceLocation getTexture(TileEntity tile) {
        return ((TileKeyboard) tile).getTextureForRender();
    }

    @Override
    protected ResourceLocation getTexture(ItemStack stack) {
        return Constants.TEXTURES_MODEL.KEYBOARD.getResourceLocation();
    }

    private void adjustRotatePivotViaMeta(TileEntity tile) {
        int meta = tile.getBlockMetadata();
        switch (meta) {
            case 2: /* no rotate */ break;
            case 3: GL11.glRotatef(180, 0, 1, 0); break;
            case 4: GL11.glRotatef(-90, 0, 1, 0); break;
            case 5: GL11.glRotatef(90, 0, 1, 0); break;
            default: break;
        }
    }

}