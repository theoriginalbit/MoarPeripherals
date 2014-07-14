package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.gui.GuiType;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.utils.ChatUtils;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockKeyboard extends BlockGeneric {
	
	private static final String NOT_PAIRED = "moarperipherals.gui.keyboard.notPaired";
	
	public BlockKeyboard() {
		super(Settings.blockIdKeyboard, Material.rock, "keyboard");
		setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileKeyboard();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public final boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		return isOnTopOfSolidBlock(world, x, y, z, ForgeDirection.getOrientation(side).getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int face = MathHelper.floor_double((double) (entity.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
		int meta = ForgeDirection.getOrientation(face).getOpposite().ordinal();
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile instanceof TileKeyboard) {
			TileKeyboard keyboard = (TileKeyboard) tile;
			if (keyboard.hasConnection()) {
				player.openGui(MoarPeripherals.instance, GuiType.KEYBOARD.ordinal(), world, x, y, z);
			} else {
				ChatUtils.sendChatToPlayer(player.username, StatCollector.translateToLocal(NOT_PAIRED));
			}
			return ((IActivateAwareTile) tile).onActivated(player, side, hitX, hitY, hitZ);
		}
		
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	private final boolean isOnTopOfSolidBlock(World world, int x, int y, int z, ForgeDirection side) {
		return side == ForgeDirection.DOWN && isNeighborBlockSolid(world, x, y, z, ForgeDirection.DOWN);
	}
	
	private final static boolean isNeighborBlockSolid(World world, int x, int y, int z, ForgeDirection side) {
		x += side.offsetX;
		y += side.offsetY;
		z += side.offsetZ;
		return world.isBlockSolidOnSide(x, y, z, side.getOpposite());
	}
}