package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TilePlayerDetector;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlayerDetector extends BlockGeneric {

    public BlockPlayerDetector() {
        super(Settings.blockIdPlayerDetector, Material.rock, "playerdetector");
        setStepSound(soundStoneFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TilePlayerDetector();
    }

}