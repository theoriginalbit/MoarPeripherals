package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelPrinter;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModelTextures;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePrinter;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RendererPrinter extends CustomTileRenderer {
	
	private static final ModelBase printerEmpty = new ModelPrinter();
	private static final ModelBase printerFeeder = new ModelPrinter(true, false);
	private static final ModelBase printerOutput = new ModelPrinter(false, true);
	private static final ModelBase printerBoth = new ModelPrinter(true, true);

	public RendererPrinter() {
		super(printerEmpty);
	}
	
	@Override
	protected void manipulateTileRender(TileEntity tile) {
		selectModel((TilePrinter) tile);
		float scale = 0.5f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glTranslatef(-1f, -0.44f, 1f);
		adjustRotatePivotViaMeta(tile);
	}

	@Override
	protected float translateOffsetX() {
		return 0;
	}

	@Override
	protected float translateOffsetY() {
		return 0;
	}

	@Override
	protected float translateOffsetZ() {
		return 0;
	}
	
	@Override
	protected void manipulateEntityRender(ItemStack stack) {
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glTranslatef(0f, -0.5f, 0f);
	}

	@Override
	protected void manipulateInventoryRender(ItemStack stack) {
		float scale = 0.55f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180, 1, 0, 0);
	}

	@Override
	protected void manipulateThirdPersonRender(ItemStack stack) {
		float scale = 0.3f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(120, 1, 0, 0);
		GL11.glRotatef(-55, 0, 0, 1);
		GL11.glRotatef(-40, 0, 1, 0);
		GL11.glTranslatef(-3.2f, 2.1f, -1.7f);
	}

	@Override
	protected void manipulateFirstPersonRender(ItemStack stack) {
		float scale = 0.5f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(170, 0, 0, 1);
		GL11.glRotatef(40, 0, 1, 0);
		GL11.glRotatef(-60, 1, 0, 0);
		GL11.glTranslatef(-2.2f, -1f, -1.5f);
	}
	
	private void selectModel(TilePrinter tile) {
		model = printerEmpty;
	}
	
	@Override
	protected ResourceLocation getTexture(TileEntity tile) {
		return ModelTextures.PRINTER_IDLE.getTexture();
	}
	
	@Override
	protected ResourceLocation getTexture(ItemStack stack) {
		return ModelTextures.PRINTER_IDLE.getTexture();
	}
}