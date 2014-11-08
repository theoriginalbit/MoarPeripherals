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

import com.theoriginalbit.moarperipherals.common.block.abstracts.BlockRotatable;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TilePrinter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPrinter extends BlockRotatable {

    private static final IIcon[] icons = new IIcon[6];

    public BlockPrinter() {
        super(Material.iron, "printer");
        setRotationMode(RotationMode.FOUR);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TilePrinter();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterTop");
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterSide");
        icons[2] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFront");
        icons[3] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontFeeder");
        icons[4] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontOutput");
        icons[5] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontBoth");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) {
            return icons[0];
        }
        if (side == meta) {
            return icons[2];
        }
        return icons[1];
    }

    @Override
    public int getRenderType() {
        return ConfigHandler.enablePrinterGfx ? -1 : 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return !ConfigHandler.enablePrinterGfx;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !ConfigHandler.enablePrinterGfx;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return true;
    }

}