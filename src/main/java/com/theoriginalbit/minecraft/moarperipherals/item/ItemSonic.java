package com.theoriginalbit.minecraft.moarperipherals.item;

import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.BlockNotifyFlags;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemSonic extends ItemMPBase {

    public ItemSonic() {
        super(Settings.itemIdSonic, "sonic");
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        int blockId = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        if (checkIsWooden(blockId)) {
            String message = EnumChatFormatting.RED + Constants.CHAT.SONIC_WOOD.getLocalised();
            player.sendChatToPlayer(new ChatMessageComponent().addText(message));
        } else if (isVanillaSixRotation(blockId) || isRotatablePiston(blockId, meta)) {
            return rotateSix(stack, player, world, x, y, z, side);
        } else if (isVanillaFourRotation(blockId) || isMoarPeripheralFourRotation(blockId)) {
            return rotateFour(stack, player, world, x, y, z, side);
        } else if (Block.blocksList[blockId] instanceof BlockStairs) {
            rotateStairs(stack, player, world, x, y, z, side);
        }

        return false;
    }

    private boolean rotateSix(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        return rotate(stack, player, world, x, y, z, side);
    }

    private boolean rotateFour(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        return !(side == 0 || side == 1) && rotate(stack, player, world, x, y, z, side);
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

    private boolean rotate(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        ForgeDirection face = ForgeDirection.getOrientation(side);
        if (player.isSneaking()) {
            face = face.getOpposite();
        }
        world.setBlockMetadataWithNotify(x, y, z, face.ordinal(), BlockNotifyFlags.ALL);
        stack.damageItem(1, player);
        return true;
    }

    private static boolean isVanillaFourRotation(int blockId) {
        return blockId == Block.furnaceIdle.blockID || blockId == Block.furnaceBurning.blockID;
    }

    private static boolean isVanillaSixRotation(int blockId) {
        return blockId == Block.dropper.blockID || blockId == Block.dispenser.blockID;
    }

    private static boolean isRotatablePiston(int blockId, int meta) {
        return blockId == Block.pistonBase.blockID && (meta & 8) == 0;
    }

    private static boolean isMoarPeripheralFourRotation(int blockId) {
        return blockId == Settings.blockIdKeyboard;
    }

    private static boolean checkIsWooden(int blockId) {
        Material material = Block.blocksList[blockId].blockMaterial;
        return material == Material.wood;
    }

}
