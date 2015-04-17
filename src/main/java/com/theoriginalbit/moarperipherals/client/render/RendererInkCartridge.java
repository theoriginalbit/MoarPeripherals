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
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelInkCartridge;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public class RendererInkCartridge implements IItemRenderer {
    private static final ModelBase modelCartridgeEmpty = new ModelInkCartridge(true);
    private static final ModelBase modelCartridgeFilled = new ModelInkCartridge(false);

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public final void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(stack));

        final ModelBase model = selectModel(stack);

        switch (type) {
            case ENTITY:
                manipulateEntityRender();
                model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case EQUIPPED:
                manipulateThirdPersonRender();
                model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case EQUIPPED_FIRST_PERSON:
                manipulateFirstPersonRender();
                model.render((Entity) data[1], 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            case INVENTORY:
                manipulateInventoryRender();
                model.render(null, 0f, 0f, 0f, 0f, 0f, 0.0625f);
                break;
            default:
                break;
        }

        glPopMatrix();
    }

    private void manipulateEntityRender() {
        float scale = 0.24f;
        glScalef(scale, scale, scale);
        glRotatef(180, 1, 0, 0);
        glTranslatef(0f, -0.5f, 0f);
    }

    private void manipulateThirdPersonRender() {
        float scale = 0.3f;
        glScalef(scale, scale, scale);
        glRotatef(120, 1, 0, 0);
        glRotatef(-55, 0, 0, 1);
        glRotatef(-40, 0, 1, 0);
        glTranslatef(-3.2f, 2.1f, -1.7f);
    }

    private void manipulateFirstPersonRender() {
        float scale = 0.5f;
        glScalef(scale, scale, scale);
        glRotatef(170, 0, 0, 1);
        glRotatef(40, 0, 1, 0);
        glRotatef(-60, 1, 0, 0);
        glTranslatef(-2.2f, -1f, -1.5f);
    }

    private void manipulateInventoryRender() {
        float scale = 0.6f;
        glScalef(scale, scale, scale);
        glRotatef(180, 1, 0, 0);
        glTranslatef(0f, -0.5f, 0f);
    }

    private ResourceLocation getTexture(ItemStack stack) {
        final Constants.TextureStore texture;
        switch (stack.getItemDamage()) {
            case 0:
                texture = Constants.TEXTURES_MODEL.INK_CARTRIDGE_C;
                break;
            case 1:
                texture = Constants.TEXTURES_MODEL.INK_CARTRIDGE_M;
                break;
            case 2:
                texture = Constants.TEXTURES_MODEL.INK_CARTRIDGE_Y;
                break;
            case 3:
                texture = Constants.TEXTURES_MODEL.INK_CARTRIDGE_K;
                break;
            default:
                texture = Constants.TEXTURES_MODEL.INK_CARTRIDGE_E;
        }
        return texture.getResourceLocation();
    }

    private ModelBase selectModel(ItemStack stack) {
        return stack.getItemDamage() == 4 ? modelCartridgeEmpty : modelCartridgeFilled;
    }
}
