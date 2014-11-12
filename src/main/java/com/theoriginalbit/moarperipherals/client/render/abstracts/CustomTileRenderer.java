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
package com.theoriginalbit.moarperipherals.client.render.abstracts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public abstract class CustomTileRenderer extends TileEntitySpecialRenderer implements IItemRenderer {

    protected ModelBase model;

    public CustomTileRenderer(ModelBase tile) {
        model = tile;
    }

    //##########################################//
    // TileEntitySpecialRenderer implementation //
    //##########################################//

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + translateOffsetX(), (float) y + translateOffsetY(), (float) z + translateOffsetZ());
        manipulateTileRender(tile);
        GL11.glPushMatrix();
        bindTexture(getTexture(tile));
        model.render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    // force methods that are used in the implementations of the TileEntitySpecialRenderer#renderTileEntityAt method
    protected abstract ResourceLocation getTexture(TileEntity tile);

    protected abstract void manipulateTileRender(TileEntity tile);

    protected abstract float translateOffsetX();

    protected abstract float translateOffsetY();

    protected abstract float translateOffsetZ();

    //##############################//
    // IItemRenderer implementation //
    //##############################//

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public final void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(stack));

        switch (type) {
            case ENTITY:
                manipulateEntityRender(stack);
                model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case EQUIPPED:
                manipulateThirdPersonRender(stack);
                model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case EQUIPPED_FIRST_PERSON:
                manipulateFirstPersonRender(stack);
                model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case INVENTORY:
                manipulateInventoryRender(stack);
                model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            default:
                break;
        }

        GL11.glPopMatrix();
    }

    // force methods that are used in the implementations of the IItemRenderer#renderItem method
    protected abstract ResourceLocation getTexture(ItemStack stack);

    protected abstract void manipulateEntityRender(ItemStack stack);

    protected abstract void manipulateInventoryRender(ItemStack stack);

    protected abstract void manipulateThirdPersonRender(ItemStack stack);

    protected abstract void manipulateFirstPersonRender(ItemStack stack);

}