package com.theoriginalbit.minecraft.moarperipherals.tile.antenna;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileMPBase;
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

    protected Integer controllerX, controllerY, controllerZ;

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

    public boolean isValidController() {
        return controllerX != null && controllerY != null && controllerZ != null && worldObj.getBlockTileEntity(controllerX, controllerY, controllerZ) instanceof TileAntennaController;
    }

    public boolean isStructureComplete() {
        if (controllerX != null && controllerY != null && controllerZ != null) {
            TileEntity tile = worldObj.getBlockTileEntity(controllerX, controllerY, controllerZ);
            if (tile instanceof TileAntennaController) {
                return ((TileAntennaController) tile).isComplete();
            }
        }
        return false;
    }

    public void structureCreated() {}

    public void structureDestroyed() {}

    @Override
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z) {
        // search for the controller
        for (int i = 1; i < 16; ++i){
            final TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord, yCoord - i, zCoord);
            if (tileEntity instanceof TileAntennaController) {
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
