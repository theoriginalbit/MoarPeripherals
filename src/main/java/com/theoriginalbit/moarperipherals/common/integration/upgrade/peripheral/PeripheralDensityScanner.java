/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.integration.upgrade.peripheral;

import com.google.common.collect.Maps;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.network.PacketHandler;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxOreScanner;
import com.theoriginalbit.moarperipherals.common.util.LogUtil;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.util.HashMap;

/**
 * @author theoriginalbit
 * @since 15/11/14
 */
@LuaPeripheral("ore_scanner")
public class PeripheralDensityScanner {
    private static final HashMap<Block, Float> DENSITIES = Maps.newHashMap();
    private static final int SCAN_DIAMETER = 5;
    private static final int MAX_DEPTH = 40;

    static {
        // densities of 'natural' vanilla blocks are taken from real specific gravities (g/cm^3)
        DENSITIES.put(Blocks.stone, 0.29f); // assumed stone is Bluestone which is a form of Basalt http://geology.about.com/cs/rock_types/a/aarockspecgrav.htm
        DENSITIES.put(Blocks.grass, 0.12f); // Loam is top-soil http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Blocks.dirt, 0.12f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Blocks.water, 0.99f); // http://pubchem.ncbi.nlm.nih.gov
        DENSITIES.put(Blocks.flowing_water, 0.99f); // http://pubchem.ncbi.nlm.nih.gov
        DENSITIES.put(Blocks.lava, 2.72f); // http://en.wikipedia.org/wiki/Magma#Density
        DENSITIES.put(Blocks.flowing_lava, 2.72f); // http://en.wikipedia.org/wiki/Magma#Density
        DENSITIES.put(Blocks.sand, 0.15f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Blocks.gravel, 0.16f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Blocks.gold_ore, 19.30f);// http://en.wikipedia.org/wiki/Gold
        DENSITIES.put(Blocks.iron_ore, 7.87f); // http://en.wikipedia.org/wiki/Iron
        DENSITIES.put(Blocks.coal_ore, 1.25f); // http://geology.about.com/cs/rock_types/a/aarockspecgrav.htm
        DENSITIES.put(Blocks.log, 0.72f); // averaged of http://www.simetric.co.uk/si_wood.htm
        DENSITIES.put(Blocks.log2, 0.72f); // averaged of http://www.simetric.co.uk/si_wood.htm
        DENSITIES.put(Blocks.lapis_ore, 2.80f); // http://www.csgnetwork.com/gemchar.html
        DENSITIES.put(Blocks.sandstone, 0.25f); // http://geology.about.com/cs/rock_types/a/aarockspecgrav.htm
        DENSITIES.put(Blocks.web, 1.31f); // http://en.wikipedia.org/wiki/Spider_silk#Density
        DENSITIES.put(Blocks.obsidian, 2.60f); // http://www.rockcollector.co.uk/infocus/obsidian.php
        DENSITIES.put(Blocks.diamond_ore, 3.51f); // http://en.wikipedia.org/wiki/Carbon (diamond)
        DENSITIES.put(Blocks.redstone_ore, 5.20f); // redstone isn't real, so it has been weighted around iron and lapis
        DENSITIES.put(Blocks.lit_redstone_ore, 5.20f); // see above
        DENSITIES.put(Blocks.ice, 0.92f); // http://en.wikipedia.org/wiki/Ice
        DENSITIES.put(Blocks.clay, 0.16f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Blocks.netherrack, 0.20f); // assumed density between stone and dirt
        DENSITIES.put(Blocks.soul_sand, 0.16f); // assumed density of gravel
        DENSITIES.put(Blocks.mycelium, 0.12f); // assumed density of grass
        DENSITIES.put(Blocks.emerald_ore, 2.73f); // calculated from the density of Be3Al2(SiO3)6 and confirmed with http://www.csgnetwork.com/gemchar.html
        DENSITIES.put(Blocks.quartz_ore, 2.65f); // http://www.csgnetwork.com/gemchar.html
        DENSITIES.put(Blocks.packed_ice, 0.94f); // density between ice and water

        // only build the custom mappings if the scanner is enabled and there are mappings
        if (ConfigData.enableUpgradeOreScanner && !ConfigData.userDensityMappings.isEmpty()) {
            final String[] mappings = ConfigData.userDensityMappings.split(";");
            for (String str : mappings) {
                try {
                    final String[] map = str.split("@");
                    final String[] block = map[0].split(":");
                    float density = Float.parseFloat(map[1]);
                    PeripheralDensityScanner.addDensityMapping(block[0], block[1], density);
                } catch (NumberFormatException e) {
                    LogUtil.warn("invalid density mapping, cannot parse density float; " + str);
                } catch (Exception e) {
                    LogUtil.warn("invalid density mapping, unable to add mapping; " + str);
                    e.printStackTrace();
                }
            }
        } else {
            LogUtil.info("no custom density mappings in config");
        }
    }

    private final ITurtleAccess turtle;

    public PeripheralDensityScanner(ITurtleAccess access) {
        turtle = access;
    }

    public static void addDensityMapping(String modid, String blockName, float density) {
        final Block block = GameRegistry.findBlock(modid, blockName);
        if (block == null) {
            LogUtil.warn(String.format("cannot find block %s:%s to apply a density mapping, skipping", modid,
                    blockName));
            return;
        }
        addDensityMapping(block, density);
    }

    public static void addDensityMapping(Block block, float density) {
        if (DENSITIES.containsKey(block)) {
            LogUtil.info("density mapping already exists for %s, skipping", block.getUnlocalizedName());
            return;
        }
        if (density < 0.0f) {
            LogUtil.warn("density for %s was negative, changing to 0", block.getUnlocalizedName());
        }
        LogUtil.info("added density mapping; " + block.getUnlocalizedName());
        DENSITIES.put(block, Math.max(density, 0.0f));
    }

    @LuaFunction
    public float getDensity() throws LuaException {
        // get the turtle world and location
        final World world = turtle.getWorld();
        final ChunkCoordinates coords = turtle.getPosition();
        final int x = coords.posX, y = coords.posY, z = coords.posZ;

        // make sure the Turtle is on the ground-ish
        final Block blockBelow = world.getBlock(x, y - 1, z);
        if (blockBelow.getMaterial() == Material.air) {
            throw new LuaException("Turtle not on the ground");
        }

        // send the effects packet
        PacketHandler.INSTANCE.sendToAllAround(
                new MessageFxOreScanner(world.provider.dimensionId, x, y, z),
                new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 64d)
        );

        // figure out the scan area
        int radius = (int) Math.floor(SCAN_DIAMETER / 2);
        int maxX = x + radius, minX = x - radius;
        int maxY = y - 1, minY = Math.max(maxY - MAX_DEPTH, 0);
        int maxZ = z + radius, minZ = z - radius;

        // scan the area
        float density = 0.0f;
        for (int xPos = minX; xPos <= maxX; ++xPos) {
            for (int zPos = minZ; zPos <= maxZ; ++zPos) {
                for (int yPos = minY; yPos <= maxY; ++yPos) {
                    final Block block = world.getBlock(xPos, yPos, zPos);
                    /*
                     * get the density of the block, if there is no density mapped we just make it 0.5,
                     * there's just way too many blocks to map them all, so the main ones are mapped and
                     * the rest are just assumed to be 0.5
                     */
                    density += DENSITIES.containsKey(block) ? DENSITIES.get(block) : 0.5f;
                }
            }
        }
        return density;
    }
}
