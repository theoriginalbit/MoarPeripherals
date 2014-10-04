/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.block.abstracts;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.IHasGui;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.IHasSpecialDrops;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.InventoryUtils;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.NEIUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockMoarP extends BlockContainer {

    protected final String blockName;
    protected final Icon[] icons = new Icon[6];

    public BlockMoarP(int id, String name) {
        this(id, Material.rock, name);
    }

    public BlockMoarP(int id, Material material, String name) {
        super(id, material);
        blockName = name;
        setHardness(0.5f);
        setResistance(10.0f);
        setCreativeTab(MoarPeripherals.creativeTab);
        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + '.' + blockName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        Icon icon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + blockName);
        for (int i = 0; i < icons.length; ++i) {
            icons[i] = icon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return icons[side];
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile instanceof IPlaceAwareTile) {
            ((IPlaceAwareTile) tile).onPlaced(entity, stack, x, y, z);
        }

        if (tile != null && entity instanceof EntityPlayer) {
            ((TileMoarP) tile).setOwner(((EntityPlayer) entity).username);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile instanceof IHasGui) {
            player.openGui(MoarPeripherals.instance, ((IHasGui) tile).getGuiId().ordinal(), world, x, y, z);
            return true;
        }

        return tile instanceof IActivateAwareTile && ((IActivateAwareTile) tile).onActivated(player, side, hitX, hitY, hitZ);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile instanceof IBreakAwareTile) {
            ((IBreakAwareTile) tile).onBreak(x, y, z);
        }

        if (tile instanceof IInventory) {
            InventoryUtils.explodeInventory((IInventory) tile, world, x, y, z);
        }

        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        final List<ItemStack> tileDrops = Lists.newArrayList();

        if (tile instanceof IPairableDevice) {
            ItemStack drop = ((IPairableDevice) tile).getPairedDrop();
            tileDrops.add(drop);
        }
        if (tile instanceof IHasSpecialDrops) {
            ((IHasSpecialDrops) tile).addDrops(tileDrops);
        }

        for (ItemStack stack : tileDrops) {
            dropBlockAsItem_do(world, x, y, z, stack);
        }

        return super.removeBlockByPlayer(world, player, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile instanceof INeighborAwareTile) {
            ((INeighborAwareTile) tile).onNeighbourChanged(blockId);
        }

        super.onNeighborBlockChange(world, x, y, z, blockId);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile == null || (((TileMoarP) tile).canPlayerAccess(player) || isOpBreakable(player))) {
            return blockHardness;
        }

        return -1.0f;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType creature, World world, int x, int y, int z) {
        return false;
    }

    /**
     * Stops the block from appearing in Not Enough Items
     */
    @SuppressWarnings("unused")
    public final BlockMoarP hideFromNEI() {
        NEIUtils.hideFromNEI(blockID);
        return this;
    }

    /**
     * Removes the block from the creative menu, by default it is added to the MoarPeripherals creative tab.
     */
    @SuppressWarnings("unused")
    public final BlockMoarP hideFromCreative() {
        setCreativeTab(null);
        return this;
    }

    private static boolean isOpBreakable(EntityPlayer player) {
        return ConfigHandler.securityOpBreak && MoarPeripherals.proxy.isOp(player);
    }

}
