package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IHasGui;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.utils.InventoryUtils;
import com.theoriginalbit.minecraft.moarperipherals.utils.NEIUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockGeneric extends BlockContainer {
	
	private final String name;
	
	public BlockGeneric(int id, Material material, String blockName) {
		this(id, material, blockName, null, true);
	}

	public BlockGeneric(int id, Material material, String blockName, StepSound stepSound) {
		this(id, material, blockName, stepSound, true);
	}
	
	public BlockGeneric(int id, Material material, String blockName, boolean inCreative) {
		this(id, material, blockName, null, inCreative);
	}

	public BlockGeneric(int id, Material material, String blockName, StepSound stepSound, boolean inCreative) {
		super(id, material);
		name = blockName;
		setHardness(0.5f);
		setResistance(10.0f);
		if (blockName != null) {
			setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + "." + name);
		}
		if (inCreative) {
			setCreativeTab(MoarPeripherals.creativeTab);
		}
		if (stepSound != null) {
			setStepSound(stepSound);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		blockIcon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + name);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile instanceof IPlaceAwareTile)
			((IPlaceAwareTile) tile).onPlaced(entity, stack, x, y, z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile instanceof IHasGui) {
			player.openGui(MoarPeripherals.instance, ((IHasGui) tile).getGuiId().ordinal(), world, x, y, z);
			return true;
		}
		
		if (tile instanceof IActivateAwareTile)
			return ((IActivateAwareTile) tile).onActivated(player, side, hitX, hitY, hitZ);
		
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile instanceof IBreakAwareTile)
			((IBreakAwareTile) tile).onBreak(x, y, z);
		
		if (tile instanceof IInventory)
			InventoryUtils.explodeInventory((IInventory)tile, world, x, y, z);
		
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile instanceof INeighborAwareTile)
			((INeighborAwareTile) tile).onNeighbourChanged(blockId);
		
		super.onNeighborBlockChange(world, x, y, z, blockId);
	}
	
	public BlockGeneric hideFromNEI() {
		NEIUtils.hideFromNEI(blockID);
		return this;
	}
}