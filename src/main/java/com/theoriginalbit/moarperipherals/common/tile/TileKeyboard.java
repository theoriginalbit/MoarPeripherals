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

import com.google.common.base.Strings;
import com.theoriginalbit.moarperipherals.api.tile.IPairedDevice;
import com.theoriginalbit.moarperipherals.common.block.BlockKeyboardPc;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.moarperipherals.common.utils.NBTUtils;
import com.theoriginalbit.moarperipherals.common.utils.PairedUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class TileKeyboard extends TileMoarP implements IPairedDevice {
    private int connectedInstanceId = -1;
    private String connectedInstanceDesc;
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
        if (!Strings.isNullOrEmpty(connectedInstanceDesc)) {
            tag.setString("instanceDesc", connectedInstanceDesc);
        }
    }

    @Override
    public void updateEntity() {
        if (!connected) {
            connected = PairedUtils.isRegisteredInstance(connectedInstanceId);
        }

        if (connected && PairedUtils.isRegisteredInstance(connectedInstanceId)) {
            connectedInstanceDesc = PairedUtils.getDescription(connectedInstanceId);
        }
    }

    @Override
    public NBTTagCompound getDescriptionNBT() {
        final NBTTagCompound tag = super.getDescriptionNBT();
        tag.setInteger("instanceId", connectedInstanceId);
        if (!Strings.isNullOrEmpty(connectedInstanceDesc)) {
            tag.setString("instanceDesc", connectedInstanceDesc);
        }
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
        PairedUtils.turnOn(PairedUtils.getInstance(connectedInstanceId));
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
        if (tag.hasKey("instanceDesc")) {
            connectedInstanceDesc = tag.getString("instanceDesc");
        }
        return true;
    }

    @Override
    public ItemStack getPairedDrop() {
        ItemStack stack = new ItemStack(getBlockType(), 1);
        if (connectedInstanceId > -1) {
            NBTUtils.setInteger(stack, "instanceId", connectedInstanceId);
        }
        return stack;
    }

    public final void terminateTarget() {
        if (isTargetInRange()) {
            PairedUtils.terminate(PairedUtils.getInstance(connectedInstanceId));
        }
    }

    public final void rebootTarget() {
        if (isTargetInRange()) {
            PairedUtils.reboot(PairedUtils.getInstance(connectedInstanceId));
        }
    }

    public final void shutdownTarget() {
        if (isTargetInRange()) {
            PairedUtils.shutdown(PairedUtils.getInstance(connectedInstanceId));
        }
    }

    public final void queueEventToTarget(String event, Object... args) {
        if (isTargetInRange()) {
            PairedUtils.queueEvent(PairedUtils.getInstance(connectedInstanceId), event, args);
        }
    }

    public boolean isTargetInRange() {
        return PairedUtils.isRegisteredInstance(connectedInstanceId);
//        final ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
//        return PairedUtils.isRegisteredInstance(connectedInstanceId) &&
//                PairedUtils.distanceToComputer(connectedInstanceId, coord) <= ConfigData.keyboardRange;
    }

}