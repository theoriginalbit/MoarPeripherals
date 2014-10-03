/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import buildcraft.api.tools.IToolWrench;
import com.theoriginalbit.moarperipherals.MoarPeripherals;
import com.theoriginalbit.moarperipherals.client.gui.GuiType;
import com.theoriginalbit.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.moarperipherals.common.block.abstracts.BlockPairable;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.TileKeyboard;
import com.theoriginalbit.moarperipherals.common.utils.BlockNotifyFlags;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockKeyboard extends BlockPairable {

    private final ForgeDirection[] validDirections = new ForgeDirection[]{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST};

    public BlockKeyboard() {
        super(Material.iron, "keyboard");
        setRotationMode(RotationMode.FOUR);
        setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        // check if the item was a wrench first
        ItemStack equipped = player.getCurrentEquippedItem();
        if (equipped != null && equipped.getItem() instanceof IToolWrench) {
            return true;
        }

        // open the GUI if there is a connection
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileKeyboard) {
            TileKeyboard keyboard = (TileKeyboard) tile;
            if (keyboard.hasConnection()) {
                player.openGui(MoarPeripherals.instance, GuiType.KEYBOARD.ordinal(), world, x, y, z);
            } else if (!world.isRemote) {
                String message = EnumChatFormatting.RED + Constants.CHAT.CHAT_NOT_PAIRED.getLocalised();
                player.addChatComponentMessage(new ChatComponentText(message));
            }
            return ((IActivateAwareTile) tile).onActivated(player, side, hitX, hitY, hitZ);
        }

        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
        setBlockBounds(0f, 0f, 0f, 0.8f, 0.1f, 0.8f);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
        setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int x, int y, int z) {
        return validDirections;
    }

    @Override
    public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection direction) {
        switch (direction) {
            case UP: return false;
            case DOWN: return false;
            default:
                return world.getBlockMetadata(x, y, z) == direction.ordinal() || world.setBlockMetadataWithNotify(x, y, z, direction.ordinal(), BlockNotifyFlags.SEND_TO_CLIENTS);
        }
    }

    private boolean isOnTopOfSolidBlock(World world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.DOWN && isNeighborBlockSolid(world, x, y, z, ForgeDirection.DOWN);
    }

    private static boolean isNeighborBlockSolid(World world, int x, int y, int z, ForgeDirection side) {
        x += side.offsetX;
        y += side.offsetY;
        z += side.offsetZ;
        return world.isSideSolid(x, y, z, side.getOpposite());
    }

}