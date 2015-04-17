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
import net.minecraft.entity.Entity;

public class ModelInkCartridge extends ModelBase {
    ModelRenderer base;
    ModelRenderer nozzle;
    ModelRenderer clipBottom;
    ModelRenderer baseBack;
    ModelRenderer baseBackSkinny;
    ModelRenderer inkNozzle1;
    ModelRenderer inkNozzle2;
    ModelRenderer inkNozzle3;
    ModelRenderer clipTop;
    private final boolean cartridgeEmpty;

    public ModelInkCartridge(boolean empty) {
        textureWidth = 64;
        textureHeight = 64;
        cartridgeEmpty = empty;

        base = new ModelRenderer(this, 0, 0);
        base.addBox(-2F, 0F, -13F, 4, 16, 21);
        base.setRotationPoint(0F, 0F, 0F);
        base.setTextureSize(64, 64);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        nozzle = new ModelRenderer(this, 36, 38);
        nozzle.addBox(-1.5F, 0F, -4F, 3, 1, 8);
        nozzle.setRotationPoint(0F, 16F, -8F);
        nozzle.setTextureSize(64, 64);
        nozzle.mirror = true;
        setRotation(nozzle, 0F, 0F, 0F);
        clipBottom = new ModelRenderer(this, 51, 0);
        clipBottom.addBox(0F, 5F, -3F, 2, 4, 1);
        clipBottom.setRotationPoint(-1F, 0F, -15.5F);
        clipBottom.setTextureSize(64, 64);
        clipBottom.mirror = true;
        setRotation(clipBottom, 0.7417649F, 0F, 0F);
        baseBack = new ModelRenderer(this, 0, 38);
        baseBack.addBox(-2F, 0F, 0F, 4, 7, 5);
        baseBack.setRotationPoint(0F, 0F, 8F);
        baseBack.setTextureSize(64, 64);
        baseBack.mirror = true;
        setRotation(baseBack, 0F, 0F, 0F);
        baseBackSkinny = new ModelRenderer(this, 19, 38);
        baseBackSkinny.addBox(-1.5F, 0F, 0F, 3, 9, 5);
        baseBackSkinny.setRotationPoint(0F, 6.5F, 7.5F);
        baseBackSkinny.setTextureSize(64, 64);
        baseBackSkinny.mirror = true;
        setRotation(baseBackSkinny, 0F, 0F, 0F);
        inkNozzle1 = new ModelRenderer(this, 52, 15);
        inkNozzle1.addBox(0F, 0F, 0F, 1, 1, 1);
        inkNozzle1.setRotationPoint(-0.5F, 16.3F, -11F);
        inkNozzle1.setTextureSize(64, 64);
        inkNozzle1.mirror = true;
        setRotation(inkNozzle1, 0F, 0F, 0F);
        inkNozzle2 = new ModelRenderer(this, 52, 15);
        inkNozzle2.addBox(0F, 0F, 0F, 1, 1, 1);
        inkNozzle2.setRotationPoint(-0.5F, 16.3F, -9F);
        inkNozzle2.setTextureSize(64, 64);
        inkNozzle2.mirror = true;
        setRotation(inkNozzle2, 0F, 0F, 0F);
        inkNozzle3 = new ModelRenderer(this, 51, 11);
        inkNozzle3.addBox(0F, 0F, 0F, 1, 1, 2);
        inkNozzle3.setRotationPoint(-0.5F, 16.3F, -7F);
        inkNozzle3.setTextureSize(64, 64);
        inkNozzle3.mirror = true;
        setRotation(inkNozzle3, 0F, 0F, 0F);
        clipTop = new ModelRenderer(this, 51, 0);
        clipTop.addBox(-1F, -0.4F, -15.5F, 2, 4, 1);
        clipTop.setRotationPoint(0F, 0F, 0.5F);
        clipTop.setTextureSize(64, 64);
        clipTop.mirror = true;
        setRotation(clipTop, 0.1396263F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        base.render(f5);
        nozzle.render(f5);
        clipBottom.render(f5);
        baseBack.render(f5);
        if (!cartridgeEmpty) {
            baseBackSkinny.render(f5);
        }
        inkNozzle1.render(f5);
        inkNozzle2.render(f5);
        inkNozzle3.render(f5);
        clipTop.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
