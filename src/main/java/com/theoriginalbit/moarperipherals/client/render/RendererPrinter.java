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

import com.theoriginalbit.moarperipherals.client.model.ModelPrinter;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import com.theoriginalbit.moarperipherals.common.tile.printer.PaperState;
import com.theoriginalbit.moarperipherals.common.tile.printer.PrinterState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import static org.lwjgl.opengl.GL11.*;

public class RendererPrinter extends TileEntitySpecialRenderer implements IItemRenderer {
    private static final ModelBase PRINTER_EMPTY = new ModelPrinter(false, false);
    private static final ModelBase PRINTER_INPUT = new ModelPrinter(true, false);
    private static final ModelBase PRINTER_OUTPUT = new ModelPrinter(false, true);
    private static final ModelBase PRINTER_BOTH = new ModelPrinter(true, true);

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
        final TilePrinter printer = (TilePrinter) tile;
        glPushMatrix();
        glTranslatef((float) x, (float) y, (float) z);
        // manipulate the model
        float scale = 0.5f;
        glScalef(scale, scale, scale);
        glRotatef(180, 0, 0, 1);
        glTranslatef(-1f, -0.44f, 1f);
        adjustRotatePivotViaMeta(tile);

        glPushMatrix();
        bindTexture(getTexture(printer.getPrinterState()));
        getModel(printer.getPaperState()).render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
        glPopMatrix();
        glPopMatrix();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(PrinterState.IDLE.getTexture());

        final ModelBase model = getModel(PaperState.PAPER_BOTH);

        switch (type) {
            case ENTITY:
                manipulateEntityRender();
                model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case EQUIPPED:
                manipulateThirdPersonRender();
                model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case EQUIPPED_FIRST_PERSON:
                manipulateFirstPersonRender();
                model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case INVENTORY:
                manipulateInventoryRender();
                model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            default:
                break;
        }

        glPopMatrix();
    }

    private void manipulateEntityRender() {
        float scale = 0.9f;
        glScalef(scale, scale, scale);
        glRotatef(180, 1, 0, 0);
        glTranslatef(0f, -0.5f, 0f);
    }

    private void manipulateThirdPersonRender() {
        float scale = 0.8f;
        glScalef(scale, scale, scale);
        glRotatef(120, 1, 0, 0);
        glRotatef(-40, 0, 0, 1);
        glRotatef(-100, 0, 1, 0);
        glTranslatef(-1.8f, 0.5f, 0.5f);
    }

    private void manipulateFirstPersonRender() {
        float scale = 0.5f;
        glScalef(scale, scale, scale);
        glRotatef(170, 0, 0, 1);
        glRotatef(40, 0, 1, 0);
        glRotatef(-60, 1, 0, 0);
        glTranslatef(-2.2f, -1f, -1.5f);
    }

    private void manipulateInventoryRender() {
        float scale = 0.5f;
        glScalef(scale, scale, scale);
        glRotatef(180, 1, 0, 0);
        glTranslatef(0f, 0.3f, 0f);
    }

    private ResourceLocation getTexture(PrinterState state) {
        return state.getTexture();
    }

    private ModelBase getModel(PaperState state) {
        switch (state) {
            case PAPER_NONE:
                return PRINTER_EMPTY;
            case PAPER_INPUT:
                return PRINTER_INPUT;
            case PAPER_OUTPUT:
                return PRINTER_OUTPUT;
        }
        return PRINTER_BOTH;
    }

    private void adjustRotatePivotViaMeta(TileEntity tile) {
        switch (tile.getBlockMetadata()) {
            case 2: /* no rotate */
                break;
            case 3:
                glRotatef(180, 0, 1, 0);
                break;
            case 4:
                glRotatef(-90, 0, 1, 0);
                break;
            case 5:
                glRotatef(90, 0, 1, 0);
                break;
        }
    }

}