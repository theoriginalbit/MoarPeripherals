package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelInkCartridge;
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
	private static final String TEXTURE_PATH = "textures/model/InkCartridge%s.png";
	
	protected ModelBase cartridgeModel;
	protected boolean cartridgeEmpty;
	
	public ItemRenderInkCartridge(boolean empty) {
		cartridgeModel = new ModelInkCartridge();
		cartridgeEmpty = empty;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case EQUIPPED_FIRST_PERSON: return Settings.enableInkCartridgeModel;
			default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
			case EQUIPPED_FIRST_PERSON:
				GL11.glPushMatrix();
				
				ResourceLocation location = cartridgeEmpty ? textures[textures.length] : textures[item.getItemDamage()];
				Minecraft.getMinecraft().getTextureManager().bindTexture(location);
				
				cartridgeModel.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				
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