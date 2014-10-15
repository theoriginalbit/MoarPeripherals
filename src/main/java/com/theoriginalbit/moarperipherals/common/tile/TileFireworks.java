/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.moarperipherals.common.container.QueueBuffer;
import com.theoriginalbit.moarperipherals.common.handler.TickHandler;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileInventory;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@LuaPeripheral("firework_launcher")
public class TileFireworks extends TileInventory implements IActivateAwareTile, IBreakAwareTile {
    private static final ImmutableList<ItemStack> ITEM_SHAPES = ImmutableList.of(
            new ItemStack(Items.fire_charge),
            new ItemStack(Items.gold_nugget),
            new ItemStack(Items.skull),
            new ItemStack(Items.feather)
    );
    private static final String[] NAME_SHAPES = new String[]{"fire charge", "gold nugget", "mob head", "feather"};
    private static final ImmutableList<Double> OFFSETS = ImmutableList.of(0.15d, 0.5d, 0.85d);
    private static final ItemStack GLOWSTONE = new ItemStack(Items.glowstone_dust);
    private static final ItemStack GUNPOWDER = new ItemStack(Items.gunpowder);
    private static final ItemStack DIAMOND = new ItemStack(Items.diamond);
    private static final ItemStack PAPER = new ItemStack(Items.paper);
    private static final int MAX_SLOTS = 9;

    // the temporary storage for crafted rockets and stars
    protected final QueueBuffer bufferRocket = new QueueBuffer("Rocket", 90);
    protected final QueueBuffer bufferStar = new QueueBuffer("Star", 7);

    // the crafting manager
    final CraftingManager manager = CraftingManager.getInstance();

    // currently crafting rocket properties
    private int rocketHeight;
    private boolean startedRocket;
    private int rocketRemainSlots;

    // a tracker for where it should launch from next
    private int nextLaunch = 0;

    public TileFireworks() {
        super(27);
    }

