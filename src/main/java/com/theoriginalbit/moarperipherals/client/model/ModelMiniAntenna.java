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
package com.theoriginalbit.moarperipherals.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class ModelMiniAntenna extends ModelBase {
    private static final float scale = 0.0625F;

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
        antenna1.addBox(-0.5F, 0F, 1.5F, 1, 8, 1);
        antenna1.setRotationPoint(0F, 0F, 0F);
        antenna1.setTextureSize(64, 32);
        antenna1.mirror = true;
        setRotation(antenna1, 0F, 0F, 0F);
        antenna2 = new ModelRenderer(this, 5, 22);
        antenna2.addBox(1.5F, 0F, -0.5F, 1, 8, 1);
        antenna2.setRotationPoint(0F, 0F, 0F);
        antenna2.setTextureSize(64, 32);
        antenna2.mirror = true;
        setRotation(antenna2, 0F, 0F, 0F);
        antenna3 = new ModelRenderer(this, 5, 22);
        antenna3.addBox(-2.5F, 0F, -0.5F, 1, 8, 1);
        antenna3.setRotationPoint(0F, 0F, 0F);
        antenna3.setTextureSize(64, 32);
        antenna3.mirror = true;
        setRotation(antenna3, 0F, 0F, 0F);
        antenna4 = new ModelRenderer(this, 5, 22);
        antenna4.addBox(-0.5F, 0F, -2.5F, 1, 8, 1);
        antenna4.setRotationPoint(0F, 0F, 0F);
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
        centralMast.addBox(-0.5F, 0F, -0.5F, 1, 10, 1);
        centralMast.setRotationPoint(0F, 4F, 0F);
        centralMast.setTextureSize(64, 32);
        centralMast.mirror = true;
        setRotation(centralMast, 0F, 0F, 0F);
    }

    public void render(float degrees, float amount) {
        float rotation = (degrees * (float) Math.PI / 180f) % 360;
        centralMastBase.render(scale);
        stand1.render(scale);
        base.render(scale);
        stand2.render(scale);
        stand3.render(scale);
        stand4.render(scale);
        upperSupport.render(scale);
        antenna1.rotateAngleY = rotation;
        antenna2.rotateAngleY = rotation;
        antenna3.rotateAngleY = rotation;
        antenna4.rotateAngleY = rotation;
        antenna1.offsetY = amount;
        antenna2.offsetY = amount;
        antenna3.offsetY = amount;
        antenna4.offsetY = amount;
        antenna1.render(scale);
        antenna2.render(scale);
        antenna3.render(scale);
        antenna4.render(scale);
        lowerSupport.render(scale);
        centralMast.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
