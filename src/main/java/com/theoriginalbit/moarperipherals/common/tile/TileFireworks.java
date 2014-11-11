/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.MultiReturn;
import com.theoriginalbit.moarperipherals.api.tile.aware.IActivateAwareTile;
import com.theoriginalbit.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.moarperipherals.common.tile.firework.LauncherTube;
import com.theoriginalbit.moarperipherals.common.tile.firework.QueueBuffer;
import com.theoriginalbit.moarperipherals.common.reference.Constants;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileInventory;
import com.theoriginalbit.moarperipherals.common.utils.InventoryUtils;
import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

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
    private static final ItemStack GLOWSTONE = new ItemStack(Items.glowstone_dust);
    private static final ItemStack GUNPOWDER = new ItemStack(Items.gunpowder);
    private static final ItemStack DIAMOND = new ItemStack(Items.diamond);
    private static final ItemStack PAPER = new ItemStack(Items.paper);
    private static final int MAX_SLOTS = 9;

    // the temporary storage for crafted rockets and stars
    protected final QueueBuffer bufferRocket = new QueueBuffer("Rocket", 90);
    protected final QueueBuffer bufferStar = new QueueBuffer("Star", 90);

    private final LauncherTube[] fireworkTubes = new LauncherTube[9];

    // the crafting manager
    final CraftingManager manager = CraftingManager.getInstance();

    public TileFireworks() {
        this(54);
    }

    protected TileFireworks(int invSize) {
        super(invSize);

        for (int i = 0; i < fireworkTubes.length; ++i) {
            fireworkTubes[i] = new LauncherTube(bufferRocket, i);
        }
    }

    @Override
    public void updateEntity() {
        synchronized (fireworkTubes) {
            for (final LauncherTube tube : fireworkTubes) {
                tube.update();
            }
        }
    }

    @Override
    public String getInventoryName() {
        return Constants.GUI.FIREWORKS.getLocalised();
    }

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!isCreativeLauncher()) {
            player.displayGUIChest(this);
            return true;
        }
        return false;
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
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        // make sure to retain the buffers!
        bufferRocket.writeToNBT(tag);
        bufferStar.writeToNBT(tag);
    }

    /**
     * The item in the slot for Firework Launcher inventory
     *
     * @param slot the slot to get the item from
     * @return the item in the slot
     */
    @LuaFunction("getStackInSlot")
    public ItemStack stackInSlot(int slot) {
        // convert from Lua indexes that start at 1
        --slot;
        if (slot >= 0 && slot < getSizeInventory()) {
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

    @LuaFunction
    public boolean isCreativeLauncher() {
        return false;
    }

    /**
     * Loads a single pre-made Firework Rocket from the Firework Launchers inventory into the end of the
     * firework rocket launch queue. Or loads a Firework Star from the inventory into the star queue.
     *
     * @param slot the slot the rocket is in
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction
    @MultiReturn
    public Object[] load(int slot) {
        // convert from Lua indexes that start at 1
        --slot;
        // make sure the slot is in range
        if (slot < 0 || slot > getSizeInventory()) {
            return new Object[]{false, "slot out of range, should be 1-" + getSizeInventory()};
        }
        // make sure there is a stack
        final ItemStack stack = getStackInSlot(slot);
        if (stack == null) {
            return new Object[]{false, "nothing in slot"};
        }
        final Item item = stack.getItem();

        QueueBuffer buffer = null;
        // if it's a rocket, add it
        if (item instanceof ItemFirework) {
            buffer = bufferRocket;
        }
        // if it's a star, add it
        if (item instanceof ItemFireworkCharge) {
            buffer = bufferStar;
        }

        if (buffer == null) {
            return new Object[]{false, "item in slot is not a Firework Rocket or Firework Star"};
        }

        if (stack.stackSize == 1) {
            setInventorySlotContents(slot, null);
        }

        return new Object[]{true, buffer.addItemStack(stack.splitStack(1))};
    }

    /**
     * Adds a <a href="http://minecraft.gamepedia.com/Firework_star">Firework Star</a> to the currently
     * crafting rocket.
     *
     * @param color    the <a href="http://computercraft.info/wiki/Colors_(API)">colour(s)</a> for the firework
     * @param shape    the <a href="http://minecraft.gamepedia.com/Firework_Rocket#Effects">shape/effect</a>
     *                 of the firework
     * @param modifier the <a href="http://minecraft.gamepedia.com/Firework_Rocket#Additional_effects">modifier/additional effects</a>
     *                 of the firework
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true and the ID (not session persistent) of the firework star
     */
    @LuaFunction
    @MultiReturn
    public Object[] craftFireworkStar(int color, int shape, int modifier) {
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
         * 0 - none, small ball
         * 1 - fire charge, large ball
         * 2 - gold nugget, star-shaped
         * 3 - head, creeper-shaped
         * 4 - feather, burst explosion
         */
        if (shape != 0) {
            --shape;
            // if we want a head, look for it
            if (shape == 2) {
                boolean found = false;
                for (Head head : Head.values()) {
                    if (findQtyOf(head.getItemStack()) > 0) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return new Object[]{false, "cannot create firework star, no mob head found"};
                }
            } else {
                final ItemStack stack = ITEM_SHAPES.get(shape);
                if (findQtyOf(stack) == 0) {
                    return new Object[]{false, "cannot create firework star, no " + stack.getDisplayName() + " found"};
                }
            }
            hasShape = true;
            --remaining;
        }

        // make sure we have the materials for the required modifier
        /*
         * Modifier / Additional effects:
         * 0 - none
         * 1 - glowstone, twinkle
         * 2 - diamond, trail effect
         * 3 - both
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
            // if we want a head, attempt to extract one
            if (shape == 2) {
                for (Head head : Head.values()) {
                    ItemStack s = extract(head.getItemStack());
                    if (s != null && s.stackSize > 0) {
                        items.add(s);
                        break;
                    }
                }
            } else {
                items.add(extract(ITEM_SHAPES.get(shape)));
            }
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
        final ItemStack result = manager.findMatchingRecipe(crafting, worldObj);
        if (result == null) {
            return new Object[]{false, "we have the resources, but crafting failed"};
        }

        // add the newly crafted star to the buffer for the rocket and put it's ID in the return result
        return new Object[]{true, bufferStar.addItemStack(result)};
    }

    /**
     * Crafts a <a href="http://minecraft.gamepedia.com/Firework_Rocket">Firework Rocket</a> with a particular height,
     * and firework stars. After crafting it will add the rocket to the launch queue. If there are not enough ingredients
     * to finalise the rocket, it will not craft or add the rocket to the queue.
     *
     * @param height  the height the rocket can travel
     * @param starIds the list of firework star IDs to include in the rocket
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true and the ID (not session persistent) of the firework rocket
     */
    @LuaFunction
    @MultiReturn
    public Object[] craftFireworkRocket(int height, List<Double> starIds) {
        if (!bufferRocket.hasFreeSpace()) {
            return new Object[]{false, "no free space in the rocket launch buffer"};
        }
        // validate height
        if (!(height >= 1 && height <= 3)) {
            return new Object[]{false, "rocket height should be between 1 and 3"};
        }
        // check we have the paper to craft
        if (findQtyOf(PAPER) == 0) {
            return new Object[]{false, "cannot create firework, no paper"};
        }
        // check if we have the gunpowder to craft
        final int qty = findQtyOf(GUNPOWDER);
        if (qty < height) {
            return new Object[]{false, "cannot create firework, missing " + (height - qty) + " gunpowder"};
        }
        // validate number of stars, slot count, gunpowder for height, and paper
        if (starIds.size() > (MAX_SLOTS - height - 1)) {
            return new Object[]{false, "cannot craft, too many firework stars"};
        }
        // check existence of the stars
        for (final Double id : starIds) {
            int rId = id.intValue();
            if (!bufferStar.containsItemStackWithId(rId)) {
                return new Object[]{false, "cannot craft, no firework rocket with ID " + rId};
            }
        }

        /*
         * we now know that we have all the needed materials to craft this firework rocket
         */

        // collect the materials from the inventory
        final ArrayList<ItemStack> items = Lists.newArrayList();

        // add the paper
        items.add(extract(PAPER));

        // add the gunpowder
        for (int i = 0; i < height; ++i) {
            items.add(extract(GUNPOWDER));
        }

        // add in the firework stars
        for (final Double id : starIds) {
            items.add(bufferStar.getItemStackWithId(id.intValue()));
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

        return new Object[]{true, bufferRocket.addItemStack(result)};
    }

    /**
     * Launches the rocket that is next in the launch queue
     *
     * @return the result. if crafting failed it will return false, and a string message as to why it failed. if crafting
     * succeeded then it will return true, and how many more firework stars can be added to the firework rocket
     */
    @LuaFunction
    @MultiReturn
    public Object[] launch() {
        // attempt to launch a rocket
        synchronized (fireworkTubes) {
            for (final LauncherTube tube : fireworkTubes) {
                if (tube.canLaunch()) {
                    return tube.launch(worldObj, xCoord, yCoord, zCoord);
                }
            }
        }

        // it didn't launch
        return new Object[]{false, "Cannot launch rocket, rockets currently reloading"};
    }

    /**
     * Unloads the rocket with the supplied ID, returning the firework to the inventory, or spawned in the
     * world if there is no space in the inventory.
     *
     * @param id the ID of the rocket
     * @return the success of unloading
     */
    @LuaFunction
    @MultiReturn
    public Object[] unloadFireworkRocket(int id) {
        if (!bufferRocket.containsItemStackWithId(id)) {
            return new Object[]{"No Firework Rocket with that ID found"};
        }
        bufferRocket.insertOrExplode(this, worldObj, xCoord, yCoord, zCoord, id);
        return new Object[]{true};
    }

    /**
     * Unloads the firework star with the supplied ID, returning the star to the inventory, or spawned in the
     * world if there is no space in the inventory.
     *
     * @param id the ID of the star
     * @return the success of unloading
     */
    @LuaFunction
    @MultiReturn
    public Object[] unloadFireworkStar(int id) {
        if (!bufferStar.containsItemStackWithId(id)) {
            return new Object[]{"No Firework Star with that ID found"};
        }
        bufferStar.insertOrExplode(this, worldObj, xCoord, yCoord, zCoord, id);
        return new Object[]{true};
    }

    /**
     * @return whether there is a rocket in the launch queue
     */
    @LuaFunction
    public boolean canLaunch() {
        if (bufferRocket.getCurrentSize() == 0) {
            return false;
        }
        synchronized (fireworkTubes) {
            for (final LauncherTube tube : fireworkTubes) {
                if (tube.canLaunch()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return all the IDs of the firework stars within the buffer
     */
    @LuaFunction
    public ArrayList<Integer> getFireworkStarIds() {
        return bufferStar.getWrapperIds();
    }

    /**
     * @return all the IDs of the firework rockets within the buffer
     */
    @LuaFunction
    public ArrayList<Integer> getFireworkRocketIds() {
        return bufferRocket.getWrapperIds();
    }

    @LuaFunction
    public ArrayList<String> inspectFireworkRocket(int id) throws LuaException {
        if (!bufferRocket.containsItemStackWithId(id)) {
            throw new LuaException("No Firework Rocket with that ID found");
        }
        return getDescription(bufferRocket, id);
    }

    @LuaFunction
    public ArrayList<String> inspectFireworkStar(int id) throws LuaException {
        if (!bufferStar.containsItemStackWithId(id)) {
            throw new LuaException("No Firework Star with that ID found");
        }
        return getDescription(bufferStar, id);
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

    protected int findQtyOf(ItemStack stack) {
        // if this is creative, we always have everything!
        return isCreativeLauncher() ? 64 : InventoryUtils.findQtyOf(this, stack);
    }

    protected ItemStack extract(ItemStack stack) {
        // if this is creative, lets just dupe the item
        if (isCreativeLauncher()) {
            final ItemStack s = stack.copy();
            s.stackSize = 1;
            return s;
        }
        return InventoryUtils.takeItems(this, stack, 1);
    }

    private static boolean test(int colors, int color) {
        return (colors & color) == color;
    }

    private static boolean validColor(int color, boolean multi) {
        if (color < 1 || (multi && color > 65535) || (!multi && color > 32768)) return false;
        if (multi) return true;
        double val = Math.log(color) / Math.log(2);
        return val >= 0 && val <= 15 && val % 1 == 0;
    }

    private static ItemStack colorToDye(int color) {
        return new ItemStack(Items.dye, 1, 15 - color);
    }

    private static ArrayList<String> getDescription(QueueBuffer buffer, int id) throws LuaException {
        final ArrayList<String> info = Lists.newArrayList();
        addInformation(buffer.peekItemStackWithId(id), info);
        return info;
    }

    private static void addInformation(ItemStack stack, ArrayList<String> list) {
        if (!stack.hasTagCompound()) {
            return;
        }
        final Item item = stack.getItem();
        if (item instanceof ItemFirework) {
            addFireworkRocketInfo(stack.getTagCompound().getCompoundTag("Fireworks"), list);
        } else if (item instanceof ItemFireworkCharge) {
            addFireworkChargeInfo(stack.getTagCompound().getCompoundTag("Explosion"), list);
        }
    }

    /*
     * Code taken from the Minecraft class so that it may be used on Side.SERVER as well as Side.CLIENT
     */
    private static void addFireworkRocketInfo(NBTTagCompound tag, ArrayList<String> list) {
        if (tag.hasKey("Flight", 99)) {
            list.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + tag.getByte("Flight"));
        }

        NBTTagList nbttaglist = tag.getTagList("Explosions", 10);

        if (nbttaglist != null && nbttaglist.tagCount() > 0) {
            for (int i = 0; i < nbttaglist.tagCount(); ++i) {
                final NBTTagCompound compound = nbttaglist.getCompoundTagAt(i);
                final ArrayList<String> other = Lists.newArrayList();
                addFireworkChargeInfo(compound, other);

                if (other.size() > 0) {
                    for (int j = 1; j < other.size(); ++j) {
                        other.set(j, other.get(j));
                    }

                    list.addAll(other);
                }
            }
        }
    }

    /*
     * Code taken from the Minecraft class so that it may be used on Side.SERVER as well as Side.CLIENT
     */
    private static void addFireworkChargeInfo(NBTTagCompound tag, ArrayList<String> list) {
        byte b0 = tag.getByte("Type");

        if (b0 >= 0 && b0 <= 4) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.type." + b0).trim());
        } else {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }

        int[] aInt = tag.getIntArray("Colors");
        int j, k;

        if (aInt.length > 0) {
            boolean flag = true;
            String s = "";
            int i = aInt.length;

            for (j = 0; j < i; ++j) {
                k = aInt[j];

                if (!flag) {
                    s = s + ", ";
                }

                flag = false;
                boolean flag1 = false;

                for (int l = 0; l < 16; ++l) {
                    if (k == ItemDye.field_150922_c[l]) {
                        flag1 = true;
                        s = s + StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.field_150923_a[l]);
                        break;
                    }
                }

                if (!flag1) {
                    s = s + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }

            list.add(s);
        }

        int[] aint2 = tag.getIntArray("FadeColors");
        boolean flag2;

        if (aint2.length > 0) {
            flag2 = true;
            String s1 = StatCollector.translateToLocal("item.fireworksCharge.fadeTo") + " ";
            j = aint2.length;

            for (k = 0; k < j; ++k) {
                int j1 = aint2[k];

                if (!flag2) {
                    s1 = s1 + ", ";
                }

                flag2 = false;
                boolean flag4 = false;

                for (int i1 = 0; i1 < 16; ++i1) {
                    if (j1 == ItemDye.field_150922_c[i1]) {
                        flag4 = true;
                        s1 = s1 + StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.field_150923_a[i1]);
                        break;
                    }
                }

                if (!flag4) {
                    s1 = s1 + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }

            list.add(s1);
        }

        flag2 = tag.getBoolean("Trail");

        if (flag2) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }

        boolean flag3 = tag.getBoolean("Flicker");

        if (flag3) {
            list.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }

    private enum Head {

        SKELETON, WITHER, ZOMBIE, PLAYER, CREEPER;

        private final ItemStack itemStack;

        private Head() {
            itemStack = new ItemStack(Items.skull, 1, ordinal());
        }

        public ItemStack getItemStack() {
            return itemStack;
        }
    }

}
