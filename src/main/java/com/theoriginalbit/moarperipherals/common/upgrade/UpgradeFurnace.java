/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.upgrade;

import com.theoriginalbit.framework.peripheral.wrapper.WrapperComputer;
import com.theoriginalbit.moarperipherals.client.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.framework.peripheral.turtle.UpgradePeripheral;
import com.theoriginalbit.moarperipherals.common.upgrade.peripheral.PeripheralFurnace;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * @author theoriginalbit
 * @since 27/10/14
 */
public class UpgradeFurnace extends UpgradePeripheral implements IUpgradeToolIcon {
    private IIcon icon;

    public UpgradeFurnace() {
        super(ConfigData.upgradeIdFurnace, Constants.UPGRADE.FURNACE.getLocalised(), new ItemStack(Blocks.furnace));
    }

    @Override
    protected WrapperComputer getPeripheralWrapper(ITurtleAccess access, TurtleSide side) {
        return new WrapperComputer(new PeripheralFurnace(access, side));
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, WrapperComputer peripheral) {
        // NO-OP
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon("furnace_front_off");
    }
}
