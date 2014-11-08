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
package com.theoriginalbit.moarperipherals.common.utils;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

@SuppressWarnings("unused")
public final class LogUtils {

    public static void init() {
        info("");
        info("Starting MoarPeripherals v" + ModInfo.VERSION);
        info("Copyright (c) Joshua Asbury (@theoriginalbit), 2014");
        info("Model assets copyright (c) Dog a.k.a HydrantHunter");
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
        if (ConfigHandler.debug) {
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
