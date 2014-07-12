package com.theoriginalbit.minecraft.moarperipherals.gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import com.theoriginalbit.minecraft.moarperipherals.keyboard.KeyboardStatus;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;

public class GuiKeyboardModify extends GuiScreen {
	protected static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/gui/keyboard.png");
	
	private final TileKeyboard tile;
	private KeyboardStatus keyboardStatus = KeyboardStatus.OTHER;
	
	private GuiTextField xTextField, yTextField, zTextField;
	private GuiButton closeButton;
	
	protected int xSize = 176;
	protected int ySize = 125;
	protected int guiLeft;
	protected int guiTop;

	public GuiKeyboardModify(TileKeyboard tileEntity) {
		tile = tileEntity;
	}
	
	@Override
	public void initGui() {
		guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;
        
        int elementPadding = 10;
        int textPosY = 42;
        int textBoxWidth = (xSize - (elementPadding*4)) / 3;
        int textBoxHeight = 15;
		xTextField = new GuiTextField(fontRenderer, elementPadding, textPosY, textBoxWidth, textBoxHeight);
		yTextField = new GuiTextField(fontRenderer, (elementPadding*2) + textBoxWidth, textPosY, textBoxWidth, textBoxHeight);
		zTextField = new GuiTextField(fontRenderer, (elementPadding*3) + (textBoxWidth*2), textPosY, textBoxWidth, textBoxHeight);
		
		int buttonHeight = 20;
		
		String close = StatCollector.translateToLocal("moarperipherals.gui.keyboard.close");
		closeButton = new GuiButton(0, elementPadding, ySize - 25, xSize - (elementPadding*2), buttonHeight, close);
		
		if (tile.hasConnection()) {
			ChunkCoordinates coords = tile.getConnectionLocation();
			xTextField.setText(String.valueOf(coords.posX));
			yTextField.setText(String.valueOf(coords.posY));
			zTextField.setText(String.valueOf(coords.posZ));
		}
		
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		drawGuiContainerBackgroundLayer(par3, par1, par2);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        super.drawScreen(par1, par2, par3);
        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        
        GL11.glDisable(GL11.GL_LIGHTING);
        drawGuiContainerForegroundLayer(par1, par2);
        GL11.glEnable(GL11.GL_LIGHTING);
        
        GL11.glPopMatrix();
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
		
		super.drawScreen(par1, par2, par3);
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
        GL11.glPopMatrix();
	}
	
	private void drawGuiContainerForegroundLayer(int a, int b) {
		a -= guiLeft;
		b -= guiTop;
		
		String title = StatCollector.translateToLocal("moarperipherals.gui.keyboard.heading");
		fontRenderer.drawString(title, (xSize - fontRenderer.getStringWidth(title))/2, 8, 0x404040);
		
		String coords = StatCollector.translateToLocal("moarperipherals.gui.keyboard.coords");
		fontRenderer.drawString(coords + ":", 10, 26, 0x404040);
		
		String status = StatCollector.translateToLocal("moarperipherals.gui.keyboard.status");
		fontRenderer.drawString(status + ":", 10, 66, 0x404040);
		
		fontRenderer.drawString(keyboardStatus.getLocal(), 10, 80, keyboardStatus.getColor());
		
		xTextField.drawTextBox();
		yTextField.drawTextBox();
		zTextField.drawTextBox();
		
		closeButton.drawButton(mc, a, b);
	}
	
	private void drawGuiContainerBackgroundLayer(float arg0, int arg1, int arg2) {
		mc.renderEngine.bindTexture(TEXTURE);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		int top = (width - xSize) / 2;
		int left = (height - ySize) / 2;
		drawTexturedModalRect(top, left, 0, 0, xSize, ySize);
	}
	
	@Override
	public void keyTyped(char ch, int key) {
		super.keyTyped(ch, key);
		
		xTextField.textboxKeyTyped(ch, key);
		yTextField.textboxKeyTyped(ch, key);
		zTextField.textboxKeyTyped(ch, key);
	}
	
	@Override
	public void mouseClicked(int x, int y, int b) {
		x -= guiLeft;
		y -= guiTop;
		
		super.mouseClicked(x, y, b);
		
		xTextField.mouseClicked(x, y, b);
		yTextField.mouseClicked(x, y, b);
		zTextField.mouseClicked(x, y, b);
		
		if (closeButton.mousePressed(mc, x, y)) {
			this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
		}
	}
	
	@Override
	public void updateScreen() {
		boolean fieldValid = validate(xTextField) && validate(yTextField) && validate(zTextField);
		boolean fieldFocused = xTextField.isFocused() || yTextField.isFocused() || zTextField.isFocused();
		
		if (fieldFocused) {
			tile.closeConnection();
			keyboardStatus = KeyboardStatus.EDITING;
		} else if (fieldValid && !fieldFocused) {
			int x = Integer.parseInt(xTextField.getText());
			int y = Integer.parseInt(yTextField.getText());
			int z = Integer.parseInt(zTextField.getText());
			
			if (!tile.isComputerInRange(new ChunkCoordinates(x, y, z))) {
				tile.closeConnection();
				keyboardStatus = KeyboardStatus.OUT_OF_RANGE;
			} else {
				tile.openConnection(x, y, z);
				boolean connected = tile.hasConnection();
				boolean valid = tile.isConnectionValid();
				if (connected && valid) {
					keyboardStatus = KeyboardStatus.CONNECTED;
				} else if (connected && !valid) {
					keyboardStatus = KeyboardStatus.INVALID;
				}
			}
		} else if (!fieldFocused) {
			if (xTextField.getText().equals("") && yTextField.getText().equals("") && zTextField.getText().equals("")) {
				tile.closeConnection();
				keyboardStatus = KeyboardStatus.DISCONNECTED;
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private final boolean validate(GuiTextField field) {
		try {
			Integer.parseInt(field.getText());
			field.setTextColor(0xffffff);
			return true;
		} catch (Exception e) {
			field.setTextColor(0xe20913);
		}
		return false;
	}
}