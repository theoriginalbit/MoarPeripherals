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
package com.moarperipherals.block;

import com.moarperipherals.ModInfo;
import com.moarperipherals.config.ConfigData;
import com.moarperipherals.tile.TilePrinter;
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
        icons[3] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontInput");
        icons[4] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontOutput");
        icons[5] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontBoth");
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        // get the top/bottom icon
        if (side == 0 || side == 1) {
            return icons[0];
        }
        // get the side icon
        if (side != world.getBlockMetadata(x, y, z)) {
            return icons[1];
        }
        // get the face icon
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TilePrinter) {
            return icons[2 + ((TilePrinter) tile).getAnim()];
        }
        return icons[2];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) {
            return icons[0];
        }
        if (side == 4) {
            return icons[2];
        }
        return icons[1];
    }

    @Override
    public int getRenderType() {
        return ConfigData.enablePrinterGfx ? -1 : 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return !ConfigData.enablePrinterGfx;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !ConfigData.enablePrinterGfx;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return true;
    }

}