package com.theoriginalbit.minecraft.moarperipherals.fluid;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public class BlockFluidGeneric extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected Icon stillIcon;
    @SideOnly(Side.SERVER)
    protected Icon flowingIcon;

    public BlockFluidGeneric(int id, Fluid fluid, Material material, String fluidName) {
        super(id, fluid, material);
        setUnlocalizedName(ModInfo.RESOURCE_DOMAIN + "." + fluidName);
        setCreativeTab(MoarPeripherals.creativeTab);

        // BucketHandler.instance.buckets.put(this, bucket);
    }

}