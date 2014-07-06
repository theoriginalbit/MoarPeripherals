package com.theoriginalbit.minecraft.moarperipherals.handler;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.FillBucketEvent;

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