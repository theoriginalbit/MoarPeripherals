/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.ComputerList;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts.TileMoarP;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.player.EntityPlayer;
import openperipheral.api.Ignore;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

@Ignore
@LuaPeripheral("player_detector")
public class TilePlayerDetector extends TileMoarP implements IActivateAwareTile {

    private static final String EVENT_PLAYER = "player";

    @ComputerList
    public ArrayList<IComputerAccess> computers;

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }
        computerQueueEvent(EVENT_PLAYER, player.username);
        return true;
    }

    protected void computerQueueEvent(String event, Object... args) {
        if (computers == null || computers.isEmpty()) {
            return;
        }
        for (IComputerAccess computer : computers) {
            computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
        }
    }

}