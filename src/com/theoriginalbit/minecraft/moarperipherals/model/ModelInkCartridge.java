package com.theoriginalbit.minecraft.moarperipherals.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelInkCartridge extends ModelBase {
	ModelRenderer Body;
	ModelRenderer Port;

	public ModelInkCartridge() {
		textureWidth = 64;
		textureHeight = 32;

		Body = new ModelRenderer(this, 0, 0);
		Body.addBox(-1F, 0F, 0F, 2, 4, 7);
		Body.setRotationPoint(0F, 0F, 0F);
		Body.setTextureSize(64, 32);
		Body.mirror = true;
		setRotation(Body, 0F, 0F, 0F);
		Port = new ModelRenderer(this, 0, 0);
		Port.addBox(-0.5F, -0.5F, 0.5F, 1, 1, 2);
		Port.setRotationPoint(0F, 4F, 0F);
		Port.setTextureSize(64, 32);
		Port.mirror = true;
		setRotation(Port, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Body.render(f5);
		Port.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
}