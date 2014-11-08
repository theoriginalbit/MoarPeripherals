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
package com.theoriginalbit.moarperipherals.common.upgrade.abstracts;

import com.theoriginalbit.moarperipherals.api.peripheral.wrapper.PeripheralWrapper;
import com.theoriginalbit.moarperipherals.common.reference.Constants.LocalisationStore;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public abstract class UpgradePeripheral implements ITurtleUpgrade {
    private final int id;
    private final String adjective;
    private final ItemStack stack;
    private final Class<? extends TileEntity> tile;

    public UpgradePeripheral(int upgradeId, LocalisationStore localisation, ItemStack itemStack, Class<? extends TileEntity> tileentity) {
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
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        if (tile == null) {
            return null;
        }
        try {
            final TileEntity te = tile.newInstance();
            final PeripheralWrapper wrapper = new PeripheralWrapper(te);
            update(turtle, side, wrapper);
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
    public void update(ITurtleAccess turtle, TurtleSide side) {
        update(turtle, side, turtle.getPeripheral(side));
    }

    protected abstract void update(ITurtleAccess turtle, TurtleSide side, IPeripheral peripheral);

}
