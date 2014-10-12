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
import com.theoriginalbit.moarperipherals.common.tile.TileChatBoxAdmin;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 12/10/2014
 */
public class BlockChatBoxAdmin extends BlockMoarP {

    public BlockChatBoxAdmin() {
        super(Material.iron, "chatboxAdmin");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par1) {
        return new TileChatBoxAdmin();
    }

}
