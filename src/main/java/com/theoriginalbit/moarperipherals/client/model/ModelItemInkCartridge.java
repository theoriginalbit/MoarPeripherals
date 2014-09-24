/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelItemInkCartridge extends ModelBase {

    protected ModelRenderer base, nozzle, clip, baseBack, baseBackSkinny;
    protected boolean cartridgeEmpty;

    public ModelItemInkCartridge(boolean empty) {
        textureWidth = 64;
        textureHeight = 64;
        cartridgeEmpty = empty;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-2F, -8F, -13F, 4, 16, 21);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(64, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        nozzle = new ModelRenderer(this, 36, 38);
        nozzle.addBox(-1.5F, -0.5F, -4F, 3, 1, 8);
        nozzle.setRotationPoint(0F, 8.5F, -8F);
        nozzle.setTextureSize(64, 64);
        nozzle.mirror = true;
        setRotation(nozzle, 0F, 0F, 0F);
        clip = new ModelRenderer(this, 51, 0);
        clip.addBox(-1F, -9F, -1F, 2, 9, 1);
        clip.setRotationPoint(0F, 1F, -12F);
        clip.setTextureSize(64, 64);
        clip.mirror = true;
        setRotation(clip, 0.2356194F, 0F, 0F);
        baseBack = new ModelRenderer(this, 0, 38);
        baseBack.addBox(-2F, -3.5F, -2.5F, 4, 7, 5);
        baseBack.setRotationPoint(0F, -4.5F, 10.5F);
        baseBack.setTextureSize(64, 64);
        baseBack.mirror = true;
        setRotation(baseBack, 0F, 0F, 0F);

        if (!cartridgeEmpty) {
            baseBackSkinny = new ModelRenderer(this, 19, 38);
            baseBackSkinny.addBox(-1.5F, -4.5F, -2.5F, 3, 9, 5);
            baseBackSkinny.setRotationPoint(0F, 3F, 10F);
            baseBackSkinny.setTextureSize(64, 64);
            baseBackSkinny.mirror = true;
            setRotation(baseBackSkinny, 0F, 0F, 0F);
        }
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        nozzle.render(f5);
        clip.render(f5);
        baseBack.render(f5);

        if (!cartridgeEmpty) {
            baseBackSkinny.render(f5);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}