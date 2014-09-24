/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.block.base.BlockRotatable;
import com.theoriginalbit.moarperipherals.reference.ModInfo;
import com.theoriginalbit.moarperipherals.reference.Settings;
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
        return Settings.enablePrinterGfx ? -1 : 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return !Settings.enablePrinterGfx;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !Settings.enablePrinterGfx;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return true;
    }

}