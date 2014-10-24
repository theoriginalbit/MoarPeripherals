/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade;

import com.theoriginalbit.framework.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradePeripheral;
import com.theoriginalbit.moarperipherals.common.upgrade.peripheral.PeripheralFurnace;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 21/10/14
 */
public class UpgradeFurnace extends UpgradePeripheral {

    public UpgradeFurnace() {
        super(ConfigHandler.upgradeIdFurnace, Constants.UPGRADE.FURNACE, new ItemStack(Blocks.furnace), null);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new PeripheralWrapper(new PeripheralFurnace(turtle));
    }

    @Override
    protected void updateLocation(ITurtleAccess turtle, IPeripheral peripheral) {
    }

}
