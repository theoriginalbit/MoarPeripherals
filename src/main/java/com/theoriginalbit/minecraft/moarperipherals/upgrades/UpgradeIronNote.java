package com.theoriginalbit.minecraft.moarperipherals.upgrades;

import com.theoriginalbit.minecraft.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.registry.BlockRegistry;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;

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
public class UpgradeIronNote implements ITurtleUpgrade {

    @Override
    public int getUpgradeID() {
        return Settings.upgradeIdIronNote;
    }

    @Override
    public String getAdjective() {
        return Constants.UPGRADE.IRONNOTE.getLocalised();
    }

    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Peripheral;
    }

    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(BlockRegistry.blockIronNote);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        TileIronNote tile = new TileIronNote();
        PeripheralWrapper wrapper = new PeripheralWrapper(tile);
        updateLocation(turtle, wrapper);
        return wrapper;
    }

    @Override
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        return TurtleCommandResult.failure();
    }

    @Override
    public Icon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return BlockRegistry.blockIronNote.getIcon(0, 0);
    }

    @Override
    public void update(ITurtleAccess turtle, TurtleSide side) {
        updateLocation(turtle, turtle.getPeripheral(side));
    }

    private void updateLocation(ITurtleAccess turtle, IPeripheral peripheral) {
        if (peripheral instanceof PeripheralWrapper) {
            Object instance = ((PeripheralWrapper) peripheral).getInstance();
            if (instance instanceof TileEntity) {
                TileEntity tile = (TileEntity) instance;
                ChunkCoordinates coords = turtle.getPosition();
                tile.worldObj = turtle.getWorld();
                tile.xCoord = coords.posX;
                tile.yCoord = coords.posY;
                tile.zCoord = coords.posZ;
                tile.updateEntity();
            }
        }
    }

}
