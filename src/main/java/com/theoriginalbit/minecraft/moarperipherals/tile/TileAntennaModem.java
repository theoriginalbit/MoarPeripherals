package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.registry.RecipeRegistry;
import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.filesystem.IWritableMount;
import dan200.computercraft.api.peripheral.IComputerAccess;

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
public class TileAntennaModem extends TileAntenna implements INeighborAwareTile, IComputerAccess {

    private static final String ATTACHMENT_NAME = "top";

    @Override
    public void onNeighbourChanged(int previousBlockId) {
        final int modemId = RecipeRegistry.cc_wirelessModem.getItem().itemID;
        if (worldObj.getBlockId(xCoord + 1, yCoord, zCoord) == modemId &&
            worldObj.getBlockId(xCoord - 1, yCoord, zCoord) == modemId &&
            worldObj.getBlockId(xCoord, yCoord, zCoord + 1) == modemId &&
            worldObj.getBlockId(xCoord, yCoord, zCoord - 1) == modemId) {
            System.out.println("All modems found");
            return;
        }
        System.out.println("Missing modem(s)");
    }

    /*
     * Lets fool the Modem into thinking that we're a computer
     */

    @Override
    public String getAttachmentName() {
        return ATTACHMENT_NAME;
    }

    @Override
    public void queueEvent(String event, Object[] arguments) {

    }

    @Override
    public String mount(String desiredLocation, IMount mount) {
        return null;
    }

    @Override
    public String mountWritable(String desiredLocation, IWritableMount mount) {
        return null;
    }

    @Override
    public void unmount(String location) {}

    @Override
    public int getID() {
        return 0;
    }

}
