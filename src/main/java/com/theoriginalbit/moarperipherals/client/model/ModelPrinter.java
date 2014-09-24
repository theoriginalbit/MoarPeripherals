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

public class ModelPrinter extends ModelBase {

    protected ModelRenderer bodyLeft, bodyRight, bodyUpperMiddle, bodyLowerMiddle,
            feederSlope, holePlug, backLowerLeft, backLowerRight,
            backUpperLeft, backUpperRight, outputTray, paperFeeder, paper,
            output;
    protected boolean hasPaper, hasPrintout;

    public ModelPrinter() {
        this(false, false);
    }

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
        if (hasPaper) {
            paper = new ModelRenderer(this, 0, 87);
            paper.addBox(-7.5F, -8F, -0.5F, 15, 17, 1);
            paper.setRotationPoint(0F, -12F, 14F);
            paper.setTextureSize(256, 128);
            paper.mirror = true;
            setRotation(paper, -0.3839724F, 0F, 0F);
        }
        if (hasPrintout) {
            output = new ModelRenderer(this, 35, 88);
            output.addBox(-7.5F, -1F, -13F, 15, 1, 26);
            output.setRotationPoint(0F, 6F, -11F);
            output.setTextureSize(256, 128);
            output.mirror = true;
            setRotation(output, 0F, 0F, 0F);
        }
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
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}