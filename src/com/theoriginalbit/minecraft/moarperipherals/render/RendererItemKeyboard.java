package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelTileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RendererItemKeyboard implements IItemRenderer {

	private final ModelTileKeyboard modelKeyboard = new ModelTileKeyboard();
	private static final ResourceLocation TEXTURE = new ResourceLocation(
			ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard.png");

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return Settings.enableInkCartridgeModel
				&& (type == ItemRenderType.ENTITY
						|| type == ItemRenderType.EQUIPPED
						|| type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.INVENTORY);
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.INVENTORY_BLOCK
				|| helper == ItemRendererHelper.ENTITY_ROTATION
				|| helper == ItemRendererHelper.ENTITY_BOBBING;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		float scale;

		switch (type) {
		case ENTITY:
			GL11.glPushMatrix();
			// load the texture
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			// for some reason it is upside down, so lets flip it
			GL11.glRotatef(180, 1, 0, 0);
			// centre it in the inventory slot
			GL11.glTranslatef(0f, 0f, 0f);
			// render
			modelKeyboard.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
			// load the texture
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			// make it smaller, the model is too big, higher res though
			scale = 0.6f;
			GL11.glScalef(scale, scale, scale);
			// rotate it to an upward direction
			GL11.glRotatef(200, 0, 0, 1);
			GL11.glRotatef(5, 0, 1, 0);
			GL11.glRotatef(70, 1, 0, 0);
			// move it around on the hand
			GL11.glTranslatef(-0.6f, -0.1f, 0.3f);
			// render
			modelKeyboard.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
			// load the texture
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			// make it smaller, the model is too big, higher res though
			scale = 0.25f;
			GL11.glScalef(scale, scale, scale);
			// rotate it to a direction more suited to the camera
			GL11.glRotatef(160, 0, 1, 0);
			GL11.glRotatef(190, 1, 0, 0);
			GL11.glRotatef(75, 0, 0, 1);
			// move it around in the camera (left/right, close/far, up/down)
			GL11.glTranslatef(-1.8f, -1.2f, -1.3f);
			// render
			modelKeyboard.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
			GL11.glPopMatrix();
			break;
		case INVENTORY:
			GL11.glPushMatrix();
			// load the texture
			Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
			scale = 0.7f;
			GL11.glScalef(scale, scale, scale);
			// for some reason it is upside down, so lets flip it
			GL11.glRotatef(180, 1, 0, 0);
			// centre it in the inventory slot
			GL11.glTranslatef(0f, 0f, 0f);
			// render
			modelKeyboard.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
			GL11.glPopMatrix();
			break;
		default:
			break;
		}
	}

}
