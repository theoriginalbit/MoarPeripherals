package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.antenna.TileAntennaController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
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
public class BlockAntennaController extends BlockAntenna {

    public BlockAntennaController() {
        super(Settings.blockIdAntennaController, "antennaController");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        super.registerIcons(registry);
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":antenna");
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":antennaCable");
    }

    @Override
    public int getRenderType() {
        return Constants.RENDER_ID.ANTENNA_CTRLR;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileAntennaController();
    }

    @Override
    public final boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return side == ForgeDirection.DOWN;
    }

}
