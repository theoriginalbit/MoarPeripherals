/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelSonic10;
import com.theoriginalbit.moarperipherals.client.model.ModelSonic11;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.client.render.abstracts.CustomItemRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class RendererItemSonic extends CustomItemRenderer {

    private static final ModelBase modelTen = new ModelSonic10();
    private static final ModelBase modelEleven = new ModelSonic11();
    private int renderPass;

    public RendererItemSonic() {
        super(null);
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        renderPass = 0;
        super.renderItem(type, stack, data);
        GL11.glEnable(GL11.GL_BLEND);
        renderPass = 1;
        super.renderItem(type, stack, data);
        GL11.glDisable(GL11.GL_BLEND);
        renderPass = 2;
        super.renderItem(type, stack, data);
    }

    @Override
    protected Constants.TextureStore getTexture(ItemRenderType type, ItemStack stack) {
        boolean ten = stack.getItemDamage() == 0;
        if (renderPass == 0) {
            return ten ? Constants.TEXTURES_MODEL.SONIC_10_0 : Constants.TEXTURES_MODEL.SONIC_11_0;
        } else if (renderPass == 1) {
            return ten ? Constants.TEXTURES_MODEL.SONIC_10_1 : Constants.TEXTURES_MODEL.SONIC_11_1;
        }
        if ((Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) && type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            return ten ? Constants.TEXTURES_MODEL.SONIC_10_TIPON : Constants.TEXTURES_MODEL.SONIC_11_TIPON;
        }
        return ten ? Constants.TEXTURES_MODEL.SONIC_10_TIPOFF : Constants.TEXTURES_MODEL.SONIC_11_TIPOFF;
    }

    @Override
    protected ModelBase selectModel(ItemStack stack) {
        return stack.getItemDamage() == 0 ? modelTen : modelEleven;
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        float scale = 0.145f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -1f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        float scale = 0.3f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, 1f, 0f);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        float scale = 0.225f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(175, 1, 0, 0);
        GL11.glTranslatef(0f, -3.5f, -4.5f);
    }
    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        float scale = 0.25f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glRotatef(-90, 0, 1, 0);
        GL11.glRotatef(10, 0, 0, 1);
        GL11.glTranslatef(-3f, -3f, 0f);
    }

}
