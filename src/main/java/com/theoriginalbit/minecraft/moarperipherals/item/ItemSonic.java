package com.theoriginalbit.minecraft.moarperipherals.item;

import buildcraft.api.tools.IToolWrench;
import com.google.common.collect.ImmutableSet;
import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.BlockNotifyFlags;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemSonic extends ItemMPBase implements IToolWrench {

    private static final ImmutableSet<Class<? extends Block>> blacklist = ImmutableSet.of(BlockLever.class, BlockButton.class, BlockBed.class);

    public ItemSonic() {
        super(Settings.itemIdSonic, "sonic");
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int blockId = world.getBlockId(x, y, z);
        Block block = Block.blocksList[blockId];

        if (block == null) {
            return false;
        }

        // make sure it's not wood, the sonic doesn't work on wood
        if (Block.blocksList[blockId].blockMaterial == Material.wood) {
            String message = EnumChatFormatting.RED + Constants.CHAT.SONIC_WOOD.getLocalised();
            player.sendChatToPlayer(new ChatMessageComponent().addText(message));
            return true;
        }

        // make sure it's not blacklisted
        if (blacklist.contains(block.getClass())) {
            return false;
        }

        // rotate furnace
        if (block instanceof BlockFurnace && !(side == 0 || side == 1)) {
            if (player.isSneaking()) {
                side = ForgeDirection.getOrientation(side).getOpposite().ordinal();
            }
            world.setBlockMetadataWithNotify(x, y, z, side, BlockNotifyFlags.SEND_TO_CLIENTS);
            player.swingItem();
            return !world.isRemote;
        }

        // rotate stairs, if I left this to vanilla it rotates weird
        if (block instanceof BlockStairs) {
            return rotateStairs(stack, player, world, x, y, z, side);
        }

        // attempt to rotate anything else that supports rotations that I don't need to customise
        return rotate(block, player, world, x, y, z, side);
    }

    public boolean rotate(Block block, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
            player.swingItem();
            return !world.isRemote;
        }
        return false;
    }

    private boolean rotateStairs(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        // get the current meta, trimming extra bits if there's any
        int meta = world.getBlockMetadata(x, y, z) & 4;
        // toggle where the 'base' is, top or bottom
        if (player.isSneaking()) {
            meta ^= 4;
        }
        // rotation
        switch (side) {
            case 2:
                meta |= 2;
                break;
            case 3:
                meta |= 3;
                break;
            case 4:
                /* no meta change */
                break;
            case 5:
                meta |= 1;
                break;
        }
        // update new meta
        world.setBlockMetadataWithNotify(x, y, z, meta, BlockNotifyFlags.SEND_TO_CLIENTS);
        stack.damageItem(1, player);
        return true;
    }

    /*
     * BC wrench compatibility
     */

    @Override
    public boolean canWrench(EntityPlayer player, int x, int y, int z) {
        return true;
    }

    @Override
    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {}

}
