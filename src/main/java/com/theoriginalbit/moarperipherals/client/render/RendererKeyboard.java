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
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.client.model.ModelKeyboardMac;
import com.theoriginalbit.moarperipherals.client.model.ModelKeyboardPc;
import com.theoriginalbit.moarperipherals.common.block.BlockKeyboardPc;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import static org.lwjgl.opengl.GL11.*;

public final class RendererKeyboard extends TileEntitySpecialRenderer implements IItemRenderer {
//    private static
    private static final ModelBase KEYBOARD_PC = new ModelKeyboardPc();
    private static final ModelBase KEYBOARD_MAC = new ModelKeyboardMac();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
        final TileKeyboard keyboard = (TileKeyboard) tile;
        glPushMatrix();
        glTranslatef((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        // manipulate the model
        float scale = 0.5f;
        glScalef(scale, scale, scale);
        glRotatef(180, 0, 0, 1);
        glTranslatef(-0.06f, 0f, 0.03f);
        adjustRotatePivotViaMeta(tile);

        glPushMatrix();
        bindTexture(keyboard.getTextureForRender());
        int meta = tile.getBlockType() instanceof BlockKeyboardPc ? 1 : 0;
        getModel(meta).render(null, 0F, 0F, 0f, 0f, 0f, 0.0625f);
        glPopMatrix();
        glPopMatrix();
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return helper != ItemRendererHelper.BLOCK_3D;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        glPushMatrix();

        final int meta = Block.blocksList[stack.itemID] instanceof BlockKeyboardPc ? 1 : 0;
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(meta));

        final ModelBase model = getModel(meta);

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

    private ResourceLocation getTexture(int meta) {
        return meta == 1
                ? new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/models/blocks/keyboard/Keyboard_1_Off.png") // PC
                : new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/models/blocks/keyboard/Keyboard_0_Off.png"); // Mac
    }

    private ModelBase getModel(int meta) {
        return meta == 1 ? KEYBOARD_PC : KEYBOARD_MAC;
    }

    private void manipulateEntityRender() {
        float scale = 0.9f;
        glScalef(scale, scale, scale);
        glRotatef(180, 1, 0, 0);
        glTranslatef(0f, -1.5f, 0f);
    }

    private void manipulateThirdPersonRender() {
        glRotatef(200, 0, 0, 1);
        glRotatef(300, 0, 1, 0);
        glRotatef(50, 1, 0, 0);
        glTranslatef(0.5f, -1f, 1f);
    }

    private void manipulateFirstPersonRender() {
        glRotatef(160, 0, 1, 0);
        glRotatef(190, 1, 0, 0);
        glRotatef(75, 0, 0, 1);
        glTranslatef(-1f, 0.5f, 0.2f);
    }

    private void manipulateInventoryRender() {
        float scale = 0.65f;
        glScalef(scale, scale, scale);
        glRotatef(180, 1, 0, 0);
        glTranslatef(0f, -1.3f, 0f);
    }

    private void adjustRotatePivotViaMeta(TileEntity tile) {
        switch (tile.getBlockMetadata()) {
            case 2: /* no rotate */
                break;
            case 3:
                glRotatef(180, 0, 1, 0);
                break;
            case 4:
                glRotatef(-90, 0, 1, 0);
                break;
            case 5:
                glRotatef(90, 0, 1, 0);
                break;
            default:
                break;
        }
    }

}