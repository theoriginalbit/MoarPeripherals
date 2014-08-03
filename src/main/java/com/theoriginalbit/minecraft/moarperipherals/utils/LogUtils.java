package com.theoriginalbit.minecraft.moarperipherals.utils;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import cpw.mods.fml.common.FMLLog;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public final class LogUtils {

    private static Logger logger;

    public static void init() {
        logger = Logger.getLogger(ModInfo.NAME);
        logger.setParent(FMLLog.getLogger());
        logger.info("");
        logger.info("Starting MoarPeripherals v" + ModInfo.VERSION);
        logger.info("Copyright (c) Joshua Asbury (@theoriginalbit), 2014");
        logger.info("http://wiki.theoriginalbit.com/moarperipherals/");
        logger.info("");
    }

    public static void info(String message) {
        logger.log(Level.INFO, message);
    }

    public static void warning(String message) {
        logger.log(Level.WARNING, message);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, message);
    }

}
