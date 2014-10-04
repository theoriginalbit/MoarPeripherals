/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.utils;

import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.common.FMLLog;

import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void debug(String message, Object... args) {
        if (ConfigHandler.debug) {
            info(String.format(message, args));
        }
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
