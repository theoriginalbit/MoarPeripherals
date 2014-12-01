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
package com.theoriginalbit.moarperipherals.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSonic10 extends ModelBase {

    ModelRenderer extender, headConnector, baseEnd, hLightSurroundSide1, blueLightMiddle,
            body, stalkFrame1, stalkFrame2, stalkFrame3, stalkFrame4, stalkConnector, headBase,
            headFrame1, headFrame2, headFrame3, headFrame4, wire, headCore, handleTop, hLightSurroundTop1,
            hLightSurroundSide2, hLightSurroundBottom1, handleLight1, hLightSurroundTop2, hLightSurroundTop3,
            hLightSurroundTop4, handleLight2, handleLight3, handleLight4, hLightSurroundBottom2, hLightSurroundBottom3,
            hLightSurroundBottom4, hLightSurroundSide3, hLightSurroundSide4, hLightSurroundSide5, hLightSurroundSide6,
            hLightSurroundSide7, hLightSurroundSide8, blueLightBase, headTop, blueLightTop;

    public ModelSonic10() {
        textureWidth = 128;
        textureHeight = 64;

        extender = new ModelRenderer(this, 0, 54);
        extender.addBox(-2F, -1F, -2F, 4, 1, 4);
        extender.setRotationPoint(0F, -39F, 0F);
        extender.setTextureSize(128, 64);
        extender.mirror = true;
        setRotation(extender, 0F, 0F, 0F);
        headConnector = new ModelRenderer(this, 88, 31);
        headConnector.addBox(-3F, 0F, -3F, 6, 1, 6);
        headConnector.setRotationPoint(0F, -41F, 0F);
        headConnector.setTextureSize(128, 64);
        headConnector.mirror = true;
        setRotation(headConnector, 0F, 0F, 0F);
        baseEnd = new ModelRenderer(this, 55, 0);
        baseEnd.addBox(-3F, 0F, -3F, 6, 1, 6);
        baseEnd.setRotationPoint(0F, 22F, 0F);
        baseEnd.setTextureSize(128, 64);
        baseEnd.mirror = true;
        setRotation(baseEnd, 0F, 0F, 0F);
        hLightSurroundSide1 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide1.addBox(0F, -20F, -2F, 1, 18, 1);
        hLightSurroundSide1.setRotationPoint(4F, 3F, 0F);
        hLightSurroundSide1.setTextureSize(128, 64);
        hLightSurroundSide1.mirror = true;
        setRotation(hLightSurroundSide1, 0F, 0F, 0F);
        blueLightMiddle = new ModelRenderer(this, 55, 39);
        blueLightMiddle.addBox(-2.5F, 0F, -2.5F, 6, 1, 6);
        blueLightMiddle.setRotationPoint(-0.5F, -50.5F, -0.5F);
        blueLightMiddle.setTextureSize(128, 64);
        blueLightMiddle.mirror = true;
        setRotation(blueLightMiddle, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 0);
        body.addBox(0F, 0F, 0F, 8, 44, 8);
        body.setRotationPoint(-4F, -22F, -4F);
        body.setTextureSize(128, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
        stalkFrame1 = new ModelRenderer(this, 88, 15);
        stalkFrame1.addBox(0F, 0F, 0F, 5, 13, 0);
        stalkFrame1.setRotationPoint(-2.5F, -37F, 2.5F);
        stalkFrame1.setTextureSize(128, 64);
        stalkFrame1.mirror = true;
        setRotation(stalkFrame1, 0F, 0F, 0F);
        stalkFrame2 = new ModelRenderer(this, 88, 15);
        stalkFrame2.addBox(0F, 0F, 0F, 5, 13, 0);
        stalkFrame2.setRotationPoint(-2.5F, -37F, -2.5F);
        stalkFrame2.setTextureSize(128, 64);
        stalkFrame2.mirror = true;
        setRotation(stalkFrame2, 0F, -1.570796F, 0F);
        stalkFrame3 = new ModelRenderer(this, 100, 10);
        stalkFrame3.addBox(0F, 0F, 0F, 0, 13, 5);
        stalkFrame3.setRotationPoint(2.5F, -37F, -2.5F);
        stalkFrame3.setTextureSize(128, 64);
        stalkFrame3.mirror = true;
        setRotation(stalkFrame3, 0F, -1.570796F, 0F);
        stalkFrame4 = new ModelRenderer(this, 100, 10);
        stalkFrame4.addBox(0F, 0F, 0F, 0, 13, 5);
        stalkFrame4.setRotationPoint(2.5F, -37F, -2.5F);
        stalkFrame4.setTextureSize(128, 64);
        stalkFrame4.mirror = true;
        setRotation(stalkFrame4, 0F, 0F, 0F);
        stalkConnector = new ModelRenderer(this, 88, 31);
        stalkConnector.addBox(0F, 0F, 0F, 6, 2, 6);
        stalkConnector.setRotationPoint(-3F, -39F, -3F);
        stalkConnector.setTextureSize(128, 64);
        stalkConnector.mirror = true;
        setRotation(stalkConnector, 0F, 0F, 0F);
        headBase = new ModelRenderer(this, 84, 40);
        headBase.addBox(0F, 0F, 0F, 8, 2, 8);
        headBase.setRotationPoint(-4F, -43F, -4F);
        headBase.setTextureSize(128, 64);
        headBase.mirror = true;
        setRotation(headBase, 0F, 0F, 0F);
        headFrame1 = new ModelRenderer(this, 41, 52);
        headFrame1.addBox(0F, 0F, 0F, 2, 5, 2);
        headFrame1.setRotationPoint(-4F, -48F, -4F);
        headFrame1.setTextureSize(128, 64);
        headFrame1.mirror = true;
        setRotation(headFrame1, 0F, 0F, 0F);
        headFrame2 = new ModelRenderer(this, 41, 52);
        headFrame2.addBox(0F, 0F, 0F, 2, 5, 2);
        headFrame2.setRotationPoint(-4F, -48F, 2F);
        headFrame2.setTextureSize(128, 64);
        headFrame2.mirror = true;
        setRotation(headFrame2, 0F, 0F, 0F);
        headFrame3 = new ModelRenderer(this, 41, 52);
        headFrame3.addBox(0F, 0F, 0F, 2, 5, 2);
        headFrame3.setRotationPoint(2F, -48F, -4F);
        headFrame3.setTextureSize(128, 64);
        headFrame3.mirror = true;
        setRotation(headFrame3, 0F, 0F, 0F);
        headFrame4 = new ModelRenderer(this, 41, 52);
        headFrame4.addBox(0F, 0F, 0F, 2, 5, 2);
        headFrame4.setRotationPoint(2F, -48F, 2F);
        headFrame4.setTextureSize(128, 64);
        headFrame4.mirror = true;
        setRotation(headFrame4, 0F, 0F, 0F);
        wire = new ModelRenderer(this, 65, 12);
        wire.addBox(0F, 0F, 0F, 1, 13, 1);
        wire.setRotationPoint(-0.6666667F, -37F, 0F);
        wire.setTextureSize(128, 64);
        wire.mirror = true;
        setRotation(wire, 0F, 0.7853982F, 0F);
        headCore = new ModelRenderer(this, 24, 53);
        headCore.addBox(0F, 0F, 0F, 2, 5, 2);
        headCore.setRotationPoint(-1.333333F, -48F, 0F);
        headCore.setTextureSize(128, 64);
        headCore.mirror = true;
        setRotation(headCore, 0F, 0.7853982F, 0F);
        handleTop = new ModelRenderer(this, 88, 31);
        handleTop.addBox(-3F, -24F, -3F, 6, 2, 6);
        handleTop.setRotationPoint(0F, 0F, 0F);
        handleTop.setTextureSize(128, 64);
        handleTop.mirror = true;
        setRotation(handleTop, 0F, 0F, 0F);
        hLightSurroundTop1 = new ModelRenderer(this, 83, 0);
        hLightSurroundTop1.addBox(0F, 0F, 0F, 1, 3, 4);
        hLightSurroundTop1.setRotationPoint(4F, -20F, -2F);
        hLightSurroundTop1.setTextureSize(128, 64);
        hLightSurroundTop1.mirror = true;
        setRotation(hLightSurroundTop1, 0F, 0F, 0F);
        hLightSurroundSide2 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide2.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide2.setRotationPoint(4F, -17F, 1F);
        hLightSurroundSide2.setTextureSize(128, 64);
        hLightSurroundSide2.mirror = true;
        setRotation(hLightSurroundSide2, 0F, 0F, 0F);
        hLightSurroundBottom1 = new ModelRenderer(this, 38, 0);
        hLightSurroundBottom1.addBox(0F, 0F, 0F, 1, 19, 4);
        hLightSurroundBottom1.setRotationPoint(4F, 1F, -2F);
        hLightSurroundBottom1.setTextureSize(128, 64);
        hLightSurroundBottom1.mirror = true;
        setRotation(hLightSurroundBottom1, 0F, 0F, 0F);
        handleLight1 = new ModelRenderer(this, 72, 8);
        handleLight1.addBox(0F, 0F, 0F, 0, 18, 2);
        handleLight1.setRotationPoint(4.5F, -17F, -1F);
        handleLight1.setTextureSize(128, 64);
        handleLight1.mirror = true;
        setRotation(handleLight1, 0F, 0F, 0F);
        hLightSurroundTop2 = new ModelRenderer(this, 83, 0);
        hLightSurroundTop2.addBox(0F, 0F, 0F, 1, 3, 4);
        hLightSurroundTop2.setRotationPoint(2F, -20F, 4F);
        hLightSurroundTop2.setTextureSize(128, 64);
        hLightSurroundTop2.mirror = true;
        setRotation(hLightSurroundTop2, 0F, -1.570796F, 0F);
        hLightSurroundTop3 = new ModelRenderer(this, 83, 0);
        hLightSurroundTop3.addBox(0F, 0F, 0F, 1, 3, 4);
        hLightSurroundTop3.setRotationPoint(-5F, -20F, -2F);
        hLightSurroundTop3.setTextureSize(128, 64);
        hLightSurroundTop3.mirror = true;
        setRotation(hLightSurroundTop3, 0F, 0F, 0F);
        hLightSurroundTop4 = new ModelRenderer(this, 83, 0);
        hLightSurroundTop4.addBox(0F, 0F, 0F, 1, 3, 4);
        hLightSurroundTop4.setRotationPoint(2F, -20F, -5F);
        hLightSurroundTop4.setTextureSize(128, 64);
        hLightSurroundTop4.mirror = true;
        setRotation(hLightSurroundTop4, 0F, -1.570796F, 0F);
        handleLight2 = new ModelRenderer(this, 72, 8);
        handleLight2.addBox(0F, 0F, 0F, 0, 18, 2);
        handleLight2.setRotationPoint(-4.5F, -17F, -1F);
        handleLight2.setTextureSize(128, 64);
        handleLight2.mirror = true;
        setRotation(handleLight2, 0F, 0F, 0F);
        handleLight3 = new ModelRenderer(this, 72, 8);
        handleLight3.addBox(0F, 0F, 0F, 0, 18, 2);
        handleLight3.setRotationPoint(1F, -17F, 4.5F);
        handleLight3.setTextureSize(128, 64);
        handleLight3.mirror = true;
        setRotation(handleLight3, 0F, -1.570796F, 0F);
        handleLight4 = new ModelRenderer(this, 72, 8);
        handleLight4.addBox(0F, 0F, 0F, 0, 18, 2);
        handleLight4.setRotationPoint(1F, -17F, -4.5F);
        handleLight4.setTextureSize(128, 64);
        handleLight4.mirror = true;
        setRotation(handleLight4, 0F, -1.570796F, 0F);
        hLightSurroundBottom2 = new ModelRenderer(this, 38, 24);
        hLightSurroundBottom2.addBox(0F, 0F, 0F, 1, 19, 4);
        hLightSurroundBottom2.setRotationPoint(-5F, 1F, -2F);
        hLightSurroundBottom2.setTextureSize(128, 64);
        hLightSurroundBottom2.mirror = true;
        setRotation(hLightSurroundBottom2, 0F, 0F, 0F);
        hLightSurroundBottom3 = new ModelRenderer(this, 38, 24);
        hLightSurroundBottom3.addBox(0F, 0F, 0F, 1, 19, 4);
        hLightSurroundBottom3.setRotationPoint(-2F, 1F, 5F);
        hLightSurroundBottom3.setTextureSize(128, 64);
        hLightSurroundBottom3.mirror = true;
        setRotation(hLightSurroundBottom3, 0F, 1.570796F, 0F);
        hLightSurroundBottom4 = new ModelRenderer(this, 38, 0);
        hLightSurroundBottom4.addBox(0F, 0F, 0F, 1, 19, 4);
        hLightSurroundBottom4.setRotationPoint(-2F, 1F, -4F);
        hLightSurroundBottom4.setTextureSize(128, 64);
        hLightSurroundBottom4.mirror = true;
        setRotation(hLightSurroundBottom4, 0F, 1.570796F, 0F);
        hLightSurroundSide3 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide3.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide3.setRotationPoint(-2F, -17F, -5F);
        hLightSurroundSide3.setTextureSize(128, 64);
        hLightSurroundSide3.mirror = true;
        setRotation(hLightSurroundSide3, 0F, 0F, 0F);
        hLightSurroundSide4 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide4.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide4.setRotationPoint(1F, -17F, -5F);
        hLightSurroundSide4.setTextureSize(128, 64);
        hLightSurroundSide4.mirror = true;
        setRotation(hLightSurroundSide4, 0F, 0F, 0F);
        hLightSurroundSide5 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide5.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide5.setRotationPoint(-2F, -17F, 4F);
        hLightSurroundSide5.setTextureSize(128, 64);
        hLightSurroundSide5.mirror = true;
        setRotation(hLightSurroundSide5, 0F, 0F, 0F);
        hLightSurroundSide6 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide6.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide6.setRotationPoint(1F, -17F, 4F);
        hLightSurroundSide6.setTextureSize(128, 64);
        hLightSurroundSide6.mirror = true;
        setRotation(hLightSurroundSide6, 0F, 0F, 0F);
        hLightSurroundSide7 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide7.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide7.setRotationPoint(-5F, -17F, -2F);
        hLightSurroundSide7.setTextureSize(128, 64);
        hLightSurroundSide7.mirror = true;
        setRotation(hLightSurroundSide7, 0F, 0F, 0F);
        hLightSurroundSide8 = new ModelRenderer(this, 80, 9);
        hLightSurroundSide8.addBox(0F, 0F, 0F, 1, 18, 1);
        hLightSurroundSide8.setRotationPoint(-5F, -17F, 1F);
        hLightSurroundSide8.setTextureSize(128, 64);
        hLightSurroundSide8.mirror = true;
        setRotation(hLightSurroundSide8, 0F, 0F, 0F);
        blueLightBase = new ModelRenderer(this, 53, 49);
        blueLightBase.addBox(0F, 0F, 0F, 7, 1, 7);
        blueLightBase.setRotationPoint(-3.5F, -50F, -3.5F);
        blueLightBase.setTextureSize(128, 64);
        blueLightBase.mirror = true;
        setRotation(blueLightBase, 0F, 0F, 0F);
        headTop = new ModelRenderer(this, 86, 52);
        headTop.addBox(0.5F, 0F, 0.5F, 8, 2, 8);
        headTop.setRotationPoint(-4.5F, -49.5F, -4.5F);
        headTop.setTextureSize(128, 64);
        headTop.mirror = true;
        setRotation(headTop, 0F, 0F, 0F);
        blueLightTop = new ModelRenderer(this, 57, 31);
        blueLightTop.addBox(0F, 0F, 0F, 5, 1, 5);
        blueLightTop.setRotationPoint(-2.5F, -51F, -2.5F);
        blueLightTop.setTextureSize(128, 64);
        blueLightTop.mirror = true;
        setRotation(blueLightTop, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        extender.render(f5);
        headConnector.render(f5);
        baseEnd.render(f5);
        hLightSurroundSide1.render(f5);
        blueLightMiddle.render(f5);
        body.render(f5);
        stalkFrame1.render(f5);
        stalkFrame2.render(f5);
        stalkFrame3.render(f5);
        stalkFrame4.render(f5);
        stalkConnector.render(f5);
        headBase.render(f5);
        headFrame1.render(f5);
        headFrame2.render(f5);
        headFrame3.render(f5);
        headFrame4.render(f5);
        wire.render(f5);
        headCore.render(f5);
        handleTop.render(f5);
        hLightSurroundTop1.render(f5);
        hLightSurroundSide2.render(f5);
        hLightSurroundBottom1.render(f5);
        handleLight1.render(f5);
        hLightSurroundTop2.render(f5);
        hLightSurroundTop3.render(f5);
        hLightSurroundTop4.render(f5);
        handleLight2.render(f5);
        handleLight3.render(f5);
        handleLight4.render(f5);
        hLightSurroundBottom2.render(f5);
        hLightSurroundBottom3.render(f5);
        hLightSurroundBottom4.render(f5);
        hLightSurroundSide3.render(f5);
        hLightSurroundSide4.render(f5);
        hLightSurroundSide5.render(f5);
        hLightSurroundSide6.render(f5);
        hLightSurroundSide7.render(f5);
        hLightSurroundSide8.render(f5);
        blueLightBase.render(f5);
        headTop.render(f5);
        blueLightTop.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
