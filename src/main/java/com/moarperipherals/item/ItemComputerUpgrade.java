/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.item;

import com.moarperipherals.util.BlockNotifyFlags;
import com.moarperipherals.util.PairedUtil;
import com.moarperipherals.util.WorldUtil;
import dan200.computercraft.shared.computer.blocks.TileComputerBase;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ItemComputerUpgrade extends ItemMoarP {
    public ItemComputerUpgrade() {
        super("computerUpgrade");
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
                                  float hitX, float hitY, float hitZ) {
        if (player.isSneaking() || WorldUtil.isClient(world)) return false;

        // make sure the TileEntity is something that can be upgraded
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && PairedUtil.isComputer(tile)) {
            // we only upgrade the normal family
            final TileComputerBase computer = (TileComputerBase) tile;
            if (computer.getFamily() == ComputerFamily.Normal) {
                // for now there is no Turtle upgrading
                if (!(computer instanceof TileTurtle)) {
                    // update the block metadata to be advanced
                    int meta = world.getBlockMetadata(x, y, z) + 8;
                    world.setBlockMetadataWithNotify(x, y, z, meta, BlockNotifyFlags.ALL);
                    // copy the data because for some reason without this the tile doesn't update
                    try {
                        Class<?> clazz = TileComputerBase.class;
                        Method transfer = clazz.getDeclaredMethod("transferStateFrom", clazz);
                        transfer.setAccessible(true);
                        transfer.invoke(computer, computer);
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                }

                return true;
            }
        }

        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}
