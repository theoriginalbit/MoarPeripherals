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
package com.theoriginalbit.moarperipherals.common.upgrade.peripheral;

import com.google.common.collect.Maps;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.network.packet.PacketFxOreScanner;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import com.theoriginalbit.moarperipherals.common.utils.PacketUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
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
    private final ITurtleAccess turtle;

    public PeripheralDensityScanner(ITurtleAccess access) {
        turtle = access;
    }

    @LuaFunction
    public float getDensity() throws Exception {
        // get the turtle world and location
        final World world = turtle.getWorld();
        final ChunkCoordinates coords = turtle.getPosition();
        final int x = coords.posX, y = coords.posY, z = coords.posZ;

        // make sure the Turtle is on the ground-ish
        final Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
        if (blockBelow.blockMaterial == Material.air) {
            throw new Exception("Turtle not on the ground");
        }

        // send the effects packet
        PacketUtils.sendToPlayersAround(new PacketFxOreScanner(world.provider.dimensionId, x, y, z),
                x, y, z, ConfigHandler.noteRange, world.provider.dimensionId
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
                    final Block block = Block.blocksList[world.getBlockId(xPos, yPos, zPos)];
                    // get the density of the block, if there is no density mapped we just pretend it is zero-density
                    density += DENSITIES.containsKey(block) ? DENSITIES.get(block) : 0;
                }
            }
        }
        return density;
    }

    public static void addDensityMapping(String modid, String blockName, float density) {
        final Block block = GameRegistry.findBlock(modid, blockName);
        if (block == null) {
            LogUtils.warn(String.format("cannot find block %s:%s to apply a density mapping, skipping", modid, blockName));
            return;
        }
        addDensityMapping(block, density);
    }

    public static void addDensityMapping(Block block, float density) {
        if (DENSITIES.containsKey(block)) {
            LogUtils.info("density mapping already exists for %s, skipping", block.getUnlocalizedName());
            return;
        }
        if (density < 0.0f) {
            LogUtils.warn("density for %s was negative, changing to 0", block.getUnlocalizedName());
        }
        LogUtils.info("added density mapping; " + block.getUnlocalizedName());
        DENSITIES.put(block, Math.max(density, 0.0f));
    }

    static {
        // densities of 'natural' vanilla blocks are taken from real specific gravities (g/cm^3)
        DENSITIES.put(Block.stone, 0.29f); // assumed stone is Bluestone which is a form of Basalt http://geology.about.com/cs/rock_types/a/aarockspecgrav.htm
        DENSITIES.put(Block.grass, 0.12f); // Loam is top-soil http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Block.dirt, 0.12f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Block.waterStill, 0.99f); // http://pubchem.ncbi.nlm.nih.gov
        DENSITIES.put(Block.waterMoving, 0.99f); // http://pubchem.ncbi.nlm.nih.gov
        DENSITIES.put(Block.lavaStill, 2.72f); // http://en.wikipedia.org/wiki/Magma#Density
        DENSITIES.put(Block.lavaMoving, 2.72f); // http://en.wikipedia.org/wiki/Magma#Density
        DENSITIES.put(Block.sand, 0.15f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Block.gravel, 0.16f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Block.oreGold, 19.30f);// http://en.wikipedia.org/wiki/Gold
        DENSITIES.put(Block.oreIron, 7.87f); // http://en.wikipedia.org/wiki/Iron
        DENSITIES.put(Block.oreCoal, 1.25f); // http://geology.about.com/cs/rock_types/a/aarockspecgrav.htm
        DENSITIES.put(Block.wood, 0.72f); // averaged of http://www.simetric.co.uk/si_wood.htm
        DENSITIES.put(Block.oreLapis, 2.80f); // http://www.csgnetwork.com/gemchar.html
        DENSITIES.put(Block.sandStone, 0.25f); // http://geology.about.com/cs/rock_types/a/aarockspecgrav.htm
        DENSITIES.put(Block.web, 1.31f); // http://en.wikipedia.org/wiki/Spider_silk#Density
        DENSITIES.put(Block.obsidian, 2.60f); // http://www.rockcollector.co.uk/infocus/obsidian.php
        DENSITIES.put(Block.oreDiamond, 3.51f); // http://en.wikipedia.org/wiki/Carbon (diamond)
        DENSITIES.put(Block.oreRedstone, 5.20f); // redstone isn't real, so it has been weighted around iron and lapis
        DENSITIES.put(Block.oreRedstoneGlowing, 5.20f); // see above
        DENSITIES.put(Block.ice, 0.92f); // http://en.wikipedia.org/wiki/Ice
        DENSITIES.put(Block.blockClay, 0.16f); // http://www.engineeringtoolbox.com/dirt-mud-densities-d_1727.html
        DENSITIES.put(Block.netherrack, 0.20f); // assumed density between stone and dirt
        DENSITIES.put(Block.slowSand, 0.16f); // assumed density of gravel
        DENSITIES.put(Block.mycelium, 0.12f); // assumed density of grass
        DENSITIES.put(Block.oreEmerald, 2.73f); // calculated from the density of Be3Al2(SiO3)6 and confirmed with http://www.csgnetwork.com/gemchar.html
        DENSITIES.put(Block.oreNetherQuartz, 2.65f); // http://www.csgnetwork.com/gemchar.html

        // only build the custom mappings if the scanner is enabled and there are mappings
        if (ConfigHandler.enableUpgradeOreScanner && !ConfigHandler.userDensityMappings.isEmpty()) {
            final String[] mappings = ConfigHandler.userDensityMappings.split(";");
            for (String str : mappings) {
                try {
                    final String[] map = str.split("@");
                    final String[] block = map[0].split(":");
                    float density = Float.parseFloat(map[1]);
                    PeripheralDensityScanner.addDensityMapping(block[0], block[1], density);
                } catch (NumberFormatException e) {
                    LogUtils.warn("invalid density mapping, cannot parse density float; " + str);
                } catch (Exception e) {
                    LogUtils.warn("invalid density mapping, unable to add mapping; " + str);
                    e.printStackTrace();
                }
            }
        } else {
            LogUtils.info("no custom density mappings in config");
        }
    }
}
