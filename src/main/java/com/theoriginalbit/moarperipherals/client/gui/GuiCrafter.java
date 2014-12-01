/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.client.gui;

import com.theoriginalbit.moarperipherals.common.inventory.ContainerCrafter;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

/**
 * @author theoriginalbit
 * @since 26/10/14
 */
public class GuiCrafter extends GuiContainer {
    private static final ResourceLocation texture = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/gui/crafter.png");
    private ContainerCrafter container;

    public GuiCrafter(ContainerCrafter container) {
        super(container);
        this.container = container;
        ySize = 222;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int a, int b) {
        super.drawGuiContainerForegroundLayer(a, b);
        String name = container.inventory.getInvName();
        fontRenderer.drawString(name, (xSize - fontRenderer.getStringWidth(name)) / 2, 6, 0x404040);
        fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 94, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        mc.renderEngine.bindTexture(texture);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        int top = (width - xSize) / 2;
        int left = (height - ySize) / 2;
        drawTexturedModalRect(top, left, 0, 0, xSize, ySize);
    }

}
