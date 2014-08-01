package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.BlockNotifyFlags;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

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
@LuaPeripheral("bitnet_controller")
public class TileAntennaController extends TileMPBase implements IPlaceAwareTile, IBreakAwareTile {

    private static final String EVENT_MESSAGE = "bitnet_message";

    private boolean complete = false;

    public boolean isComplete() {
        return complete;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    @Override
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z) {
        blockAdded();
    }

    @Override
    public void onBreak(int x, int y, int z) {
        blockRemoved();
    }

    public void blockAdded() {
        // check if the multi-block is complete
        complete = false;

        for (int y = 1; y < 13; ++y) {
            int id = worldObj.getBlockId(xCoord, yCoord + y, zCoord);
            if (id != Settings.blockIdAntenna) {
                System.out.println("Antenna Pole missing!");
                return;
            }
        }
        System.out.println("Found all Antenna Poles!");

        if (worldObj.getBlockId(xCoord, yCoord + 13, zCoord) != Settings.blockIdAntennaModem) {
            System.out.println("Modem Interface not found!");
            return;
        }
        System.out.println("Modem Interface found!");

        if (worldObj.getBlockId(xCoord, yCoord + 14, zCoord) != Settings.blockIdAntennaCell) {
            System.out.println("First Cell not found!");
            return;
        }
        System.out.println("First Cell found!");

        if (worldObj.getBlockId(xCoord, yCoord + 15, zCoord) != Settings.blockIdAntennaCell) {
            System.out.println("Second Cell not found!");
            return;
        }
        System.out.println("Second Cell found!");
        System.out.println("Structure complete!");

        // tell all the blocks in the structure to become invisible
        for (int y = 1; y < 16; ++y) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 1, BlockNotifyFlags.ALL);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord + y, zCoord);
        }

        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);

        complete = true;
    }

    public void blockRemoved() {
        System.out.println("Block was removed from structure!");
        // tell all the blocks in the structure to become visible again
        for (int y = 1; y < 16; ++y) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord + y, zCoord, 0, BlockNotifyFlags.ALL);
        }
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        complete = false;
    }

}
