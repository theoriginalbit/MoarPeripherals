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
import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradePeripheral;
import com.theoriginalbit.moarperipherals.common.upgrade.peripheral.PeripheralCompass;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
public class UpgradeCompass extends UpgradePeripheral implements ITurtleUpgrade, IUpgradeToolIcon {
    private IIcon icon;

    public UpgradeCompass() {
        super(ConfigHandler.upgradeIdCompass, Constants.UPGRADE.COMPASS, new ItemStack(Items.compass), null);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new PeripheralWrapper(new PeripheralCompass(turtle));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    protected void update(ITurtleAccess turtle, IPeripheral peripheral) {
        // NO-OP
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_DOMAIN + ":upgradeCompass");
    }

}
