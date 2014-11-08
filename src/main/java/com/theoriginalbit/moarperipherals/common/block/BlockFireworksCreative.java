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
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.block.abstracts.BlockMoarP;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TileFireworksCreative;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class BlockFireworksCreative extends BlockMoarP {

    public BlockFireworksCreative() {
        super("fireworksCreative");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":fireworksCreativeBottom");
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":fireworksCreative");
        for (int i = 2; i < 6; ++i) {
            icons[i] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":fireworksCreativeSide");
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileFireworksCreative();
    }

}
