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
package com.moarperipherals.integration.buildcraft;

import buildcraft.api.transport.IInjectable;
import com.moarperipherals.api.sorter.IInteractiveSorterOutput;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.integration.IModule;
import com.moarperipherals.integration.Mods;
import com.moarperipherals.registry.InteractiveSorterRegistry;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ModuleBuildcraft implements IModule {
    @Override
    public boolean shouldLoad() {
        return Loader.isModLoaded(Mods.BUILDCRAFT_TRANSPORT);
    }

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
        if (ConfigData.enableInteractiveSorter) {
            InteractiveSorterRegistry.INSTANCE.register(new IInteractiveSorterOutput() {
                @Override
                public int output(ItemStack stack, TileEntity tile, ForgeDirection direction) {
                    if (tile instanceof IInjectable) {
                        return ((IInjectable) tile).injectItem(stack, true, direction, null);
                    }
                    return 0;
                }
            });
        }
    }
}
