/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockAntennaCell extends BlockAntenna {

    public BlockAntennaCell() {
        super("antennaCell");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        super.registerBlockIcons(registry);
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":antenna");
    }

}
