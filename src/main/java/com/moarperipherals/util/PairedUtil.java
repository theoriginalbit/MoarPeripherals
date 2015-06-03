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
package com.moarperipherals.util;

import com.google.common.base.Strings;
import com.moarperipherals.integration.init.ComputerCraft;
import dan200.computercraft.shared.computer.blocks.IComputerTile;
import dan200.computercraft.shared.computer.blocks.TileCommandComputer;
import dan200.computercraft.shared.computer.blocks.TileComputerBase;
import dan200.computercraft.shared.computer.core.IComputer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;

/**
 * @author theoriginalbit
 */
public final class PairedUtil {
    public static boolean isComputer(TileEntity tile) {
        return tile instanceof TileComputerBase;
    }

    public static boolean isPairAllowed(TileEntity tile) {
        return tile instanceof TileComputerBase && !(tile instanceof TileCommandComputer);
    }

    public static int getInstanceId(TileEntity tile) {
        if (tile instanceof IComputerTile) {
            IComputerTile computerTile = (IComputerTile) tile;
            IComputer computer = computerTile.getComputer();
            return computer.getInstanceID();
        }

        return -1;
    }

    public static IComputer getInstance(int instanceId) {
        return ComputerCraft.getComputer(instanceId);
    }

    public static boolean isInstance(int instanceId) {
        return getInstance(instanceId) != null;
    }

    public static void queueEvent(int instanceId, String event, Object... args) {
        IComputer instance = getInstance(instanceId);
        if (instance != null) {
            instance.queueEvent(event, args);
        }
    }

    public static boolean isOn(int instanceId) {
        IComputer instance = getInstance(instanceId);
        return instance != null && instance.isOn();
    }

    public static void turnOn(int instanceId) {
        IComputer instance = getInstance(instanceId);
        if (instance != null) {
            instance.turnOn();
        }
    }

    public static void shutdown(int instanceId) {
        IComputer instance = getInstance(instanceId);
        if (instance != null) {
            instance.shutdown();
        }
    }

    public static void reboot(int instanceId) {
        IComputer instance = getInstance(instanceId);
        if (instance != null) {
            instance.reboot();
        }
    }

    public static void terminate(int instanceId) {
        queueEvent(instanceId, ComputerCraft.EVENT.TERMINATE);
    }

    public static String getDescription(int instanceId) {
        final IComputer computer = getInstance(instanceId);

        if (computer != null) {
            final int id = computer.getID();
            final String label = computer.getLabel();

            if (Strings.isNullOrEmpty(label)) {
                return String.format("#%d", id);
            }

            return String.format("%s (#%d)", label, id);
        }

        return EnumChatFormatting.OBFUSCATED + "[error]";
    }
}
