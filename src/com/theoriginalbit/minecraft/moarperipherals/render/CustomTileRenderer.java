package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

public abstract class CustomTileRenderer extends TileEntitySpecialRenderer implements IItemRenderer {
	
	protected final ModelBase model;
	
	public CustomTileRenderer(ModelBase tile) {
		model = tile;
	}
	
	//##########################################//
	// TileEntitySpecialRenderer implementation //
	//##########################################//
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + translateOffsetX(), (float) y + translateOffsetY(), (float) z + translateOffsetZ());
		manipulateTileRender(tile);
		GL11.glPushMatrix();
		bindTexture(getTexture(tile));
		model.render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
	
	protected void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		GL11.glRotatef((meta - 1) * -90, 0, 1, 0);
	}
	
	// force methods that are used in the implementations of the TileEntitySpecialRenderer#renderTileEntityAt method
	protected abstract ResourceLocation getTexture(TileEntity tile);
	protected abstract void manipulateTileRender(TileEntity tile);
	protected abstract float translateOffsetX();
	protected abstract float translateOffsetY();
	protected abstract float translateOffsetZ();
	
	//##############################//
	// IItemRenderer implementation //
	//##############################//

	@Override
	public final boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		switch (type) {
			case ENTITY: return true; // item on the ground
			case EQUIPPED: return true; // item being seen in 3rd person
			case EQUIPPED_FIRST_PERSON: return true; // item being seen in 1st person
			case INVENTORY: return true; // item being seen in the inventory
			default: return false;
		}
	}

	@Override
	public final boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (helper) {
			case INVENTORY_BLOCK: return true;
			case ENTITY_BOBBING: return true; // this makes the item bob when on the ground
			case ENTITY_ROTATION: return true; // this makes the item rotate when on the ground
			case EQUIPPED_BLOCK: return true;
			default: return false;
		}
	}

	@Override
	public final void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(stack));
		
		switch (type) {
			case ENTITY:
				manipulateEntityRender(stack);
				model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			case EQUIPPED:
				manipulateThirdPersonRender(stack);
				model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			case EQUIPPED_FIRST_PERSON:
				manipulateFirstPersonRender(stack);
				model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			case INVENTORY:
				manipulateInventoryRender(stack);
				model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
				break;
			default: break;
		}
		
		GL11.glPopMatrix();
	}
	
	// force methods that are used in the implementations of the IItemRenderer#renderItem method
	protected abstract ResourceLocation getTexture(ItemStack stack);
	protected abstract void manipulateEntityRender(ItemStack stack);
	protected abstract void manipulateInventoryRender(ItemStack stack);
	protected abstract void manipulateThirdPersonRender(ItemStack stack);
	protected abstract void manipulateFirstPersonRender(ItemStack stack);
}