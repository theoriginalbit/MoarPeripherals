/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrades;

import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
public class UpgradeCompass implements ITurtleUpgrade, IUpgradeToolIcon {
    private final ItemStack stack = new ItemStack(Items.compass);
    private IIcon icon;

    @Override
    public int getUpgradeID() {
        return ConfigHandler.upgradeIdCompass;
    }

    @Override
    public String getUnlocalisedAdjective() {
        return Constants.UPGRADE.COMPASS.getLocalised();
    }

    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Peripheral;
    }

    @Override
    public ItemStack getCraftingItem() {
        return stack;
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new PeripheralCompass(turtle);
    }

    @Override
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        return TurtleCommandResult.failure();
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    public void update(ITurtleAccess turtle, TurtleSide side) {
        // NO-OP
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_DOMAIN + ":compass");
    }

    private class PeripheralCompass implements IPeripheral {
        private final ITurtleAccess turtle;

        public PeripheralCompass(ITurtleAccess access) {
            turtle = access;
        }

        @Override
        public String getType() {
            return "compass";
        }

        @Override
        public String[] getMethodNames() {
            return new String[]{"getFacing"};
        }

        @Override
        public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
            return method == 0 ? new Object[]{ForgeDirection.getOrientation(turtle.getDirection()).name().toLowerCase()} : new Object[0];
        }

        @Override
        public void attach(IComputerAccess computer) {
        }

        @Override
        public void detach(IComputerAccess computer) {
        }

        @Override
        public boolean equals(IPeripheral other) {
            return true;
        }
    }
}
