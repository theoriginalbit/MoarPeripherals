package com.theoriginalbit.minecraft.moarperipherals.tile.antenna;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.registry.RecipeRegistry;
import com.theoriginalbit.minecraft.moarperipherals.utils.PeripheralUtils;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

import java.util.ArrayList;

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
public class TileAntennaModem extends TileAntenna {

    private static final Double REDNET_BROADCAST = 65535.0d;
    private IPeripheral[] modems;
    private IComputerAccess dummyComputer = new FauxComputer("top");

    @Override
    public void structureCreated() {
        attachModem();
        openModems();
    }

    @Override
    public void structureDestroyed() {
        closeModems();
        detachModem();
    }

    @Override
    public void updateEntity() {
        if (isValidController() && isStructureComplete() && hasModemsConnected()) {
            refreshModems();
        }
    }

    public void attachModem() {
        if (isValidController() && hasModemsConnected()) {
            modems[0].attach(((TileAntennaController) worldObj.getBlockTileEntity(controllerX, controllerY, controllerZ)).getComputer());
            modems[1].attach(dummyComputer);
            modems[2].attach(dummyComputer);
            modems[3].attach(dummyComputer);
        }
    }

    public void detachModem() {
        if (isValidController() && hasModemsConnected()) {
            modems[0].detach(((TileAntennaController) worldObj.getBlockTileEntity(controllerX, controllerY, controllerZ)).getComputer());
            modems[1].detach(dummyComputer);
            modems[2].detach(dummyComputer);
            modems[3].detach(dummyComputer);
        }
    }

    public void openModems() {
        if (isValidController() && hasModemsConnected()) {
            for (IPeripheral modem : modems) {
                try {
                    modem.callMethod(null, null, 0, new Object[]{REDNET_BROADCAST});
                } catch (Exception e) {
                    System.out.println(String.format("Communications Tower at %d %d %d failed to open rednet broadcast channel on modem", xCoord, yCoord, zCoord));
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeModems() {
        if (isValidController() && hasModemsConnected()) {
            for (IPeripheral modem : modems) {
                try {
                    modem.callMethod(null, null, 2, new Object[]{REDNET_BROADCAST});
                } catch (Exception e) {
                    System.out.println(String.format("Communications Tower at %d %d %d failed to close rednet broadcast channel on modem", xCoord, yCoord, zCoord));
                    e.printStackTrace();
                }
            }
        }
    }

    public void refreshModems() {
        hasModemsConnected();
        attachModem();
        openModems();
    }

    public boolean hasModemsConnected() {
        ArrayList<IPeripheral> found = Lists.newArrayList();
        checkModem(found, 1, 0);
        checkModem(found, -1, 0);
        checkModem(found, 0, 1);
        checkModem(found, 0, -1);
        modems = found.toArray(new IPeripheral[found.size()]);
        return modems.length == 4;
    }

    private void checkModem(ArrayList<IPeripheral> list, int xOffset, int zOffset) {
        int x = xCoord + xOffset;
        int z = zCoord + zOffset;
        if (worldObj.getBlockId(x, yCoord, z) == RecipeRegistry.cc_wirelessModem.getItem().itemID) {
            IPeripheral p = PeripheralUtils.getIPeripheral(worldObj.getBlockTileEntity(x, yCoord, z));
            if (p != null) {
                list.add(p);
            }
        }
    }

}
