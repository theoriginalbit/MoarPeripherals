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
package com.moarperipherals.block;

import com.moarperipherals.MoarPeripherals;
import com.moarperipherals.client.gui.GuiType;
import com.moarperipherals.item.ItemSonic;
import com.moarperipherals.Constants;
import com.moarperipherals.tile.TileKeyboard;
import com.moarperipherals.util.BlockNotifyFlags;
import com.moarperipherals.util.ReflectionUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BlockKeyboardMac extends BlockPaired {
    private static final Class<?> CLASS_ITOOLWRENCH = ReflectionUtil.getClass("buildcraft.api.tools.IToolWrench");
    private final ForgeDirection[] validDirections = new ForgeDirection[]{
            ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST
    };

    public BlockKeyboardMac() {
        this("keyboardMac");
    }

    protected BlockKeyboardMac(String name) {
        super(Material.iron, name);
        setRotationMode(RotationMode.FOUR);
        setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
    }

    private static boolean isNeighborBlockSolid(World world, int x, int y, int z, ForgeDirection side) {
        x += side.offsetX;
        y += side.offsetY;
        z += side.offsetZ;
        return world.isSideSolid(x, y, z, side.getOpposite());
    }

    private static boolean isItemWrench(Item item) {
        return (CLASS_ITOOLWRENCH != null && item.getClass().isAssignableFrom(CLASS_ITOOLWRENCH)) || item instanceof ItemSonic;
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
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        // check if the item was a wrench first
        ItemStack equipped = player.getCurrentEquippedItem();
        if (equipped != null && isItemWrench(equipped.getItem())) {
            return true;
        }

        // open the GUI if there is a connection
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileKeyboard) {
            TileKeyboard keyboard = (TileKeyboard) tile;
            if (keyboard.hasConnection()) {
                if (keyboard.isTargetInRange()) {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            player.openGui(MoarPeripherals.INSTANCE, GuiType.KEYBOARD.ordinal(), world, x, y, z);
                        }
                    }, 1000);
                }
            } else if (!world.isRemote) {
                String message = EnumChatFormatting.RED + Constants.CHAT.CHAT_NOT_PAIRED.getLocalised();
                player.addChatComponentMessage(new ChatComponentText(message));
            }
            return ((IBlockEventHandler) tile).blockActivated(player, side, hitX, hitY, hitZ);
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
            case UP:
                return false;
            case DOWN:
                return false;
            default:
                return world.getBlockMetadata(x, y, z) == direction.ordinal() || world.setBlockMetadataWithNotify(x, y, z, direction.ordinal(), BlockNotifyFlags.SEND_TO_CLIENTS);
        }
    }

    private boolean isOnTopOfSolidBlock(World world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.DOWN && isNeighborBlockSolid(world, x, y, z, ForgeDirection.DOWN);
    }

}