    @Override
    public String getInventoryName() {
        return Constants.GUI.FIREWORKS.getLocalised();
    }

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.displayGUIChest(this);
        return true;
    }

    @Override
    public void onBreak(int x, int y, int z) {
        // explode the buffers, they spent good resources to have those rockets!
        bufferRocket.explodeBuffer(worldObj, x, y, z);
        bufferStar.explodeBuffer(worldObj, x, y, z);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        // make sure to retain the buffers!
        bufferRocket.readFromNBT(tag);
        bufferStar.readFromNBT(tag);
        rocketHeight = tag.getInteger("rocketHeight");
        startedRocket = tag.getBoolean("startedRocket");
        rocketRemainSlots = tag.getInteger("rocketRemainSlots");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        // make sure to retain the buffers!
        bufferRocket.writeToNBT(tag);
        bufferStar.writeToNBT(tag);
        tag.setInteger("rocketHeight", rocketHeight);
        tag.setBoolean("startedRocket", startedRocket);
        tag.setInteger("rocketRemainSlots", rocketRemainSlots);
    }

    /**
     * The item in the slot for Firework Launcher inventory
     *
     * @param slot the slot to get the item from
     * @return the item in the slot
     */
    @LuaFunction(name = "getStackInSlot")
    public ItemStack stackInSlot(int slot) {
        // convert from Lua indexes that start at 1
        --slot;
        if (slot >= 0 || slot <= getSizeInventory()) {
            return getStackInSlot(slot);
        }
        return null;
    }

    /**
     * @return the size of the Firework Launcher inventory
     */
    @LuaFunction
    public int getInventorySize() {
        return getSizeInventory();
    }

    /**
     * Loads a pre-made Firework Rocket from the Firework Launchers inventory into the end of the
     * firework rocket launch queue. Or loads a Firework Star from the inventory into the star queue.
     *
     * @param slot the slot the rocket is in
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] load(int slot) {
        // convert from Lua indexes that start at 1
        --slot;
        // make sure the slot is in range
        if (slot < 0 || slot > getSizeInventory()) {
            return new Object[]{false, "slot out of range, should be 1-" + getSizeInventory()};
        }
        // make sure it is a firework rocket
        ItemStack stack = decrStackSize(slot, 1);
        Item item = stack.getItem();
        if (!(item instanceof ItemFirework) && !(item instanceof ItemFireworkCharge)) {
            return new Object[]{false, "item in slot is not a Firework Rocket or Firework Star"};
        }
        // load the firework rocket or firework charge
        bufferRocket.addItemStack(stack);
        return new Object[]{true};
    }

    /**
     * Starts the crafting of a <a href="http://minecraft.gamepedia.com/Firework_Rocket">Firework Rocket</a> with
     * a particular launch height
     *
     * @param height the height the rocket can travel
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] startRocket(int height) {
        // make sure that fireworks have been finished before making the next
        if (startedRocket) {
            return new Object[]{false, "finish the current firework rocket first"};
        }
        if (!bufferRocket.hasFreeSpace()) {
            return new Object[]{false, "no free space in the rocket launch buffer"};
        }
        // validate height
        if (!(height >= 1 && height <= 3)) {
            return new Object[]{false, "rocket height should be between 1 and 3"};
        }

        // setup the properties
        rocketHeight = height;
        rocketRemainSlots = MAX_SLOTS - height - 1 - bufferStar.getCurrentSize();

        startedRocket = true;
        // return success and how many slots remain in crafting
        return new Object[]{true, rocketRemainSlots};
    }

    /**
     * Adds a <a href="http://minecraft.gamepedia.com/Firework_star">Firework Star</a> to the currently crafting
     * rocket.
     *
     * @param color    the <a href="http://computercraft.info/wiki/Colors_(API)">colour(s)</a> for the firework
     * @param shape    the <a href="http://minecraft.gamepedia.com/Firework_Rocket#Effects">shape/effect</a>
     *                 of the firework
     * @param modifier the <a href="http://minecraft.gamepedia.com/Firework_Rocket#Additional_effects">modifier/additional effects</a>
     *                 of the firework
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] addFireworkStar(int color, int shape, int modifier) {
        // make there was a started firework
        if (!startedRocket) {
            return new Object[]{false, "no firework rocket started"};
        }
        if (rocketRemainSlots == 0) {
            return new Object[]{false, "the firework cannot fit any more firework stars"};
        }
        // make sure the firework colour is valid
        if (!validColor(color, true)) {
            return new Object[]{false, "invalid color colour provided"};
        }
        // make sure the shape is valid
        if (!(shape >= 0 && shape <= 4)) {
            return new Object[]{false, "shape should be 0 to 4"};
        }
        // make sure the modifier is valid
        if (!(modifier >= 0 && modifier <= 3)) {
            return new Object[]{false, "modifier should be 0 to 3"};
        }

        // starting slots is the MAX minus the gunpowder
        int remaining = MAX_SLOTS - 1;
        boolean shouldTwinkle = false, shouldTrail = false, hasShape = false;

        // make sure there is enough gunpowder
        if (findQtyOf(GUNPOWDER) == 0) {
            return new Object[]{false, "cannot create firework star, no gunpowder"};
        }

        // make sure we have the materials for the required shape
        /*
         * Shape type:
         * 0 – none, small ball
         * 1 – fire charge, large ball
         * 2 – gold nugget, star-shaped
         * 3 – head, creeper-shaped
         * 4 – feather, burst explosion
         */
        if (shape != 0) {
            --shape;
            ItemStack stack = ITEM_SHAPES.get(shape);
            if (findQtyOf(stack) == 0) {
                return new Object[]{false, "cannot create firework star, no " + NAME_SHAPES[shape]};
            }
            hasShape = true;
            --remaining;
        }

        // make sure we have the materials for the required modifier
        /*
         * Modifier / Additional effects:
         * 0 – none
         * 1 – glowstone, twinkle
         * 2 – diamond, trail effect
         * 3 – both
         */
        if (modifier != 0) {
            shouldTwinkle = modifier == 1 || modifier == 3;
            shouldTrail = modifier == 2 || modifier == 3;
            if (shouldTwinkle) {
                if (findQtyOf(GLOWSTONE) == 0) {
                    return new Object[]{false, "cannot create firework star, no glowstone"};
                }
                --remaining;
            }
            if (shouldTrail) {
                if (findQtyOf(DIAMOND) == 0) {
                    return new Object[]{false, "cannot create firework star, no diamond"};
                }
                --remaining;
            }
        }

        // make sure that we have the materials for the colours
        final ArrayList<Integer> colors = explodeColor(color);
        if (colors.size() > remaining) {
            return new Object[]{false, "too many items to craft firework star"};
        }

        // convert the colours to dyes, and make sure we have them
        final ArrayList<ItemStack> dyes = Lists.newArrayList();
        for (int i : colors) {
            ItemStack stack = colorToDye(i);
            dyes.add(stack);
            if (findQtyOf(stack) == 0) {
                return new Object[]{false, "cannot create firework star, no " + stack.getDisplayName()};
            }
        }

        /*
         * we now know that we have all the needed materials to craft this firework star, FINALLY!
         */

        // collect the materials from the inventory
        final ArrayList<ItemStack> items = Lists.newArrayList(extract(GUNPOWDER));
        // get the glowstone if the twinkle attribute is on
        if (shouldTwinkle) {
            items.add(extract(GLOWSTONE));
        }
        // get the diamond if the trail attribute is on
        if (shouldTrail) {
            items.add(extract(DIAMOND));
        }
        // get the material for the shape
        if (hasShape) {
            items.add(extract(ITEM_SHAPES.get(shape)));
        }
        // get the dyes from the inventory
        for (ItemStack dye : dyes) {
            items.add(extract(dye));
        }

        // create the crafting inventory
        final InventoryCrafting crafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return true;
            }
        }, items.size() + 1, 1);

        // copy the items into the crafting inventory
        for (int i = 0; i < items.size(); ++i) {
            crafting.setInventorySlotContents(i, items.get(i));
        }

        // craft, hopefully!
        ItemStack result = manager.findMatchingRecipe(crafting, worldObj);
        if (result == null) {
            return new Object[]{false, "we have the resources, but crafting failed"};
        }

        // add the newly crafted star to the buffer for the rocket
        bufferStar.addItemStack(result);
        return new Object[]{true, --rocketRemainSlots};
    }

    /**
     * Crafts the rocket with the added height, and firework stars. After crafting it will add the rocket to the
     * launch queue. If there are not enough ingredients to finalise the rocket, it will not craft or add the rocket
     * to the queue.
     *
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] finishRocket() {
        // make there was a started firework
        if (!startedRocket) {
            return new Object[]{false, "no firework rocket started"};
        }

        // check we have the paper to craft
        if (findQtyOf(PAPER) == 0) {
            return new Object[]{false, "cannot create firework, no paper"};
        }
        // check if we have the gunpowder to craft
        final int qty = findQtyOf(GUNPOWDER);
        if (qty < rocketHeight) {
            return new Object[]{false, "cannot create firework, missing " + (rocketHeight - qty) + " gunpowder"};
        }

        /*
         * we now know that we have all the needed materials to craft this firework rocket
         */

        // collect the materials from the inventory
        final ArrayList<ItemStack> items = Lists.newArrayList();

        // add the paper
        items.add(extract(PAPER));

        // add the gunpowder
        for (int i = 0; i < rocketHeight; ++i) {
            items.add(extract(GUNPOWDER));
        }

        // add in the firework stars
        ItemStack temp;
        while ((temp = bufferStar.getNextItemStack()) != null) {
            items.add(temp);
        }

        // create the crafting inventory
        final InventoryCrafting crafting = new InventoryCrafting(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer player) {
                return true;
            }
        }, items.size() + 1, 1);

        // copy the items into the crafting inventory
        for (int i = 0; i < items.size(); ++i) {
            crafting.setInventorySlotContents(i, items.get(i));
        }

        // craft, hopefully!
        ItemStack result = manager.findMatchingRecipe(crafting, worldObj);
        if (result == null) {
            return new Object[]{false, "we have the resources, but crafting failed"};
        }

        bufferRocket.addItemStack(result);

        rocketHeight = 0;
        startedRocket = false;
        return new Object[]{true};
    }

    /**
     * @return whether there is a rocket currently crafting
     */
    @LuaFunction
    public boolean isRocketCraftPending() {
        return startedRocket;
    }

    /**
     * @return the height the crafted rocket is for
     */
    @LuaFunction
    public int getRocketHeight() {
        return rocketHeight;
    }

    /**
     * @return the number of Firework Stars crafted and stored in the buffer
     */
    @LuaFunction
    public int getFireworkStarCount() {
        return bufferStar.getCurrentSize();
    }

    /**
     * @return a list of all the firework stars that have been crafted
     */
    @LuaFunction
    public ArrayList<ItemStack> getFireworkStarsList() {
        return bufferStar.getContentsList();
    }

    /**
     * @return the number of Firework Rockets that can be launched
     */
    @LuaFunction
    public int getFireworkRocketCount() {
        return bufferRocket.getCurrentSize();
    }

    /**
     * @return a list of all the Firework Rockets that are in the buffer
     */
    @LuaFunction
    public ArrayList<ItemStack> getFireworkRocketList() {
        return bufferRocket.getContentsList();
    }

    /**
     * @return whether there is a rocket in the launch queue
     */
    @LuaFunction
    public boolean canLaunch() {
        return bufferRocket.getCurrentSize() > 0;
    }

    /**
     * Launches the rocket that is next in the launch queue
     *
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] launch() {
        final ItemStack firework = bufferRocket.getNextItemStack();
        if (firework != null) {
            int offsetX = nextLaunch % 3;
            int offsetY = (int) Math.ceil(nextLaunch / 3);
            nextLaunch = ++nextLaunch % MAX_SLOTS;

            double nX = OFFSETS.get(offsetX);
            double nZ = OFFSETS.get(offsetY);
            final EntityFireworkRocket rocket = new EntityFireworkRocket(
                    worldObj,
                    xCoord + nX,
                    yCoord + 1.1d,
                    zCoord + nZ,
                    firework
            );

            TickHandler.addTickCallback(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    LogUtils.info("Callback finished");
                    worldObj.spawnEntityInWorld(rocket);
                    return null;
                }
            });

            return new Object[]{true};
        }
        return new Object[]{false, "no firework to launch"};
    }

    /**
     * Cancels the rocket that is next in the launch queue, returning the Firework to the inventory, or spawned
     * in the world if there is no space in the inventory.
     *
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] cancelLaunch() {
        if (!canLaunch()) {
            return new Object[]{false, "no launches to cancel"};
        }
        // remove the next rocket from the buffer
        bufferRocket.insertOrExplodeNext(this, worldObj, xCoord, yCoord, zCoord);
        return new Object[]{true};
    }

    /**
     * Cancels the rocket that is currently being crafted, returning any crafted Firework Stars to the inventory,
     * or spawned in the world if there is no space in the inventory.
     *
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     * @see #startRocket(int)
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] cancelRocket() {
        // make there was a started firework
        if (!startedRocket) {
            return new Object[]{false, "no firework rocket started"};
        }
        // remove all the buffer stars from the buffer
        while (bufferStar.getCurrentSize() > 0) {
            bufferStar.insertOrExplodeNext(this, worldObj, xCoord, yCoord, zCoord);
        }
        startedRocket = false;
        return new Object[]{true};
    }

    /**
     * Explodes the supplied colour(s) from ComputerCraft into separate colours
     *
     * @param color the ComputerCraft colour(s)
     * @return the list of colours
     */
    private static ArrayList<Integer> explodeColor(int color) {
        final ArrayList<Integer> colors = Lists.newArrayList();

        for (int i = 0; i < 16; ++i) {
            if (test(color, (int) Math.pow(2, i))) {
                colors.add(i);
            }
        }

        return colors;
    }

    protected int findQtyOf(ItemStack template) {
        return InventoryUtils.findQtyOf(this, template);
    }

    protected ItemStack extract(ItemStack template) {
        return InventoryUtils.extract(this, template, 1);
    }

    private static boolean test(int colors, int color) {
        return (colors & color) == color;
    }

    private static boolean validColor(int color, boolean multi) {
        if (color < 0 || (multi && color > 65535) || (!multi && color > 32768)) return false;
        if (multi) return true;
        double val = Math.log(color) / Math.log(2);
        return val >= 0 && val <= 15 && val % 1 == 0;
    }

    private ItemStack colorToDye(int color) {
        return new ItemStack(Items.dye, 1, 15 - color);
    }

}
