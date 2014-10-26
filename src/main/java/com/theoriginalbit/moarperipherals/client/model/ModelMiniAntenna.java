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

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class ModelMiniAntenna extends ModelBase {
    ModelRenderer centralMastBase;
    ModelRenderer stand1;
    ModelRenderer base;
    ModelRenderer stand2;
    ModelRenderer stand3;
    ModelRenderer stand4;
    ModelRenderer upperSupport;
    ModelRenderer antenna1;
    ModelRenderer antenna2;
    ModelRenderer antenna3;
    ModelRenderer antenna4;
    ModelRenderer lowerSupport;
    ModelRenderer centralMast;

    public ModelMiniAntenna() {
        textureWidth = 64;
        textureHeight = 32;

        centralMastBase = new ModelRenderer(this, 29, 12);
        centralMastBase.addBox(0F, 0F, 0F, 3, 6, 3);
        centralMastBase.setRotationPoint(-1.5F, 14F, -1.5F);
        centralMastBase.setTextureSize(64, 32);
        centralMastBase.mirror = true;
        setRotation(centralMastBase, 0F, 0F, 0F);
        stand1 = new ModelRenderer(this, 0, 22);
        stand1.addBox(0F, 0F, 0F, 1, 1, 1);
        stand1.setRotationPoint(2F, 21F, -0.5F);
        stand1.setTextureSize(64, 32);
        stand1.mirror = true;
        setRotation(stand1, 0F, 0F, 0F);
        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 10, 1, 10);
        base.setRotationPoint(-5F, 23F, -5F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        stand2 = new ModelRenderer(this, 0, 22);
        stand2.addBox(0F, 0F, 0F, 1, 1, 1);
        stand2.setRotationPoint(-3F, 21F, -0.5F);
        stand2.setTextureSize(64, 32);
        stand2.mirror = true;
        setRotation(stand2, 0F, 0F, 0F);
        stand3 = new ModelRenderer(this, 0, 22);
        stand3.addBox(0F, 0F, 0F, 1, 1, 1);
        stand3.setRotationPoint(-0.5F, 21F, 2F);
        stand3.setTextureSize(64, 32);
        stand3.mirror = true;
        setRotation(stand3, 0F, 0F, 0F);
        stand4 = new ModelRenderer(this, 0, 22);
        stand4.addBox(0F, 0F, 0F, 1, 1, 1);
        stand4.setRotationPoint(-0.5F, 21F, -3F);
        stand4.setTextureSize(64, 32);
        stand4.mirror = true;
        setRotation(stand4, 0F, 0F, 0F);
        upperSupport = new ModelRenderer(this, 0, 12);
        upperSupport.addBox(0F, 0F, 0F, 7, 1, 7);
        upperSupport.setRotationPoint(-3.5F, 20F, -3.5F);
        upperSupport.setTextureSize(64, 32);
        upperSupport.mirror = true;
        setRotation(upperSupport, 0F, 0F, 0F);
        antenna1 = new ModelRenderer(this, 5, 22);
        antenna1.addBox(0F, 0F, 0F, 1, 8, 1);
        antenna1.setRotationPoint(-0.5F, 0F, 1.5F);
        antenna1.setTextureSize(64, 32);
        antenna1.mirror = true;
        setRotation(antenna1, 0F, 0F, 0F);
        antenna2 = new ModelRenderer(this, 5, 22);
        antenna2.addBox(0F, 0F, 0F, 1, 8, 1);
        antenna2.setRotationPoint(1.5F, 0F, -0.5F);
        antenna2.setTextureSize(64, 32);
        antenna2.mirror = true;
        setRotation(antenna2, 0F, 0F, 0F);
        antenna3 = new ModelRenderer(this, 5, 22);
        antenna3.addBox(0F, 0F, 0F, 1, 8, 1);
        antenna3.setRotationPoint(-2.5F, 0F, -0.5F);
        antenna3.setTextureSize(64, 32);
        antenna3.mirror = true;
        setRotation(antenna3, 0F, 0F, 0F);
        antenna4 = new ModelRenderer(this, 5, 22);
        antenna4.addBox(0F, 0F, 0F, 1, 8, 1);
        antenna4.setRotationPoint(-0.5F, 0F, -2.5F);
        antenna4.setTextureSize(64, 32);
        antenna4.mirror = true;
        setRotation(antenna4, 0F, 0F, 0F);
        lowerSupport = new ModelRenderer(this, 0, 12);
        lowerSupport.addBox(0F, 0F, 0F, 7, 2, 7);
        lowerSupport.setRotationPoint(-3.5F, 21.5F, -3.5F);
        lowerSupport.setTextureSize(64, 32);
        lowerSupport.mirror = true;
        setRotation(lowerSupport, 0F, 0F, 0F);
        centralMast = new ModelRenderer(this, 10, 21);
        centralMast.addBox(0F, 0F, 0F, 1, 10, 1);
        centralMast.setRotationPoint(-0.5F, 4F, -0.5F);
        centralMast.setTextureSize(64, 32);
        centralMast.mirror = true;
        setRotation(centralMast, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        centralMastBase.render(f5);
        stand1.render(f5);
        base.render(f5);
        stand2.render(f5);
        stand3.render(f5);
        stand4.render(f5);
        upperSupport.render(f5);
        antenna1.render(f5);
        antenna2.render(f5);
        antenna3.render(f5);
        antenna4.render(f5);
        lowerSupport.render(f5);
        centralMast.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
