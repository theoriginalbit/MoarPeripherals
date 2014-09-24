/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.block.base;

import com.theoriginalbit.moarperipherals.utils.BlockNotifyFlags;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockRotatable extends BlockMoarP {

    @SideOnly(Side.CLIENT)
    protected IIcon faceIcon;
    protected RotationMode rotationMode;

    public BlockRotatable(Material material, String blockName) {
        super(material, blockName);
    }

    public BlockRotatable setRotationMode(RotationMode mode) {
        rotationMode = mode;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == meta && faceIcon != null ? faceIcon : blockIcon;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        list.add(new ItemStack(this, 1, 3));
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return 3;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int meta = 0;
        switch (rotationMode) {
            case FOUR:
                int face = MathHelper.floor_double((double) (entity.rotationYaw * 4.0f / 360.0f) + 0.5d) & 3;
                meta = face == 0 ? 2 : (face == 1 ? 5 : (face == 2 ? 3 : (face == 3 ? 4 : 0)));
                break;
            case SIX:
                meta = BlockPistonBase.determineOrientation(world, x, y, z, entity);
                break;
        }
        world.setBlockMetadataWithNotify(x, y, z, meta, BlockNotifyFlags.SEND_TO_CLIENTS);
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public enum RotationMode {
        FOUR, SIX
    }

}
