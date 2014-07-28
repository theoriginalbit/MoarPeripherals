package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemSonic extends ItemMPBase {

    public ItemSonic() {
        super(Settings.itemIdSonic, "sonic");
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        int blockId = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (blockId == Block.pistonBase.blockID && (meta & 8) == 0) {
            rotate(stack, player, world, x, y, z);
            return true;
        } else if (blockId == Block.furnaceIdle.blockID || blockId == Block.furnaceBurning.blockID || blockId == Block.dropper.blockID || blockId == Block.dispenser.blockID) {
            rotate(stack, player, world, x, y, z);
            return true;
        }
        return false;
    }

    private void rotate(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
        int face = BlockPistonBase.determineOrientation(world, x, y , z, player);
        if (player.isSneaking()) {
            face = ForgeDirection.getOrientation(face).getOpposite().ordinal();
        }
        world.setBlockMetadataWithNotify(x, y, z, face, 3);
        stack.damageItem(1, player);
    }

}
