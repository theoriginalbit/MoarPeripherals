package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

public final class InventoryUtils {

    public final static void explodeInventory(IInventory inv, World world, int x, int y, int z) {
        Random rand = new Random();
        for (int i = 0; i < inv.getSizeInventory(); ++i) {
            ItemStack item = inv.getStackInSlot(i);
            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8f + 0.1f;
                float ry = rand.nextFloat() * 0.8f + 0.1f;
                float rz = rand.nextFloat() * 0.8f + 0.1f;
                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }
                float factor = 0.05f;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2f;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
    }

}