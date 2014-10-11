/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockAntennaController extends BlockAntenna {

    public BlockAntennaController() {
        super("antennaController");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        super.registerBlockIcons(registry);
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":antenna");
    }

    @Override
    public int getRenderType() {
        return Constants.RENDER_ID.ANTENNA_CTRLR;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileAntennaController();
    }

}
