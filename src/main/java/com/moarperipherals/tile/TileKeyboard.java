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
package com.moarperipherals.tile;

import com.moarperipherals.block.IPairedDeviceHook;
import com.moarperipherals.block.BlockKeyboardPc;
import com.moarperipherals.ModInfo;
import com.moarperipherals.util.NBTUtil;
import com.moarperipherals.util.PairedUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class TileKeyboard extends TileMoarP implements IPairedDeviceHook {
    private int connectedInstanceId = -1;
    private boolean connected;

    /**
     * Read the target information from NBT
     */
    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        configureTargetFromNbt(tag);
    }

    /**
     * Write the target information to NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("instanceId", connectedInstanceId);
    }

    @Override
    public void updateEntity() {
        if (!connected) {
            connected = PairedUtil.isInstance(connectedInstanceId);
        }
    }

    @Override
    public NBTTagCompound getDescriptionNBT() {
        final NBTTagCompound tag = super.getDescriptionNBT();
        tag.setInteger("instanceId", connectedInstanceId);
        return tag;
    }

    @Override
    protected void readDescriptionNBT(NBTTagCompound tag) {
        super.readDescriptionNBT(tag);
        configureTargetFromNbt(tag);
    }

    /**
     * When the Keyboard is right-clicked, it shall turn on the target computer if it is not on
     */
    @Override
    public boolean blockActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        PairedUtil.turnOn(connectedInstanceId);
        return true;
    }

    /**
     * Whether the keyboard has a valid connection or not
     */
    public final boolean hasConnection() {
        return connectedInstanceId > -1;
    }

    /**
     * Used by the renderer to get which texture to display based on the connection status
     */
    public final ResourceLocation getTextureForRender() {
        final String state;
        if (hasConnection() && isTargetInRange()) {
            state = "On";
        } else if (hasConnection() && !isTargetInRange()) {
            state = "Lost";
        } else {
            state = "Off";
        }
        final int meta = getBlockType() instanceof BlockKeyboardPc ? 1 : 0;
        return new ResourceLocation(
                ModInfo.RESOURCE_DOMAIN,
                String.format("textures/models/blocks/keyboard/Keyboard_%d_%s.png", meta, state)
        );
    }

    @Override
    public final boolean configureTargetFromNbt(NBTTagCompound tag) {
        connectedInstanceId = tag.getInteger("instanceId");
        return true;
    }

    @Override
    public void addDrops(List<ItemStack> dropsList) {
        final ItemStack stack = new ItemStack(getBlockType(), 1);
        if (connectedInstanceId > -1) {
            NBTUtil.setInteger(stack, "instanceId", connectedInstanceId);
        }
        dropsList.add(stack);
    }

    public final void terminateTarget() {
        if (isTargetInRange()) {
            PairedUtil.terminate(connectedInstanceId);
        }
    }

    public final void rebootTarget() {
        if (isTargetInRange()) {
            PairedUtil.reboot(connectedInstanceId);
        }
    }

    public final void shutdownTarget() {
        if (isTargetInRange()) {
            PairedUtil.shutdown(connectedInstanceId);
        }
    }

    public final void queueEventToTarget(String event, Object... args) {
        if (isTargetInRange()) {
            PairedUtil.queueEvent(connectedInstanceId, event, args);
        }
    }

    public boolean isTargetInRange() {
        return PairedUtil.isInstance(connectedInstanceId);
//        final ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
//        return PairedUtil.isRegisteredInstance(connectedInstanceId) &&
//                PairedUtil.distanceToComputer(connectedInstanceId, coord) <= ConfigData.keyboardRange;
    }
}