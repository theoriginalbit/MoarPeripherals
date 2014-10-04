/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.block;

import com.theoriginalbit.minecraft.moarperipherals.common.block.abstracts.BlockRotatable;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.TilePrinter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockPrinter extends BlockRotatable {

    private static final Icon[] icons = new Icon[6];

    public BlockPrinter() {
        super(ConfigHandler.blockIdPrinter, Material.iron, "printer");
        setStepSound(soundMetalFootstep);
        setRotationMode(RotationMode.FOUR);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TilePrinter();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterTop");
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterSide");
        icons[2] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFront");
        icons[3] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontFeeder");
        icons[4] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontOutput");
        icons[5] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":advPrinterFrontBoth");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        if (side == 0 || side == 1) {
            return icons[0];
        }
        if (side == meta) {
            return icons[2];
        }
        return icons[1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta == side && faceIcon != null) {
            return faceIcon;
        }
        return super.getBlockTexture(blockAccess, x, y, z, side);
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
    public final boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

}