package com.theoriginalbit.minecraft.moarperipherals.tile.antenna;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ComputerCraftInfo;
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
public class TileAntennaModem extends TileAntenna implements INeighborAwareTile {

    private IPeripheral[] modems;
    private IComputerAccess dummyComputer = new FauxComputer("top");

    @Override
    public void onNeighbourChanged(int previousBlockId) {
        modemSearch();
    }

    public void attachModem() {
        if (isValidController() && hasModemsConnected()) {
            modems[0].attach(controller.getComputer());
            modems[1].attach(dummyComputer);
            modems[2].attach(dummyComputer);
            modems[3].attach(dummyComputer);
        }
    }

    public void detachModem() {
        if (isValidController() && hasModemsConnected()) {
            modems[0].detach(controller.getComputer());
            modems[1].detach(dummyComputer);
            modems[2].detach(dummyComputer);
            modems[3].detach(dummyComputer);
        }
    }

    public void transmit(Double channel, Object payload) {
        if (isValidController() && hasModemsConnected()) {
            channel = channel != null ? channel : ComputerCraftInfo.REDNET_BROADCAST;
            try {
                modems[0].callMethod(null, null, 4, new Object[]{channel, channel, payload});
            } catch (Exception e) {
                System.out.println(String.format("Communications Tower at %d %d %d failed to transmit message over channel %d", xCoord, yCoord, zCoord, channel.intValue()));
            }
        }
    }

    public void openChannel(double channel) {
        if (isValidController() && hasModemsConnected()) {
            for (IPeripheral modem : modems) {
                try {
                    modem.callMethod(null, null, 0, new Object[]{channel});
                } catch (Exception e) {
                    System.out.println(String.format("Communications Tower at %d %d %d failed to open channel %d on modem", xCoord, yCoord, zCoord, (int)channel));
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isChannelOpen(double channel) {
        if (isValidController() && hasModemsConnected()) {
            for (IPeripheral modem : modems) {
                try {
                    return (Boolean) modem.callMethod(null, null, 1, new Object[]{channel})[0];
                } catch (Exception e) {
                    System.out.println(String.format("Communications Tower at %d %d %d failed to check if channel %d was open on modem", xCoord, yCoord, zCoord, (int)channel));
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public void closeAllChannels() {
        // close all the channels
        if (isValidController() && hasModemsConnected()) {
            for (IPeripheral modem : modems) {
                try {
                    modem.callMethod(null, null, 3, new Object[0]);
                } catch (Exception e) {
                    System.out.println(String.format("Communications Tower at %d %d %d failed to close all channels on modem", xCoord, yCoord, zCoord));
                    e.printStackTrace();
                }
            }
        }
        // re-open the rednet broadcast channel
        openModems();
    }

    public void openModems() {
        if (isValidController() && hasModemsConnected() && !isChannelOpen(ComputerCraftInfo.REDNET_BROADCAST)) {
            openChannel(ComputerCraftInfo.REDNET_BROADCAST);
        }
    }

    public void closeModems() {
        if (isValidController() && hasModemsConnected()) {
            closeAllChannels();
        }
    }

    public void structureComplete() {
        modemSearch();
        if (isValidController() && hasModemsConnected()) {
            attachModem();
            openModems();
        }
    }

    public void structureDestroyed() {
        closeModems();
        detachModem();
    }

    private boolean hasModemsConnected() {
        return modems != null && modems.length == 4;
    }

    private void modemSearch() {
        ArrayList<IPeripheral> found = Lists.newArrayList();
        checkModem(found, 1, 0);
        checkModem(found, -1, 0);
        checkModem(found, 0, 1);
        checkModem(found, 0, -1);
        modems = found.toArray(new IPeripheral[found.size()]);
    }

    private void checkModem(ArrayList<IPeripheral> list, int xOffset, int zOffset) {
        int x = xCoord + xOffset;
        int z = zCoord + zOffset;
        if (worldObj.getBlockId(x, yCoord, z) == ComputerCraftInfo.cc_wirelessModem.getItem().itemID) {
            IPeripheral p = PeripheralUtils.getIPeripheral(worldObj.getBlockTileEntity(x, yCoord, z));
            if (p != null) {
                list.add(p);
            }
        }
    }

}
