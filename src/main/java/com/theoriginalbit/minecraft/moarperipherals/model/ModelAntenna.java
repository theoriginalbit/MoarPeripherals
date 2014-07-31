package com.theoriginalbit.minecraft.moarperipherals.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class ModelAntenna extends ModelBase {

    protected ModelRenderer poleTop, poleBottom, poleMiddle, cellAnchor,
            modemPanel1, modemPanel2, modemPanel3, modemPanel4, modemSupport1, modemSupport2,
            cellSupport1, cellSupport2, cellSupport3, cellSupport4, cellSupport5, cellSupport6,
            cellSupport7, cellSupport8, cell1, cell3, cell5, cell8, cell6, cell2, cell7, cell4,
            structSupport1, structSupport2, structSupport3, structSupport4, anchorSupport1, anchorSupport2;

    public ModelAntenna() {
        textureWidth = 256;
        textureHeight = 128;

        poleBottom = new ModelRenderer(this, 0, 0);
        poleBottom.addBox(-6F, -48F, -6F, 12, 96, 12);
        poleBottom.setRotationPoint(0F, -24F, 0F);
        poleBottom.setTextureSize(256, 128);
        poleBottom.mirror = true;
        setRotation(poleBottom, 0F, 0F, 0F);
        poleMiddle = new ModelRenderer(this, 49, 0);
        poleMiddle.addBox(-4F, -40F, -4F, 8, 80, 8);
        poleMiddle.setRotationPoint(0F, -112F, 0F);
        poleMiddle.setTextureSize(256, 128);
        poleMiddle.mirror = true;
        setRotation(poleMiddle, 0F, 0F, 0F);
        poleTop = new ModelRenderer(this, 83, 0);
        poleTop.addBox(-2F, -40F, -2F, 4, 84, 4);
        poleTop.setRotationPoint(0F, -196F, 0F);
        poleTop.setTextureSize(256, 128);
        poleTop.mirror = true;
        setRotation(poleTop, 0F, 0F, 0F);
        cellAnchor = new ModelRenderer(this, 128, 38);
        cellAnchor.addBox(-5F, 0F, -5F, 10, 28, 10);
        cellAnchor.setRotationPoint(0F, -230F, 0F);
        cellAnchor.setTextureSize(256, 128);
        cellAnchor.mirror = true;
        setRotation(cellAnchor, 0F, 0F, 0F);
        modemPanel1 = new ModelRenderer(this, 100, 0);
        modemPanel1.addBox(-6F, -6F, -1F, 12, 12, 1);
        modemPanel1.setRotationPoint(0F, -192F, -7F);
        modemPanel1.setTextureSize(256, 128);
        modemPanel1.mirror = true;
        setRotation(modemPanel1, 0F, 0F, 0F);
        modemPanel2 = new ModelRenderer(this, 100, 0);
        modemPanel2.addBox(-6F, -6F, 0F, 12, 12, 1);
        modemPanel2.setRotationPoint(0F, -192F, 8F);
        modemPanel2.setTextureSize(256, 128);
        modemPanel2.mirror = true;
        setRotation(modemPanel2, 0F, 3.141593F, 0F);
        modemPanel3 = new ModelRenderer(this, 100, 0);
        modemPanel3.addBox(-6F, -6F, -1F, 12, 12, 1);
        modemPanel3.setRotationPoint(-7F, -192F, 0F);
        modemPanel3.setTextureSize(256, 128);
        modemPanel3.mirror = true;
        setRotation(modemPanel3, 0F, 1.570796F, 0F);
        modemPanel4 = new ModelRenderer(this, 100, 0);
        modemPanel4.addBox(-6F, -6F, -1F, 12, 12, 1);
        modemPanel4.setRotationPoint(7F, -192F, 0F);
        modemPanel4.setTextureSize(256, 128);
        modemPanel4.mirror = true;
        setRotation(modemPanel4, 0F, -1.570796F, 0F);
        modemSupport1 = new ModelRenderer(this, 49, 90);
        modemSupport1.addBox(-7F, -1F, -1F, 14, 2, 2);
        modemSupport1.setRotationPoint(0F, -192F, 0F);
        modemSupport1.setTextureSize(256, 128);
        modemSupport1.mirror = true;
        setRotation(modemSupport1, 0F, 0F, 0F);
        modemSupport2 = new ModelRenderer(this, 49, 90);
        modemSupport2.addBox(-7F, -1F, -1F, 14, 2, 2);
        modemSupport2.setRotationPoint(0F, -192F, 0F);
        modemSupport2.setTextureSize(256, 128);
        modemSupport2.mirror = true;
        setRotation(modemSupport2, 0F, -1.570796F, 0F);
        cellSupport1 = new ModelRenderer(this, 50, 105);
        cellSupport1.addBox(-17F, -1F, 0F, 34, 2, 1);
        cellSupport1.setRotationPoint(-12F, -224F, -12F);
        cellSupport1.setTextureSize(256, 128);
        cellSupport1.mirror = true;
        setRotation(cellSupport1, 0F, 0.7853982F, 0F);
        cellSupport2 = new ModelRenderer(this, 50, 105);
        cellSupport2.addBox(-17F, -1F, 0F, 34, 2, 1);
        cellSupport2.setRotationPoint(-12F, -209F, -12F);
        cellSupport2.setTextureSize(256, 128);
        cellSupport2.mirror = true;
        setRotation(cellSupport2, 0F, 0.7853982F, 0F);
        cellSupport3 = new ModelRenderer(this, 50, 105);
        cellSupport3.addBox(-17F, -1F, -1F, 34, 2, 1);
        cellSupport3.setRotationPoint(12F, -209F, 12F);
        cellSupport3.setTextureSize(256, 128);
        cellSupport3.mirror = true;
        setRotation(cellSupport3, 0F, 0.7853982F, 0F);
        cellSupport4 = new ModelRenderer(this, 50, 105);
        cellSupport4.addBox(-17F, -1F, -1F, 34, 2, 1);
        cellSupport4.setRotationPoint(12F, -224F, 12F);
        cellSupport4.setTextureSize(256, 128);
        cellSupport4.mirror = true;
        setRotation(cellSupport4, 0F, 0.7853982F, 0F);
        cellSupport5 = new ModelRenderer(this, 50, 105);
        cellSupport5.addBox(-16F, -1F, -1F, 32, 2, 1);
        cellSupport5.setRotationPoint(-12F, -224F, 12F);
        cellSupport5.setTextureSize(256, 128);
        cellSupport5.mirror = true;
        setRotation(cellSupport5, 0F, -0.7853982F, 0F);
        cellSupport6 = new ModelRenderer(this, 50, 105);
        cellSupport6.addBox(-16F, -1F, -1F, 32, 2, 1);
        cellSupport6.setRotationPoint(-12F, -209F, 12F);
        cellSupport6.setTextureSize(256, 128);
        cellSupport6.mirror = true;
        setRotation(cellSupport6, 0F, -0.7853982F, 0F);
        cellSupport7 = new ModelRenderer(this, 50, 105);
        cellSupport7.addBox(-16F, -1F, 0F, 32, 2, 1);
        cellSupport7.setRotationPoint(12F, -209F, -12F);
        cellSupport7.setTextureSize(256, 128);
        cellSupport7.mirror = true;
        setRotation(cellSupport7, 0F, -0.7853982F, 0F);
        cellSupport8 = new ModelRenderer(this, 50, 105);
        cellSupport8.addBox(-16F, -1F, 0F, 32, 2, 1);
        cellSupport8.setRotationPoint(12F, -224F, -12F);
        cellSupport8.setTextureSize(256, 128);
        cellSupport8.mirror = true;
        setRotation(cellSupport8, 0F, -0.7853982F, 0F);
        cell1 = new ModelRenderer(this, 128, 0);
        cell1.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell1.setRotationPoint(-6F, -216F, -21F);
        cell1.setTextureSize(256, 128);
        cell1.mirror = true;
        setRotation(cell1, 0F, 0.7853982F, 0F);
        cell3 = new ModelRenderer(this, 128, 0);
        cell3.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell3.setRotationPoint(-21F, -216F, 6F);
        cell3.setTextureSize(256, 128);
        cell3.mirror = true;
        setRotation(cell3, 0F, -0.7853982F, 0F);
        cell5 = new ModelRenderer(this, 128, 0);
        cell5.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell5.setRotationPoint(6F, -216F, 21F);
        cell5.setTextureSize(256, 128);
        cell5.mirror = true;
        setRotation(cell5, 0F, 0.7853982F, 0F);
        cell8 = new ModelRenderer(this, 128, 0);
        cell8.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell8.setRotationPoint(6F, -216F, -21F);
        cell8.setTextureSize(256, 128);
        cell8.mirror = true;
        setRotation(cell8, 0F, -0.7853982F, 0F);
        cell6 = new ModelRenderer(this, 128, 0);
        cell6.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell6.setRotationPoint(21F, -216F, 6F);
        cell6.setTextureSize(256, 128);
        cell6.mirror = true;
        setRotation(cell6, 0F, 0.7853982F, 0F);
        cell2 = new ModelRenderer(this, 128, 0);
        cell2.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell2.setRotationPoint(-21F, -216F, -6F);
        cell2.setTextureSize(256, 128);
        cell2.mirror = true;
        setRotation(cell2, 0F, 0.7853982F, 0F);
        cell7 = new ModelRenderer(this, 128, 0);
        cell7.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell7.setRotationPoint(21F, -216F, -6F);
        cell7.setTextureSize(256, 128);
        cell7.mirror = true;
        setRotation(cell7, 0F, -0.7853982F, 0F);
        cell4 = new ModelRenderer(this, 128, 0);
        cell4.addBox(-4F, -16F, -2F, 8, 32, 4);
        cell4.setRotationPoint(-6F, -216F, 21F);
        cell4.setTextureSize(256, 128);
        cell4.mirror = true;
        setRotation(cell4, 0F, -0.7853982F, 0F);
        structSupport1 = new ModelRenderer(this, 100, 14);
        structSupport1.addBox(-1F, -5F, 0F, 2, 13, 1);
        structSupport1.setRotationPoint(-12F, -218F, -12F);
        structSupport1.setTextureSize(256, 128);
        structSupport1.mirror = true;
        setRotation(structSupport1, 0F, 0.7853982F, 0F);
        structSupport2 = new ModelRenderer(this, 100, 14);
        structSupport2.addBox(-1F, -5F, -1F, 2, 13, 1);
        structSupport2.setRotationPoint(12F, -218F, 12F);
        structSupport2.setTextureSize(256, 128);
        structSupport2.mirror = true;
        setRotation(structSupport2, 0F, 0.7853982F, 0F);
        structSupport3 = new ModelRenderer(this, 100, 14);
        structSupport3.addBox(-1F, -5F, 0F, 2, 13, 1);
        structSupport3.setRotationPoint(12F, -218F, -12F);
        structSupport3.setTextureSize(256, 128);
        structSupport3.mirror = true;
        setRotation(structSupport3, 0F, -0.7853982F, 0F);
        structSupport4 = new ModelRenderer(this, 100, 14);
        structSupport4.addBox(-1F, -5F, -1F, 2, 13, 1);
        structSupport4.setRotationPoint(-12F, -218F, 12F);
        structSupport4.setTextureSize(256, 128);
        structSupport4.mirror = true;
        setRotation(structSupport4, 0F, -0.7853982F, 0F);
        anchorSupport1 = new ModelRenderer(this, 49, 96);
        anchorSupport1.addBox(-16F, -1F, -1F, 32, 2, 2);
        anchorSupport1.setRotationPoint(0F, -218F, 0F);
        anchorSupport1.setTextureSize(256, 128);
        anchorSupport1.mirror = true;
        setRotation(anchorSupport1, 0F, -0.7853982F, 0F);
        anchorSupport2 = new ModelRenderer(this, 49, 96);
        anchorSupport2.addBox(-16F, -1F, -1F, 32, 2, 2);
        anchorSupport2.setRotationPoint(0F, -218F, 0F);
        anchorSupport2.setTextureSize(256, 128);
        anchorSupport2.mirror = true;
        setRotation(anchorSupport2, 0F, 0.7853982F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        poleBottom.render(f5);
        poleMiddle.render(f5);
        poleTop.render(f5);
        cellAnchor.render(f5);
        modemPanel1.render(f5);
        modemPanel2.render(f5);
        modemPanel3.render(f5);
        modemPanel4.render(f5);
        modemSupport1.render(f5);
        modemSupport2.render(f5);
        cellSupport1.render(f5);
        cellSupport2.render(f5);
        cellSupport3.render(f5);
        cellSupport4.render(f5);
        cellSupport5.render(f5);
        cellSupport6.render(f5);
        cellSupport7.render(f5);
        cellSupport8.render(f5);
        cell1.render(f5);
        cell3.render(f5);
        cell5.render(f5);
        cell8.render(f5);
        cell6.render(f5);
        cell2.render(f5);
        cell7.render(f5);
        cell4.render(f5);
        structSupport1.render(f5);
        structSupport2.render(f5);
        structSupport3.render(f5);
        structSupport4.render(f5);
        anchorSupport1.render(f5);
        anchorSupport2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
