package com.theoriginalbit.minecraft.moarperipherals.render;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.render.base.CustomTileRenderer;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
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