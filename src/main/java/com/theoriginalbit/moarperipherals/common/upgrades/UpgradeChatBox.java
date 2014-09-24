/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrades;

import com.theoriginalbit.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.moarperipherals.reference.Constants;
import com.theoriginalbit.moarperipherals.reference.Settings;
import com.theoriginalbit.moarperipherals.common.registry.BlockRegistry;
import com.theoriginalbit.moarperipherals.common.tile.TileChatBox;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;

public class UpgradeChatBox implements ITurtleUpgrade {

    @Override
    public int getUpgradeID() {
        return Settings.upgradeIdChatBox;
    }

    @Override
    public String getUnlocalisedAdjective() {
        return Constants.UPGRADE.CHATBOX.getLocalised();
    }

    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Peripheral;
    }

    @Override
    public ItemStack getCraftingItem() {
        return new ItemStack(BlockRegistry.blockChatBox);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        TileChatBox tile = new TileChatBox();
        PeripheralWrapper wrapper = new PeripheralWrapper(tile);
        updateLocation(turtle, wrapper);
        return wrapper;
    }

    @Override
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        return TurtleCommandResult.failure();
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return BlockRegistry.blockChatBox.getIcon(0, 0);
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
                tile.setWorldObj(turtle.getWorld());
                tile.xCoord = coords.posX;
                tile.yCoord = coords.posY;
                tile.zCoord = coords.posZ;
                tile.updateEntity();
            }
        }
    }

}
