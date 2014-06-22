package com.theoriginalbit.minecraft.moarperipherals.playerdetector;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.playerdetector.TilePlayerDetector;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlayerDetector extends BlockContainer {
	private Icon icon;
	
	public BlockPlayerDetector() {
		super(Settings.blockPlayerDetectorID, Material.rock);
		setUnlocalizedName(ModInfo.ID + ".playerdetector");
		setStepSound(Block.soundStoneFootstep);
		setHardness(0.5f);
		setResistance(10.0f);
		setCreativeTab(MoarPeripherals.creativeTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TilePlayerDetector();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		icon = register.registerIcon(ModInfo.ID + ":playerdetector");
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
		return getIcon(side, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (player.isSneaking()) {
			return false;
		}
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (!(tile instanceof TilePlayerDetector)) {
			return false;
		}
		return ((TilePlayerDetector) tile).onActivated(player, side, hitX, hitY, hitZ);
	}

}
