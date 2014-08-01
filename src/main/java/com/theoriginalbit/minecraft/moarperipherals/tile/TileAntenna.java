package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

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
public class TileAntenna extends TileMPBase implements IPlaceAwareTile, IBreakAwareTile {

    private Integer controllerX, controllerY, controllerZ;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("controllerX") && tag.hasKey("controllerY") && tag.hasKey("controllerZ")) {
            controllerX = tag.getInteger("controllerX");
            controllerY = tag.getInteger("controllerY");
            controllerZ = tag.getInteger("controllerZ");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (controllerX != null && controllerY != null && controllerZ != null) {
            tag.setInteger("controllerX", controllerX);
            tag.setInteger("controllerY", controllerY);
            tag.setInteger("controllerZ", controllerZ);
        }
    }

    public void setController(Integer x, Integer y, Integer z) {
        controllerX = x;
        controllerY = y;
        controllerZ = z;
    }

    @Override
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z) {
        // search for the controller
        for (int i = 1; i < 16; ++i){
            final TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
            if (tileEntity instanceof TileAntennaController) {
                System.out.println(String.format("Found controller at %d %d %d for %d %d %d!", xCoord, yCoord-i, zCoord, xCoord, yCoord, zCoord));
                // inform the controller of this block
                ((TileAntennaController) tileEntity).blockAdded();
                // inform the TileEntity for this block of the controller
                setController(xCoord, yCoord - i, zCoord);
                break;
            }
        }
    }

    @Override
    public void onBreak(int x, int y, int z) {
        if (controllerX != null && controllerY != null && controllerZ != null) {
            final TileEntity tileEntity = worldObj.getBlockTileEntity(controllerX, controllerY, controllerZ);
            if (tileEntity instanceof TileAntennaController) {
                ((TileAntennaController) tileEntity).blockRemoved();
            }
        }
    }

}
