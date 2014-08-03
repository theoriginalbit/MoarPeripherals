package com.theoriginalbit.minecraft.moarperipherals.render.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
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
        Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture(stack));

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

    protected abstract ResourceLocation getTexture(ItemStack stack);

    protected abstract void manipulateEntityRender(ItemStack stack);

    protected abstract void manipulateInventoryRender(ItemStack stack);

    protected abstract void manipulateThirdPersonRender(ItemStack stack);

    protected abstract void manipulateFirstPersonRender(ItemStack stack);

}