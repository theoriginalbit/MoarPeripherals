package com.theoriginalbit.minecraft.moarperipherals.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.List;

public abstract class BlockRotatable extends BlockGeneric {

    @SideOnly(Side.CLIENT)
    protected Icon faceIcon;
    protected RotationMode rotationMode;

    public BlockRotatable(int id, Material material, String blockName) {
        super(id, material, blockName);
    }

    public BlockRotatable(int id, Material material, String blockName, StepSound stepSound) {
        super(id, material, blockName, stepSound, true);
    }

    public BlockRotatable(int id, Material material, String blockName, boolean inCreative) {
        super(id, material, blockName, null, inCreative);
    }

    public BlockRotatable(int id, Material material, String blockName, StepSound stepSound, boolean inCreative) {
        super(id, material, blockName, stepSound, inCreative);
    }

    public BlockRotatable setRotationMode(RotationMode mode) {
        rotationMode = mode;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return side == meta && faceIcon != null ? faceIcon : blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        if (meta == side && faceIcon != null) {
            return faceIcon;
        }
        return super.getBlockTexture(blockAccess, x, y, z, side);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void getSubBlocks(int par1, CreativeTabs tab, List list) {
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
                meta = ForgeDirection.getOrientation(face).getOpposite().ordinal();
                break;
            case SIX:
                meta = BlockPistonBase.determineOrientation(world, x, y, z, entity);
                break;
        }
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public enum RotationMode {
        FOUR, SIX;
    }

}
