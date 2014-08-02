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

    protected TileAntennaController controller;
    private Integer nbtTargetX, nbtTargetY, nbtTargetZ;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("controllerX") && tag.hasKey("controllerY") && tag.hasKey("controllerZ")) {
            nbtTargetX = tag.getInteger("controllerX");
            nbtTargetY = tag.getInteger("controllerY");
            nbtTargetZ = tag.getInteger("controllerZ");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        if (controller != null && !controller.isInvalid()) {
            tag.setInteger("controllerX", controller.xCoord);
            tag.setInteger("controllerY", controller.yCoord);
            tag.setInteger("controllerZ", controller.zCoord);
        }
    }

    @Override
    public void updateEntity() {
        if (nbtTargetX != null && nbtTargetY != null && nbtTargetZ != null) {
            connectToController(nbtTargetX, nbtTargetY, nbtTargetZ);
            controller.blockAdded();
            nbtTargetX = nbtTargetY = nbtTargetZ = null;
        }
    }

    @Override
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z) {
        // search for the controller
        for (int i = 1; i < 16; ++i){
            if (connectToController(xCoord, yCoord - i, zCoord)) {
                controller.blockAdded();
                break;
            }
        }
    }

    @Override
    public void onBreak(int x, int y, int z) {
        if (isValidController()) {
            controller.blockRemoved();
        }
    }

    public boolean connectToController(int x, int y, int z) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if (tile != null && tile instanceof TileAntennaController) {
            controller = (TileAntennaController) tile;
            return true;
        }
        return false;
    }

    protected boolean isValidController() {
        return controller != null && !controller.isInvalid();
    }

}
