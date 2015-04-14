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
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.registry.ModBlocks;
import com.theoriginalbit.moarperipherals.common.tile.TileIronNote;
import com.theoriginalbit.framework.peripheral.turtle.UpgradePeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;

public class UpgradeIronNote extends UpgradePeripheral {
    public UpgradeIronNote() {
        super(ConfigData.upgradeIdIronNote, Constants.UPGRADE.IRONNOTE.getLocalised(), new ItemStack(ModBlocks.blockIronNote));
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return ModBlocks.blockIronNote.getIcon(0, 0);
    }

    @Override
    protected WrapperComputer getPeripheralWrapper(ITurtleAccess access, TurtleSide side) {
        return new WrapperComputer(new TileIronNote());
    }

    @Override
    protected void update(ITurtleAccess turtle, TurtleSide side, WrapperComputer peripheral) {
        final Object instance = peripheral.getInstance();
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
