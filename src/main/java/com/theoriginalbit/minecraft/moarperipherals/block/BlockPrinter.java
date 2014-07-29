package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.block.base.BlockRotatable;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePrinter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class BlockPrinter extends BlockRotatable {

    private static final Icon[] icons = new Icon[6];

    public BlockPrinter() {
        super(Settings.blockIdPrinter, Material.iron, "printer");
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
    public final boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

}