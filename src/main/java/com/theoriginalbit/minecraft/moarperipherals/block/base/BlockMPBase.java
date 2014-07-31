package com.theoriginalbit.minecraft.moarperipherals.block.base;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.tile.IHasGui;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.tile.IHasSpecialDrops;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.tile.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileMPBase;
import com.theoriginalbit.minecraft.moarperipherals.utils.InventoryUtils;
import com.theoriginalbit.minecraft.moarperipherals.utils.NEIUtils;
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
public abstract class BlockMPBase extends BlockContainer {

    protected final String blockName;
    protected final Icon[] icons = new Icon[6];

    public BlockMPBase(int id, String name) {
        this(id, Material.rock, name);
    }

    public BlockMPBase(int id, Material material, String name) {
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

        if (entity instanceof EntityPlayer) {
            ((TileMPBase) tile).setOwner(((EntityPlayer) entity).username);
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

        if ((((TileMPBase) tile).canPlayerAccess(player) || isOpBreakable(player))) {
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
    public final BlockMPBase hideFromNEI() {
        NEIUtils.hideFromNEI(blockID);
        return this;
    }

    /**
     * Removes the block from the creative menu, by default it is added to the MoarPeripherals creative tab.
     */
    public final BlockMPBase hideFromCreative() {
        setCreativeTab(null);
        return this;
    }

    private static boolean isOpBreakable(EntityPlayer player) {
        return Settings.securityOpBreak && MoarPeripherals.proxy.isOp(player);
    }

}
