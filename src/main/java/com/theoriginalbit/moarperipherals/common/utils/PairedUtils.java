/**
 * Copyright 2015 Joshua Asbury (@theoriginalbit)
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
package com.theoriginalbit.moarperipherals.common.utils;

import com.google.common.base.Strings;
import com.theoriginalbit.moarperipherals.common.reference.Mods;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author theoriginalbit
 */
@SuppressWarnings("unchecked")
public final class PairedUtils {
    private static final Class<?> CLASS_COMPUTER_CMD = ReflectionUtils.getClass("dan200.computercraft.shared.computer.blocks.TileCommandComputer");
    private static final Class<?> CLASS_COMPUTER_BASE = ReflectionUtils.getClass("dan200.computercraft.shared.computer.blocks.TileComputerBase");
    private static final Class<?> CLASS_I_COMPUTER = ReflectionUtils.getClass("dan200.computercraft.shared.computer.core.IComputer");
    private static final Method METHOD_QUEUE_EVENT = ReflectionUtils.getMethod(CLASS_I_COMPUTER, "queueEvent", String.class, Object[].class);
    private static final Method METHOD_IS_ON = ReflectionUtils.getMethod(CLASS_I_COMPUTER, "isOn");
    private static final Method METHOD_TURN_ON = ReflectionUtils.getMethod(CLASS_I_COMPUTER, "turnOn");
    private static final Method METHOD_SHUTDOWN = ReflectionUtils.getMethod(CLASS_I_COMPUTER, "shutdown");
    private static final Method METHOD_REBOOT = ReflectionUtils.getMethod(CLASS_I_COMPUTER, "reboot");
    private static final String EVENT_TERMINATE = "terminate";

    private static Map<Integer, Object> computerRegistry = new HashMap<>();

    public static boolean isComputer(TileEntity tile) {
        if (tile == null) return false;
        final Class<?> clazz = tile.getClass();
        return instanceOf(clazz, CLASS_COMPUTER_BASE);
    }

    public static boolean isPairAllowed(TileEntity tile) {
        if (tile == null) return false;
        final Class<?> clazz = tile.getClass();
        return isComputer(tile) && !instanceOf(clazz, CLASS_COMPUTER_CMD);
    }

    public static int getInstanceId(TileEntity tile) {
        if (tile == null) return -1;

        try {
            Field idField = CLASS_COMPUTER_BASE.getDeclaredField("m_instanceID");
            idField.setAccessible(true);
            int instanceId = (int) idField.get(tile);
            idField.setAccessible(false);
            return instanceId;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Object getInstance(int instanceId) {
        if (!isRegisteredInstance(instanceId)) return null;
        return computerRegistry.get(instanceId);
    }

    public static boolean isRegisteredInstance(int instanceId) {
        return computerRegistry.containsKey(instanceId);
    }

    public static ChunkCoordinates getInstanceLocation(int instanceId) {
        Object computer = getInstance(instanceId);
        if (computer == null) return null;

        try {
            Field field = computer.getClass().getDeclaredField("m_position");
            field.setAccessible(true);
            return (ChunkCoordinates) field.get(computer);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void queueEvent(Object instance, String event, Object... args) {
        if (instance == null || !CLASS_I_COMPUTER.isAssignableFrom(instance.getClass())) return;
        ReflectionUtils.callMethod(instance, METHOD_QUEUE_EVENT, event, args);
    }

    public static boolean isOn(Object instance) {
        if (instance == null || !CLASS_I_COMPUTER.isAssignableFrom(instance.getClass())) return false;
        return (Boolean) ReflectionUtils.callMethod(instance, METHOD_IS_ON);
    }

    public static void turnOn(Object instance) {
        if (instance == null || !CLASS_I_COMPUTER.isAssignableFrom(instance.getClass())) return;
        ReflectionUtils.callMethod(instance, METHOD_TURN_ON);
    }

    public static void shutdown(Object instance) {
        if (instance == null || !CLASS_I_COMPUTER.isAssignableFrom(instance.getClass())) return;
        ReflectionUtils.callMethod(instance, METHOD_SHUTDOWN);
    }

    public static void reboot(Object instance) {
        if (instance == null || !CLASS_I_COMPUTER.isAssignableFrom(instance.getClass())) return;
        ReflectionUtils.callMethod(instance, METHOD_REBOOT);
    }

    public static void terminate(Object instance) {
        if (instance == null || !CLASS_I_COMPUTER.isAssignableFrom(instance.getClass())) return;
        queueEvent(instance, EVENT_TERMINATE);
    }

    public static float distanceToComputer(int instanceId, ChunkCoordinates origin) {
        ChunkCoordinates coordinates = getInstanceLocation(instanceId);
        return coordinates != null ? MathHelper.sqrt_double(origin.getDistanceSquaredToChunkCoordinates(coordinates)) : 0f;
    }

    public static String getDescription(int instanceId) {
        if (!isRegisteredInstance(instanceId)) return "[missing]";

        Object computer = computerRegistry.get(instanceId);
        try {
            Method getIdMethod = CLASS_I_COMPUTER.getMethod("getID");
            Method getLabelMethod = CLASS_I_COMPUTER.getMethod("getLabel");
            int id = (int) getIdMethod.invoke(computer);
            String label = (String) getLabelMethod.invoke(computer);

            if (Strings.isNullOrEmpty(label)) {
                return String.format("#%d", id);
            }
            return String.format("%s (#%d)", label, id);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return EnumChatFormatting.OBFUSCATED + "[error]";
    }

    private static boolean instanceOf(Class<?> tile, Class<?> other) {
        return other != null && other.isAssignableFrom(tile);
    }

    static {
        try {
            final Class<?> computerCraft = ReflectionUtils.getClass("dan200.computercraft.ComputerCraft");
            final Field registryField = computerCraft.getDeclaredField("serverComputerRegistry");
            final ModContainer container = FMLCommonHandler.instance().findContainerFor(Mods.COMPUTERCRAFT);
            final Object registryObject = registryField.get(container);
            final Field computerField = registryObject.getClass().getSuperclass().getDeclaredField("m_computers");
            computerField.setAccessible(true);
            computerRegistry = (Map) computerField.get(registryObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
