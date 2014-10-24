/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.upgrade.abstracts;

import com.theoriginalbit.moarperipherals.common.reference.Constants.LocalisationStore;
import com.theoriginalbit.moarperipherals.common.utils.WorldUtils;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public abstract class UpgradeTool implements ITurtleUpgrade {
    private final int id;
    private final String adjective;
    private final ItemStack stack;

    protected UpgradeTool(int upgradeId, LocalisationStore localisation, ItemStack itemStack) {
        id = upgradeId;
        adjective = localisation.getLocalised();
        stack = itemStack;
    }

    @Override
    public int getUpgradeID() {
        return id;
    }

    @Override
    public String getUnlocalisedAdjective() {
        return adjective;
    }

    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.Tool;
    }

    @Override
    public ItemStack getCraftingItem() {
        return stack;
    }

    @Override
    public IIcon getIcon(ITurtleAccess turtle, TurtleSide side) {
        return stack.getIconIndex();
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return null;
    }

    @Override
    public void update(ITurtleAccess turtle, TurtleSide side) {
        // NO-OP
    }

    @Override
    public TurtleCommandResult useTool(ITurtleAccess turtle, TurtleSide side, TurtleVerb verb, int direction) {
        switch (verb) {
            case Attack:
                return attack(turtle, direction);
            case Dig:
                return dig(turtle, direction);
        }
        return TurtleCommandResult.failure("Unsupported action");
    }

    protected boolean canBreakBlock(World world, int x, int y, int z) {
        final Block block = world.getBlock(x, y, z);
        return !((block.isAir(world, x, y, z)) || (block == Blocks.bedrock) || (block.getBlockHardness(world, x, y, z) <= -1.0F));
    }

    protected abstract boolean canHarvestBlock(World world, int x, int y, int z);

    protected float getDamageMultiplier() {
        return 3f;
    }

    private TurtleCommandResult attack(final ITurtleAccess turtle, int direction) {
        final World world = turtle.getWorld();
        final ChunkCoordinates position = turtle.getPosition();
//        FakePlayer turtlePlayer = new FakePlayer(); //TurtlePlaceCommand.createPlayer(world, position, turtle, direction);

//        Vec3 turtlePos = Vec3.func_72443_a(turtlePlayer.field_70165_t, turtlePlayer.field_70163_u, turtlePlayer.field_70161_v);
//        Vec3 rayDir = turtlePlayer.func_70676_i(1.0F);
//        Vec3 rayStart = turtlePos.func_72441_c(rayDir.field_72450_a * 0.4D, rayDir.field_72448_b * 0.4D, rayDir.field_72449_c * 0.4D);
//        Entity hitEntity = WorldUtils.rayTraceEntities(world, rayStart, rayDir, 1.1D);
//        if (hitEntity != null) {
//            ItemStack stackCopy = this.m_item.func_77946_l();
//            turtlePlayer.loadInventory(stackCopy);
//
//            ComputerCraft.setEntityDropConsumer(hitEntity, new IEntityDropConsumer() {
//                public void consumeDrop(Entity entity, ItemStack drop) {
//                    ItemStack remainder = InventoryUtil.storeItems(drop, turtle.getInventory(), 0, turtle.getInventory().func_70302_i_(), turtle.getSelectedSlot());
//                    if (remainder != null) {
//                        WorldUtil.dropItemStack(remainder, world, position.posX, position.posY, position.posZ, net.minecraft.util.Facing.field_71588_a[turtle.getDirection()]);
//                    }
//                }
//            });
//            boolean placed = false;
//            if ((hitEntity.func_70075_an()) && (!hitEntity.func_85031_j(turtlePlayer))) {
//                float damage = (float) turtlePlayer.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
//                damage *= getDamageMultiplier();
//                if (damage > 0.0F) {
//                    if (hitEntity.func_70097_a(DamageSource.func_76365_a(turtlePlayer), damage)) {
//                        placed = true;
//                    }
//                }
//
//            }
//
//            ComputerCraft.clearEntityDropConsumer(hitEntity);
//
//            if (placed) {
//                turtlePlayer.unloadInventory(turtle);
//                return TurtleCommandResult.success();
//            }
//        }

        return TurtleCommandResult.failure("Nothing to attack here");
    }

    private TurtleCommandResult dig(ITurtleAccess turtle, int direction) {
        World world = turtle.getWorld();
        ChunkCoordinates position = turtle.getPosition();
        ChunkCoordinates newPosition = WorldUtils.moveCoords(position, direction);

//        if ((WorldUtils.isBlockInWorld(world, newPosition)) && (!world.isAirBlock(newPosition.posX, newPosition.posY, newPosition.posZ)) && (!WorldUtils.isLiquidBlock(world, newPosition))) {
//            if (!canBreakBlock(world, newPosition.posX, newPosition.posY, newPosition.posZ)) {
//                return TurtleCommandResult.failure("Unbreakable block detected");
//            }
//
//            if (canHarvestBlock(world, newPosition.posX, newPosition.posY, newPosition.posZ)) {
//                ArrayList items = getBlockDropped(world, newPosition.posX, newPosition.posY, newPosition.posZ);
//                if ((items != null) && (items.size() > 0)) {
//                    for (Object item : items) {
//                        ItemStack stack = (ItemStack) item;
//                        ItemStack remainder = InventoryUtils.storeItems(stack, turtle.getInventory(), 0, turtle.getInventory().getSizeInventory(), turtle.getSelectedSlot());
//                        if (remainder != null) {
//                            WorldUtils.dropItemStack(remainder, world, position.posX, position.posY, position.posZ, direction);
//                        }
//                    }
//                }
//
//            }
//
//            Block block = world.func_147439_a(newPosition.posX, newPosition.posY, newPosition.posZ);
//            if (block != null) {
//                world.func_72908_a(newPosition.posX + 0.5D, newPosition.posY + 0.5D, newPosition.posZ + 0.5D, block.field_149762_H.func_150495_a(), (block.field_149762_H.func_150497_c() + 1.0F) / 2.0F, block.field_149762_H.func_150494_d() * 0.8F);
//            }
//            world.func_147468_f(newPosition.posX, newPosition.posY, newPosition.posZ);
//            return TurtleCommandResult.success();
//        }

        return TurtleCommandResult.failure("Nothing to dig here");
    }

    private ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z) {
//        Block block = world.func_147439_a(x, y, z);
//        int metadata = world.func_72805_g(x, y, z);
//        return block.getDrops(world, x, y, z, metadata, 0);
        return null;
    }
}