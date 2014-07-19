package com.theoriginalbit.minecraft.moarperipherals.block;

import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileIronNote;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockIronNote extends BlockGeneric {

    public BlockIronNote() {
        super(Settings.blockIdIronNote, Material.iron, "ironnote", soundMetalFootstep);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileIronNote();
    }

}