/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelPrinter;
import com.theoriginalbit.moarperipherals.reference.Constants;
import com.theoriginalbit.moarperipherals.client.render.base.CustomTileRenderer;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RendererPrinter extends CustomTileRenderer {

    private static final ModelBase printerEmpty = new ModelPrinter();
    private static final ModelBase printerFeeder = new ModelPrinter(true, false);
    private static final ModelBase printerOutput = new ModelPrinter(false, true);
    private static final ModelBase printerBoth = new ModelPrinter(true, true);

    public RendererPrinter() {
        super(printerEmpty);
    }

    @Override
    protected void manipulateTileRender(TileEntity tile) {
        selectModel((TilePrinter) tile);
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glTranslatef(-1f, -0.44f, 1f);
        // FIXME: adjustRotatePivotViaMeta(tile);
    }

    @Override
    protected float translateOffsetX() {
        return 0;
    }

    @Override
    protected float translateOffsetY() {
        return 0;
    }

    @Override
    protected float translateOffsetZ() {
        return 0;
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        float scale = 0.9f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -0.5f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        // switch to the model with paper as it looks better
        model = printerBoth;
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, 0.3f, 0f);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        // switch to the model with paper as it looks better
        model = printerEmpty;
        float scale = 0.8f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(120, 1, 0, 0);
        GL11.glRotatef(-40, 0, 0, 1);
        GL11.glRotatef(-100, 0, 1, 0);
        GL11.glTranslatef(-1.8f, 0.5f, 0.5f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        // switch to the model with paper as it looks better
        model = printerBoth;
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(170, 0, 0, 1);
        GL11.glRotatef(40, 0, 1, 0);
        GL11.glRotatef(-60, 1, 0, 0);
        GL11.glTranslatef(-2.2f, -1f, -1.5f);
    }

    private void selectModel(TilePrinter tile) {
        model = printerEmpty;
    }

    @Override
    protected ResourceLocation getTexture(TileEntity tile) {
        return Constants.TEXTURES_MODEL.PRINTER_IDLE.getResourceLocation();
    }

    @Override
    protected ResourceLocation getTexture(ItemStack stack) {
        return Constants.TEXTURES_MODEL.PRINTER_IDLE.getResourceLocation();
    }

}