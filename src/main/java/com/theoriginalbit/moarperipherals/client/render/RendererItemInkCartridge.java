/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.common.item.ItemInkCartridge;
import com.theoriginalbit.moarperipherals.client.model.ModelItemInkCartridge;
import com.theoriginalbit.moarperipherals.reference.Constants;
import com.theoriginalbit.moarperipherals.client.render.base.CustomItemRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public final class RendererItemInkCartridge extends CustomItemRenderer {

    private static final ModelBase modelCartridgeEmpty = new ModelItemInkCartridge(true);
    private static final ModelBase modelCartridgeFilled = new ModelItemInkCartridge(false);

    public RendererItemInkCartridge() {
        super(modelCartridgeEmpty);
    }

    @Override
    protected Constants.TextureStore getTexture(ItemRenderType type, ItemStack stack) {
        int inkColor = ItemInkCartridge.getInkColor(stack);
        switch (inkColor) {
            case 0: return Constants.TEXTURES_MODEL.INK_CARTRIDGE_C;
            case 1: return Constants.TEXTURES_MODEL.INK_CARTRIDGE_M;
            case 2: return Constants.TEXTURES_MODEL.INK_CARTRIDGE_Y;
            case 3: return Constants.TEXTURES_MODEL.INK_CARTRIDGE_K;
            default: return Constants.TEXTURES_MODEL.INK_CARTRIDGE_E;
        }
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.24f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -0.5f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.6f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.3f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(120, 1, 0, 0);
        GL11.glRotatef(-55, 0, 0, 1);
        GL11.glRotatef(-40, 0, 1, 0);
        GL11.glTranslatef(-3.2f, 2.1f, -1.7f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(170, 0, 0, 1);
        GL11.glRotatef(40, 0, 1, 0);
        GL11.glRotatef(-60, 1, 0, 0);
        GL11.glTranslatef(-2.2f, -1f, -1.5f);
    }

    protected ModelBase selectModel(ItemStack stack) {
        return ItemInkCartridge.isCartridgeEmpty(stack) ? modelCartridgeEmpty : modelCartridgeFilled;
    }

}