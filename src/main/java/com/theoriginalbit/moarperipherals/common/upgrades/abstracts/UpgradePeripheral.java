/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrades.abstracts;

import com.theoriginalbit.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.moarperipherals.common.reference.Constants.LocalisationStore;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public abstract class UpgradePeripheral implements ITurtleUpgrade {
    private final int id;
    private final String adjective;
    private final ItemStack stack;
    private final Class<? extends TileEntity> tile;

    protected UpgradePeripheral(int upgradeId, LocalisationStore localisation, ItemStack itemStack, Class<? extends TileEntity> tileentity) {
        id = upgradeId;
        adjective = localisation.getLocalised();
        stack = itemStack;
        tile = tileentity;
    }

    @Override
    public final int getUpgradeID() {
        return id;
    }

    @Override
    public final String getUnlocalisedAdjective() {
        return adjective;
    }

    @Override
    public final TurtleUpgradeType getType() {
        return TurtleUpgradeType.Peripheral;
    }

    @Override
    public final ItemStack getCraftingItem() {
        return stack;
    }

    @Override
    public final IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        try {
            final TileEntity te = tile.newInstance();
            final PeripheralWrapper wrapper = new PeripheralWrapper(te);
            updateLocation(turtle, wrapper);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public final TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        return TurtleCommandResult.failure();
    }

    @Override
    public final IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return stack.getIconIndex();
    }

    @Override
    public final void update(ITurtleAccess turtle, TurtleSide side) {
        updateLocation(turtle, turtle.getPeripheral(side));
    }

    protected abstract void updateLocation(ITurtleAccess turtle, IPeripheral peripheral);

}
