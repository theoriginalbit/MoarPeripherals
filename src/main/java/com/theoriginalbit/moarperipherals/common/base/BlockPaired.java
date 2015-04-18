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
package com.theoriginalbit.moarperipherals.common.base;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.hook.IPairedDeviceHook;
import com.theoriginalbit.moarperipherals.common.util.WorldUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class BlockPaired extends BlockRotatable {

    public BlockPaired(Material material, String blockName) {
        super(material, blockName);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return Lists.newArrayList();
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        ItemStack stack = createPairedItemStack(world, x, y, z);
        return stack != null ? stack : super.getPickBlock(target, world, x, y, z, player);
    }

    private ItemStack createPairedItemStack(World world, int x, int y, int z) {
        IPairedDeviceHook device = WorldUtil.getTileEntity(world, x, y, z, IPairedDeviceHook.class);
        if (device != null) {
            final ArrayList<ItemStack> drops = Lists.newArrayList();
            device.addDrops(drops);
            return drops.get(0);
        }
        return null;
    }

}
