package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import net.minecraft.entity.player.EntityPlayer;
import openperipheral.api.Ignore;

@Ignore
public class TilePlayerDetector extends TilePeripheral implements IActivateAwareTile {

    private static final String TYPE = "player_detector";
    private static final String EVENT_PLAYER = "player";

    public TilePlayerDetector() {
        super(TYPE);
    }

    @Override
    public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }
        computerQueueEvent(EVENT_PLAYER, player.username);
        return true;
    }

}