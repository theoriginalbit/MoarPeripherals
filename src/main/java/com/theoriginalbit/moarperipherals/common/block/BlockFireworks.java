/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block;

import com.theoriginalbit.moarperipherals.common.block.abstracts.BlockMoarP;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.TileFireworks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class BlockFireworks extends BlockMoarP {

    public BlockFireworks() {
        super("fireworks");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        icons[0] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":fireworksBottom");
        icons[1] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":fireworks");
        for (int i = 2; i < 6; ++i) {
            icons[i] = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":fireworksSide");
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileFireworks();
    }

}
