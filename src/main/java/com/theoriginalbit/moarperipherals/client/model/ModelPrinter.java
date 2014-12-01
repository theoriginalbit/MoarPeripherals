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

public class ModelPrinter extends ModelBase {

    ModelRenderer bodyLeft;
    ModelRenderer bodyRight;
    ModelRenderer bodyUpperMiddle;
    ModelRenderer bodyLowerMiddle;
    ModelRenderer feederSlope;
    ModelRenderer holePlug;
    ModelRenderer backLowerLeft;
    ModelRenderer backLowerRight;
    ModelRenderer backUpperLeft;
    ModelRenderer backUpperRight;
    ModelRenderer outputTray;
    ModelRenderer paperFeeder;
    ModelRenderer paper;
    ModelRenderer output;
    ModelRenderer display;
    ModelRenderer buttonUp;
    ModelRenderer buttonDown;
    ModelRenderer buttonLeft;
    ModelRenderer buttonRight;
    ModelRenderer button1;
    ModelRenderer button2;
    protected boolean hasPaper, hasPrintout;

    public ModelPrinter(boolean paperPresent, boolean printoutPresent) {
        textureWidth = 256;
        textureHeight = 128;
        hasPaper = paperPresent;
        hasPrintout = printoutPresent;

        bodyLeft = new ModelRenderer(this, 0, 36);
        bodyLeft.addBox(-3.5F, -7F, -12F, 7, 14, 21);
        bodyLeft.setRotationPoint(12F, 0F, 0F);
        bodyLeft.setTextureSize(256, 128);
        bodyLeft.mirror = true;
        setRotation(bodyLeft, 0F, 0F, 0F);
        bodyRight = new ModelRenderer(this, 57, 36);
        bodyRight.addBox(-3.5F, -7F, -12F, 7, 14, 21);
        bodyRight.setRotationPoint(-12F, 0F, 0F);
        bodyRight.setTextureSize(256, 128);
        bodyRight.mirror = true;
        setRotation(bodyRight, 0F, 0F, 0F);
        bodyUpperMiddle = new ModelRenderer(this, 103, 0);
        bodyUpperMiddle.addBox(-8.5F, -7F, -12F, 17, 4, 21);
        bodyUpperMiddle.setRotationPoint(0F, 0F, 0F);
        bodyUpperMiddle.setTextureSize(256, 128);
        bodyUpperMiddle.mirror = true;
        setRotation(bodyUpperMiddle, 0F, 0F, 0F);
        bodyLowerMiddle = new ModelRenderer(this, 114, 36);
        bodyLowerMiddle.addBox(-8.5F, -4F, -11.5F, 17, 7, 23);
        bodyLowerMiddle.setRotationPoint(0F, 1F, 0.5F);
        bodyLowerMiddle.setTextureSize(256, 128);
        bodyLowerMiddle.mirror = true;
        setRotation(bodyLowerMiddle, 0F, 0F, 0F);
        feederSlope = new ModelRenderer(this, 103, 26);
        feederSlope.addBox(-10.5F, -2F, -2F, 21, 5, 4);
        feederSlope.setRotationPoint(0F, -4.2F, 8.6F);
        feederSlope.setTextureSize(256, 128);
        feederSlope.mirror = true;
        setRotation(feederSlope, 0.6632251F, 0F, 0F);
        holePlug = new ModelRenderer(this, 114, 69);
        holePlug.addBox(-8.5F, -1F, -1.5F, 17, 2, 3);
        holePlug.setRotationPoint(0F, 5F, 10.5F);
        holePlug.setTextureSize(256, 128);
        holePlug.mirror = true;
        setRotation(holePlug, 0F, 0F, 0F);
        backLowerLeft = new ModelRenderer(this, 0, 72);
        backLowerLeft.addBox(-3.5F, -5F, -1.5F, 7, 10, 3);
        backLowerLeft.setRotationPoint(12F, 2F, 10.5F);
        backLowerLeft.setTextureSize(256, 128);
        backLowerLeft.mirror = true;
        setRotation(backLowerLeft, 0F, 0F, 0F);
        backLowerRight = new ModelRenderer(this, 21, 72);
        backLowerRight.addBox(-3.5F, -5F, -1.5F, 7, 10, 3);
        backLowerRight.setRotationPoint(-12F, 2F, 10.5F);
        backLowerRight.setTextureSize(256, 128);
        backLowerRight.mirror = true;
        setRotation(backLowerRight, 0F, 0F, 0F);
        backUpperLeft = new ModelRenderer(this, 79, 72);
        backUpperLeft.addBox(-2.5F, -2F, -1.5F, 5, 4, 3);
        backUpperLeft.setRotationPoint(13F, -5F, 10.5F);
        backUpperLeft.setTextureSize(256, 128);
        backUpperLeft.mirror = true;
        setRotation(backUpperLeft, 0F, 0F, 0F);
        backUpperRight = new ModelRenderer(this, 79, 80);
        backUpperRight.addBox(-2.5F, -2F, -1.5F, 5, 4, 3);
        backUpperRight.setRotationPoint(-13F, -5F, 10.5F);
        backUpperRight.setTextureSize(256, 128);
        backUpperRight.mirror = true;
        setRotation(backUpperRight, 0F, 0F, 0F);
        outputTray = new ModelRenderer(this, 0, 0);
        outputTray.addBox(-8.5F, 0F, -18F, 17, 1, 34);
        outputTray.setRotationPoint(0F, 6F, -4F);
        outputTray.setTextureSize(256, 128);
        outputTray.mirror = true;
        setRotation(outputTray, 0F, 0F, 0F);
        paperFeeder = new ModelRenderer(this, 42, 72);
        paperFeeder.addBox(-8.5F, -7F, -0.5F, 17, 14, 1);
        paperFeeder.setRotationPoint(0F, -10F, 14F);
        paperFeeder.setTextureSize(256, 128);
        paperFeeder.mirror = true;
        setRotation(paperFeeder, -0.3839724F, 0F, 0F);
        paper = new ModelRenderer(this, 0, 87);
        paper.addBox(-7.5F, -8F, -0.5F, 15, 17, 1);
        paper.setRotationPoint(0F, -12F, 14F);
        paper.setTextureSize(256, 128);
        paper.mirror = true;
        setRotation(paper, -0.3839724F, 0F, 0F);
        output = new ModelRenderer(this, 35, 88);
        output.addBox(-7.5F, -1F, -13F, 15, 1, 26);
        output.setRotationPoint(0F, 6.4F, -11F);
        output.setTextureSize(256, 128);
        output.mirror = true;
        setRotation(output, 0F, 0F, 0F);
        display = new ModelRenderer(this, 0, 109);
        display.addBox(0F, 0F, 0F, 5, 1, 5);
        display.setRotationPoint(8.5F, -6.9F, 2F);
        display.setTextureSize(256, 128);
        display.mirror = true;
        setRotation(display, 1.047198F, 0F, 0F);
        buttonUp = new ModelRenderer(this, 25, 106);
        buttonUp.addBox(0F, 0F, 0F, 1, 1, 1);
        buttonUp.setRotationPoint(10.5F, -7.2F, -1.5F);
        buttonUp.setTextureSize(256, 128);
        buttonUp.mirror = true;
        setRotation(buttonUp, 0F, 0F, 0F);
        buttonDown = new ModelRenderer(this, 25, 106);
        buttonDown.addBox(0F, 0F, 0F, 1, 1, 1);
        buttonDown.setRotationPoint(10.5F, -7.2F, -4.1F);
        buttonDown.setTextureSize(256, 128);
        buttonDown.mirror = true;
        setRotation(buttonDown, 0F, 0F, 0F);
        buttonLeft = new ModelRenderer(this, 25, 106);
        buttonLeft.addBox(0F, 0F, -2F, 1, 1, 1);
        buttonLeft.setRotationPoint(9.3F, -7.2F, -0.8F);
        buttonLeft.setTextureSize(256, 128);
        buttonLeft.mirror = true;
        setRotation(buttonLeft, 0F, 0F, 0F);
        buttonRight = new ModelRenderer(this, 25, 106);
        buttonRight.addBox(0F, 0F, 0F, 1, 1, 1);
        buttonRight.setRotationPoint(11.7F, -7.2F, -2.8F);
        buttonRight.setTextureSize(256, 128);
        buttonRight.mirror = true;
        setRotation(buttonRight, 0F, 0F, 0F);
        button1 = new ModelRenderer(this, 24, 110);
        button1.addBox(0F, 0F, 0F, 2, 1, 1);
        button1.setRotationPoint(8.7F, -7.2F, 0.2F);
        button1.setTextureSize(256, 128);
        button1.mirror = true;
        setRotation(button1, 0F, 0F, 0F);
        button2 = new ModelRenderer(this, 24, 113);
        button2.addBox(0F, 0F, 0F, 2, 1, 1);
        button2.setRotationPoint(11.3F, -7.2F, 0.2F);
        button2.setTextureSize(256, 128);
        button2.mirror = true;
        setRotation(button2, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        bodyLeft.render(f5);
        bodyRight.render(f5);
        bodyUpperMiddle.render(f5);
        bodyLowerMiddle.render(f5);
        feederSlope.render(f5);
        holePlug.render(f5);
        backLowerLeft.render(f5);
        backLowerRight.render(f5);
        backUpperLeft.render(f5);
        backUpperRight.render(f5);
        outputTray.render(f5);
        paperFeeder.render(f5);
        if (hasPaper) {
            paper.render(f5);
        }
        if (hasPrintout) {
            output.render(f5);
        }
        display.render(f5);
        buttonUp.render(f5);
        buttonDown.render(f5);
        buttonLeft.render(f5);
        buttonRight.render(f5);
        button1.render(f5);
        button2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}