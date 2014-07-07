package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import com.theoriginalbit.minecraft.moarperipherals.model.item.ModelItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderInkCartridge implements IItemRenderer {
	
	private static final ResourceLocation[] textures;
	private static final String TEXTURE_PATH = "/textures/models/InkCartridge%s.png";
	
	protected ModelBase cartridgeModel;
	protected boolean cartridgeEmpty;
	
	public ItemRenderInkCartridge(boolean empty) {
		cartridgeModel = new ModelItemInkCartridge(empty);
		cartridgeEmpty = empty;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case EQUIPPED: return Settings.enableInkCartridgeModel;
			case EQUIPPED_FIRST_PERSON: return Settings.enableInkCartridgeModel;
			case INVENTORY: return Settings.enableInkCartridgeModel;
			default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return helper == ItemRendererHelper.INVENTORY_BLOCK;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		// find the right texture
		ResourceLocation location = cartridgeEmpty ? textures[textures.length-1] : textures[item.getItemDamage()];
		float scale;
		
		switch (type) {
			case EQUIPPED:
				GL11.glPushMatrix();
				// load the texture
				Minecraft.getMinecraft().getTextureManager().bindTexture(location);
				// make it smaller, the model is too big, higher res though
				scale = 0.3f;
				GL11.glScalef(scale, scale, scale);
				// rotate it to an upward direction
				GL11.glRotatef(75, 1, 0, 0);
				// move it around on the hand
				GL11.glTranslatef(1.4F, 0.1F, -0.3F);
				// render
				cartridgeModel.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				GL11.glPopMatrix();
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glPushMatrix();
				// load the texture
				Minecraft.getMinecraft().getTextureManager().bindTexture(location);
				// make it smaller, the model is too big, higher res though
				scale = 0.25f;
				GL11.glScalef(scale, scale, scale);
				// rotate it to a direction more suited to the camera
				GL11.glRotatef(170, 0, 1, 0);
				GL11.glRotatef(90, 1, 0, 0);
				GL11.glRotatef(75, 0, 0, 1);
				// move it around in the camera (left/right, close/far, up/down)
				GL11.glTranslatef(1F, 0.25F, -2.1F);
				// render
				cartridgeModel.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				GL11.glPopMatrix();
				break;
			case INVENTORY:
				GL11.glPushMatrix();
				// load the texture
				Minecraft.getMinecraft().getTextureManager().bindTexture(location);
				// for some reason it is upside down, so lets flip it
				GL11.glRotatef(180, 1, 0, 0);
				// centre it in the inventory slot
				GL11.glTranslatef(0f, -0.5f, 0f);
				// render
				cartridgeModel.render((Entity) null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
				GL11.glPopMatrix();
				break;
			default: break;
		}
	}
	
	static {
		textures = new ResourceLocation[17];
		for (int i = 0; i < textures.length - 1; ++i) {
			textures[i] = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, String.format(TEXTURE_PATH, i));
		}
		textures[textures.length - 1] = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, String.format(TEXTURE_PATH, "Empty"));
	}
}