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
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.MultiReturn;
import com.theoriginalbit.moarperipherals.common.mount.MountMoarP;
import net.minecraft.item.ItemStack;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("firework_launcher")
@Computers.Mount(MountMoarP.class)
public class TileFireworksCreative extends TileFireworks {

    public TileFireworksCreative() {
        super(0);
    }

    /*
     * We don't want the buffers returned on the creative launcher, the player shouldn't get free resources
     */
    @Override
    public void blockBroken(int x, int y, int z) {
        // NO-OP
    }

    /*
     * We have no inventory
     */
    @Override
    @LuaFunction("getStackInSlot")
    public ItemStack stackInSlot(int slot) {
        return null;
    }

    @Override
    @LuaFunction
    public boolean isCreativeLauncher() {
        return true;
    }

    @LuaFunction
    @MultiReturn
    public Object[] load(int slot) {
        return new Object[]{false, "Cannot invoke load on creative launcher"};
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @Override
    @LuaFunction
    @MultiReturn
    public Object[] unloadFireworkRocket(int id) {
        if (!bufferRocket.containsItemStackWithId(id)) {
            return new Object[]{"No Firework Rocket with that ID found"};
        }
        bufferRocket.getNextItemStack();
        return new Object[]{true};
    }

    /*
     * This is creative, don't drop the items, just destroy them
     */
    @Override
    @LuaFunction
    @MultiReturn
    public Object[] unloadFireworkStar(int id) {
        if (!bufferStar.containsItemStackWithId(id)) {
            return new Object[]{"No Firework Star with that ID found"};
        }
        bufferStar.getNextItemStack();
        return new Object[]{true};
    }

}
