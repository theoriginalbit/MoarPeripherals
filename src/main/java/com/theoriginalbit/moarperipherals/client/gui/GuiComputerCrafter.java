/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.gui;

import com.theoriginalbit.moarperipherals.common.block.container.ContainerCrafter;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class GuiComputerCrafter extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/gui/crafter.png");
    private ContainerCrafter container;

    public GuiComputerCrafter(ContainerCrafter container) {
        super(container);
        this.container = container;
        ySize = 222;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int a, int b) {
        super.drawGuiContainerForegroundLayer(a, b);
        String name = container.inventory.getInventoryName();
        fontRendererObj.drawString(name, (xSize - fontRendererObj.getStringWidth(name)) / 2, 6, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 0x404040);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        mc.renderEngine.bindTexture(texture);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        int top = (width - xSize) / 2;
        int left = (height - ySize) / 2;
        drawTexturedModalRect(top, left, 0, 0, xSize, ySize);
    }

}
