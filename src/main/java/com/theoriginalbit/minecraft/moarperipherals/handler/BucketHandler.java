package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import java.util.Map;

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
public class BucketHandler {

    public static BucketHandler instance = new BucketHandler();
    public Map<Block, Item> buckets = Maps.newHashMap();

    @ForgeSubscribe
    public void onBucketFill(FillBucketEvent event) {
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result != null) {
            event.result = result;
            event.setResult(Result.ALLOW);
        }
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        int x = pos.blockX;
        int y = pos.blockY;
        int z = pos.blockZ;

        int blockID = world.getBlockId(x, y, z);
        Item bucket = buckets.get(Block.blocksList[blockID]);

        if (bucket != null && world.getBlockMetadata(x, y, z) == 0) {
            world.setBlock(x, y, z, 0);
            return new ItemStack(bucket);
        }

        return null;
    }

}