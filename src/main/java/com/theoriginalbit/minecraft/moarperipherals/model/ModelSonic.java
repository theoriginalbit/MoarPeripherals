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
public class ModelSonic extends ModelBase {

    ModelRenderer stalk, extender, connector, head, body, decal1, decal2, decal3,
            decal4, decal5, blueLightSmall, blueLightLarge;

    public ModelSonic() {
        textureWidth = 128;
        textureHeight = 64;

        stalk = new ModelRenderer(this, 55, 8);
        stalk.addBox(-3F, -8F, -3F, 6, 16, 6);
        stalk.setRotationPoint(0F, -30F, 0F);
        stalk.setTextureSize(128, 64);
        stalk.mirror = true;
        setRotation(stalk, 0F, 0F, 0F);
        extender = new ModelRenderer(this, 80, 17);
        extender.addBox(-2F, -1F, -2F, 4, 2, 4);
        extender.setRotationPoint(0F, -39F, 0F);
        extender.setTextureSize(128, 64);
        extender.mirror = true;
        setRotation(extender, 0F, 0F, 0F);
        connector = new ModelRenderer(this, 55, 31);
        connector.addBox(-3F, 0F, -3F, 6, 1, 6);
        connector.setRotationPoint(0F, -41F, 0F);
        connector.setTextureSize(128, 64);
        connector.mirror = true;
        setRotation(connector, 0F, 0F, 0F);
        decal5 = new ModelRenderer(this, 55, 0);
        decal5.addBox(-3F, 0F, -3F, 6, 1, 6);
        decal5.setRotationPoint(0F, 22F, 0F);
        decal5.setTextureSize(128, 64);
        decal5.mirror = true;
        setRotation(decal5, 0F, 0F, 0F);
        head = new ModelRenderer(this, 80, 0);
        head.addBox(-4F, -4F, -4F, 8, 8, 8);
        head.setRotationPoint(0F, -45F, 0F);
        head.setTextureSize(128, 64);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        blueLightSmall = new ModelRenderer(this, 80, 24);
        blueLightSmall.addBox(-1.5F, 0F, -1.5F, 3, 1, 3);
        blueLightSmall.setRotationPoint(0F, -51F, 0F);
        blueLightSmall.setTextureSize(128, 64);
        blueLightSmall.mirror = true;
        setRotation(blueLightSmall, 0F, 0F, 0F);
        decal4 = new ModelRenderer(this, 33, 0);
        decal4.addBox(0F, -20F, -2F, 1, 40, 4);
        decal4.setRotationPoint(-5F, 0F, 0F);
        decal4.setTextureSize(128, 64);
        decal4.mirror = true;
        setRotation(decal4, 0F, 0F, 0F);
        decal3 = new ModelRenderer(this, 33, 0);
        decal3.addBox(0F, -20F, -2F, 1, 40, 4);
        decal3.setRotationPoint(4F, 0F, 0F);
        decal3.setTextureSize(128, 64);
        decal3.mirror = true;
        setRotation(decal3, 0F, 0F, 0F);
        decal2 = new ModelRenderer(this, 44, 0);
        decal2.addBox(-2F, -20F, 0F, 4, 40, 1);
        decal2.setRotationPoint(0F, 0F, 4F);
        decal2.setTextureSize(128, 64);
        decal2.mirror = true;
        setRotation(decal2, 0F, 0F, 0F);
        decal1 = new ModelRenderer(this, 44, 0);
        decal1.addBox(-2F, -20F, 0F, 4, 40, 1);
        decal1.setRotationPoint(0F, 0F, -5F);
        decal1.setTextureSize(128, 64);
        decal1.mirror = true;
        setRotation(decal1, 0F, 0F, 0F);
        blueLightLarge = new ModelRenderer(this, 55, 39);
        blueLightLarge.addBox(-3F, 0F, -3F, 6, 1, 6);
        blueLightLarge.setRotationPoint(0F, -50F, 0F);
        blueLightLarge.setTextureSize(128, 64);
        blueLightLarge.mirror = true;
        setRotation(blueLightLarge, 0F, 0F, 0F);
        body = new ModelRenderer(this, 0, 0);
        body.addBox(-4F, -22F, -4F, 8, 44, 8);
        body.setRotationPoint(0F, 0F, 0F);
        body.setTextureSize(128, 64);
        body.mirror = true;
        setRotation(body, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        stalk.render(f5);
        extender.render(f5);
        connector.render(f5);
        decal5.render(f5);
        head.render(f5);
        blueLightSmall.render(f5);
        decal4.render(f5);
        decal3.render(f5);
        decal2.render(f5);
        decal1.render(f5);
        blueLightLarge.render(f5);
        body.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
