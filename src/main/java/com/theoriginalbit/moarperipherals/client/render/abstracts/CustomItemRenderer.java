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
package com.theoriginalbit.moarperipherals.client.render.abstracts;

import com.theoriginalbit.moarperipherals.common.reference.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public abstract class CustomItemRenderer implements IItemRenderer {

    private ModelBase modelItem;

    public CustomItemRenderer(ModelBase model) {
        modelItem = model;
    }

    @Override
    public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
        switch (type) {
            case ENTITY:
                return true; // item on the ground
            case EQUIPPED:
                return true; // item being seen in 3rd person
            case EQUIPPED_FIRST_PERSON:
                return true; // item being seen in 1st person
            case INVENTORY:
                return true; // item being seen in the inventory
            default:
                return false;
        }
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        switch (helper) {
            case INVENTORY_BLOCK:
                return true;
            case ENTITY_BOBBING:
                return true; // this makes the item bob when on the ground
            case ENTITY_ROTATION:
                return true; // this makes the item rotate when on the ground
            case EQUIPPED_BLOCK:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(type, stack).getResourceLocation());

        ModelBase model = selectModel(stack);

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
            default:
                break;
        }

        GL11.glPopMatrix();
    }

    protected ModelBase selectModel(ItemStack stack) {
        return modelItem;
    }

    protected abstract Constants.TextureStore getTexture(ItemRenderType type, ItemStack stack);

    protected abstract void manipulateEntityRender(ItemStack stack);

    protected abstract void manipulateInventoryRender(ItemStack stack);

    protected abstract void manipulateThirdPersonRender(ItemStack stack);

    protected abstract void manipulateFirstPersonRender(ItemStack stack);

}