/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.client.gui;

import com.moarperipherals.inventory.ContainerPrinter;
import com.moarperipherals.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public class GuiPrinter extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/gui/printer.png");
    private ContainerPrinter container;

    public GuiPrinter(ContainerPrinter printer) {
        super(printer);
        container = printer;
        ySize = 187;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int a, int b) {
        super.drawGuiContainerForegroundLayer(a, b);
        String name = container.inventory.getInventoryName();
        fontRendererObj.drawString(name, (xSize - fontRendererObj.getStringWidth(name)) / 2, 8, 0x404040);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        mc.renderEngine.bindTexture(texture);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        int top = (width - xSize) / 2;
        int left = (height - ySize) / 2;
        drawTexturedModalRect(top, left, 0, 0, xSize, ySize);
    }
}
