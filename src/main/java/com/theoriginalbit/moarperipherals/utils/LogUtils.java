/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.utils;

import com.theoriginalbit.moarperipherals.reference.ModInfo;
import com.theoriginalbit.moarperipherals.reference.Settings;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class LogUtils {

    public static void init() {
        info("");
        info("Starting MoarPeripherals v" + ModInfo.VERSION);
        info("Copyright (c) Joshua Asbury (@theoriginalbit), 2014");
        info("http://wiki.theoriginalbit.com/moarperipherals/");
        info("");
    }

    public static void log(Level logLevel, Object object) {
        FMLLog.log(ModInfo.NAME, logLevel, String.valueOf(object));
    }

    public static void all(Object object) {
        log(Level.ALL, object);
    }

    public static void debug(Object object) {
        if (Settings.debug) {
            log(Level.DEBUG, object);
        }
    }

    public static void error(Object object) {
        log(Level.ERROR, object);
    }

    public static void fatal(Object object) {
        log(Level.FATAL, object);
    }

    public static void info(Object object) {
        log(Level.INFO, object);
    }

    public static void off(Object object) {
        log(Level.OFF, object);
    }

    public static void trace(Object object) {
        log(Level.TRACE, object);
    }

    public static void warn(Object object) {
        log(Level.WARN, object);
    }

}
