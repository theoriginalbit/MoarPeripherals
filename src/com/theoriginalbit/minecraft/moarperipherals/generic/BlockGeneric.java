package com.theoriginalbit.minecraft.moarperipherals.generic;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.api.IHasGui;
import com.theoriginalbit.minecraft.moarperipherals.api.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.utils.InventoryUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockGeneric extends BlockContainer {
	protected Icon icon;
	private String name;

	protected BlockGeneric(int id, Material material, String blockName) {
		super(id, material);
		setHardness(0.5f);
		setResistance(10.0f);
		setUnlocalizedName(ModInfo.ID + "." + blockName);
		setCreativeTab(MoarPeripherals.creativeTab);
		name = blockName;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister registry) {
		icon = registry.registerIcon(ModInfo.ID + ":" + name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int x, int y, int z, int side) {
		int meta = access.getBlockMetadata(x, y, z);
		return getIcon(meta, side);
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
			player.openGui(MoarPeripherals.instance, ((IHasGui) tile).getGuiId(), world, x, y, z);
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
			InventoryUtil.explodeInventory((IInventory)tile, world, x, y, z);
		
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile instanceof INeighborAwareTile)
			((INeighborAwareTile) tile).onNeighbourChanged(blockId);
		
		super.onNeighborBlockChange(world, x, y, z, blockId);
	}

}
