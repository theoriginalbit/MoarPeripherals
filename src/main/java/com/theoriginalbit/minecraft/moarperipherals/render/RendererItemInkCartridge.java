package com.theoriginalbit.minecraft.moarperipherals.render;

import com.theoriginalbit.minecraft.moarperipherals.item.ItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.model.ModelItemInkCartridge;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public final class RendererItemInkCartridge extends CustomItemRenderer {

    private static final ResourceLocation[] textures;
    private static final ModelBase modelCartridgeEmpty = new ModelItemInkCartridge(true);
    private static final ModelBase modelCartridgeFilled = new ModelItemInkCartridge(false);

    public RendererItemInkCartridge() {
        super(modelCartridgeEmpty);
    }

    @Override
    protected ResourceLocation getTexture(ItemStack stack) {
        int inkColor = ItemInkCartridge.getInkColor(stack);
        return textures[inkColor];
    }

    @Override
    protected void manipulateEntityRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.24f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
        GL11.glTranslatef(0f, -0.5f, 0f);
    }

    @Override
    protected void manipulateInventoryRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.6f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(180, 1, 0, 0);
    }

    @Override
    protected void manipulateThirdPersonRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.3f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(120, 1, 0, 0);
        GL11.glRotatef(-55, 0, 0, 1);
        GL11.glRotatef(-40, 0, 1, 0);
        GL11.glTranslatef(-3.2f, 2.1f, -1.7f);
    }

    @Override
    protected void manipulateFirstPersonRender(ItemStack stack) {
        selectModel(stack);
        float scale = 0.5f;
        GL11.glScalef(scale, scale, scale);
        GL11.glRotatef(170, 0, 0, 1);
        GL11.glRotatef(40, 0, 1, 0);
        GL11.glRotatef(-60, 1, 0, 0);
        GL11.glTranslatef(-2.2f, -1f, -1.5f);
    }

    private void selectModel(ItemStack stack) {
        modelItem = ItemInkCartridge.isCartridgeEmpty(stack) ? modelCartridgeEmpty : modelCartridgeFilled;
    }

    static {
        final String texturePath = Constants.TEXTURES.MODELS.INK_CARTRIDGE.getPath();
        textures = new ResourceLocation[17];
        for (int i = 0; i < textures.length - 1; ++i) {
            textures[i] = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, String.format(texturePath, i));
        }
        textures[textures.length - 1] = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, String.format(texturePath, "Empty"));
    }

}