package com.theoriginalbit.minecraft.moarperipherals.model.item;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelItemInkCartridge extends ModelBase {
	ModelRenderer base, nozzle, clip, baseBack, baseBackSkinny;
	protected boolean cartridgeEmpty;

	public ModelItemInkCartridge(boolean empty) {
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
		clip = new ModelRenderer(this, 51, 0);
		clip.addBox(0F, 0F, 0F, 2, 9, 1);
		clip.setRotationPoint(-1F, 1F, -15F);
		clip.setTextureSize(64, 64);
		clip.mirror = true;
		setRotation(clip, 0.2356194F, 0F, 0F);
		baseBack = new ModelRenderer(this, 0, 38);
		baseBack.addBox(-2F, 0F, 0F, 4, 7, 5);
		baseBack.setRotationPoint(0F, 0F, 8F);
		baseBack.setTextureSize(64, 64);
		baseBack.mirror = true;
		setRotation(baseBack, 0F, 0F, 0F);
		
		if (!cartridgeEmpty) {
			baseBackSkinny = new ModelRenderer(this, 19, 38);
			baseBackSkinny.addBox(-1.5F, 0F, 0F, 3, 9, 5);
			baseBackSkinny.setRotationPoint(0F, 6.5F, 7.5F);
			baseBackSkinny.setTextureSize(64, 64);
			baseBackSkinny.mirror = true;
			setRotation(baseBackSkinny, 0F, 0F, 0F);
		}
	}

	public void render(Entity entity, float f, float f1, float f2, float f3,
			float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
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

	public void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

}
