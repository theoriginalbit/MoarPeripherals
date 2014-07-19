package com.theoriginalbit.minecraft.moarperipherals.fluid;

import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

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