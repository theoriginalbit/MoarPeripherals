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

import com.theoriginalbit.moarperipherals.api.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.upgrade.abstracts.UpgradePeripheral;
import com.theoriginalbit.moarperipherals.common.upgrade.peripheral.PeripheralCompass;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
public class UpgradeCompass extends UpgradePeripheral implements IUpgradeToolIcon {
    private IIcon icon;

    public UpgradeCompass() {
        super(ConfigHandler.upgradeIdCompass, Constants.UPGRADE.COMPASS, new ItemStack(Items.compass), null);
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return icon;
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new PeripheralWrapper(new PeripheralCompass(turtle));
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, IPeripheral peripheral) {
        // NO-OP
    }

    @Override
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(ModInfo.RESOURCE_DOMAIN + ":upgradeCompass");
    }

}
