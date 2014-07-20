package com.theoriginalbit.minecraft.moarperipherals.block;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IHasGui;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.IPairableDevice;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.INeighborAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IPlaceAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.tile.IHasSpecialDrops;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
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
import net.minecraft.world.World;

import java.util.List;

public abstract class BlockGeneric extends BlockContainer {

    private final String blockName;
    private String blockOwner;

    public BlockGeneric(int id, String name) {
        this(id, Material.rock, name);
    }

    public BlockGeneric(int id, Material material, String name) {
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
        blockIcon = registry.registerIcon(ModInfo.RESOURCE_DOMAIN + ":" + blockName);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        if (tile instanceof IPlaceAwareTile) {
            ((IPlaceAwareTile) tile).onPlaced(entity, stack, x, y, z);
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
    public boolean canCreatureSpawn(EnumCreatureType creature, World world, int x, int y, int z) {
        return false;
    }

    public final String getBlockOwner() {
        return blockOwner;
    }

    /**
     * Stops the block from appearing in Not Enough Items
     */
    public final BlockGeneric hideFromNEI() {
        NEIUtils.hideFromNEI(blockID);
        return this;
    }

    /**
     * Removes the block from the creative menu, by default it is added to the MoarPeripherals creative tab.
     */
    public final BlockGeneric hideFromCreative() {
        setCreativeTab(null);
        return this;
    }

}