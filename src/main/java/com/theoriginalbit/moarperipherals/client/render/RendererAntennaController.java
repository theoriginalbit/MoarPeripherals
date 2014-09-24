/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.render;

import com.theoriginalbit.moarperipherals.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.TileAntennaController;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RendererAntennaController extends RendererAntenna {

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        TileAntennaController tile = (TileAntennaController) world.getTileEntity(x, y, z);
        if (!tile.isTowerComplete()) {
            renderer.renderStandardBlock(block, x, y, z);
        }
        return true;
    }

    @Override
    public int getRenderId() {
        return Constants.RENDER_ID.ANTENNA_CTRLR;
    }

}
