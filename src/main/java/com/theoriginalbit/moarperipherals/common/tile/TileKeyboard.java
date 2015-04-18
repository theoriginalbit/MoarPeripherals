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
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.base.Strings;
import com.theoriginalbit.moarperipherals.api.hook.IPairedDeviceHook;
import com.theoriginalbit.moarperipherals.common.block.BlockKeyboardPc;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import com.theoriginalbit.moarperipherals.common.base.TileMoarP;
import com.theoriginalbit.moarperipherals.common.util.NBTUtil;
import com.theoriginalbit.moarperipherals.common.util.PairedUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class TileKeyboard extends TileMoarP implements IPairedDeviceHook {
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
            connected = PairedUtil.isRegisteredInstance(connectedInstanceId);
        }

        if (connected && PairedUtil.isRegisteredInstance(connectedInstanceId)) {
            connectedInstanceDesc = PairedUtil.getDescription(connectedInstanceId);
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
        PairedUtil.turnOn(PairedUtil.getInstance(connectedInstanceId));
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
    public void addDrops(List<ItemStack> dropsList) {
        final ItemStack stack = new ItemStack(getBlockType(), 1);
        if (connectedInstanceId > -1) {
            NBTUtil.setInteger(stack, "instanceId", connectedInstanceId);
        }
        dropsList.add(stack);
    }

    public final void terminateTarget() {
        if (isTargetInRange()) {
            PairedUtil.terminate(PairedUtil.getInstance(connectedInstanceId));
        }
    }

    public final void rebootTarget() {
        if (isTargetInRange()) {
            PairedUtil.reboot(PairedUtil.getInstance(connectedInstanceId));
        }
    }

    public final void shutdownTarget() {
        if (isTargetInRange()) {
            PairedUtil.shutdown(PairedUtil.getInstance(connectedInstanceId));
        }
    }

    public final void queueEventToTarget(String event, Object... args) {
        if (isTargetInRange()) {
            PairedUtil.queueEvent(PairedUtil.getInstance(connectedInstanceId), event, args);
        }
    }

    public boolean isTargetInRange() {
        return PairedUtil.isRegisteredInstance(connectedInstanceId);
//        final ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
//        return PairedUtil.isRegisteredInstance(connectedInstanceId) &&
//                PairedUtil.distanceToComputer(connectedInstanceId, coord) <= ConfigData.keyboardRange;
    }
}