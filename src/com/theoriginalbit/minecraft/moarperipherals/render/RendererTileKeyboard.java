package com.theoriginalbit.minecraft.moarperipherals.render;

import org.lwjgl.opengl.GL11;

import com.theoriginalbit.minecraft.moarperipherals.model.ModelTileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RendererTileKeyboard extends TileEntitySpecialRenderer {
	
	private final ModelTileKeyboard modelKeyboard = new ModelTileKeyboard();
	private static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.485f, (float) y + 0.06f, (float) z + 0.5f);
		scale = 0.5f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(180, 0, 0, 1);
		
		GL11.glPushMatrix();
		adjustRotatePivotViaMeta(tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
		bindTexture(TEXTURE);
		modelKeyboard.render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		GL11.glRotatef((meta - 1) * -90, 0, 1, 0);
	}

//	private void adjustLightFixture(World world, int i, int j, int k, Block block) {
//		Tessellator tess = Tessellator.instance;
//		// float brightness = block.getBlockBrightness(world, i, j, k);
//		// As of MC 1.7+ block.getBlockBrightness() has become
//		// block.getLightValue():
//		float brightness = block.getLightValue(world, i, j, k);
//		int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
//		int modulousModifier = skyLight % 65536;
//		int divModifier = skyLight / 65536;
//		tess.setColorOpaque_F(brightness, brightness, brightness);
//		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit,
//				(float) modulousModifier, divModifier);
//	}
}