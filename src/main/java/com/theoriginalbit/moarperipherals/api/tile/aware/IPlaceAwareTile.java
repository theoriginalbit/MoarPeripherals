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
package com.theoriginalbit.moarperipherals.api.tile.aware;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Subscribe your {@link TileEntity} to block right-clicks
 *
 * @author theoriginalbit
 */
public interface IPlaceAwareTile {
    /**
     * Invoked when the block is placed
     */
    public void onPlaced(EntityLivingBase entity, ItemStack stack, int x, int y, int z);

}