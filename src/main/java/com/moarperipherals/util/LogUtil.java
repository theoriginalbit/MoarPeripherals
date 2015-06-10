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

import com.moarperipherals.config.ConfigData;
import com.moarperipherals.ModInfo;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

@SuppressWarnings("unused")
public final class LogUtil {

    public static void init() {
        info("");
        info("Starting MoarPeripherals v" + ModInfo.VERSION);
        info("Copyright (c) Joshua Asbury (@theoriginalbit), 2014");
        info("Model assets copyright (c) Dog a.k.a HydrantHunter");
        info("http://moarperipherals.com");
        info("");
    }

    public static void log(Level logLevel, String message, Object... args) {
        FMLLog.log(ModInfo.NAME, logLevel, " " + message, args);
    }

    public static void debug(String message, Object... args) {
        if (ConfigData.debug) {
            log(Level.INFO, message, args);
        }
    }

    public static void info(String message, Object... args) {
        log(Level.INFO, message, args);
    }

    public static void warn(String message, Object... args) {
        log(Level.WARN, message, args);
    }

    public static void error(String message, Object... args) {
        log(Level.ERROR, message, args);
    }

    public static void fatal(String message, Object... args) {
        log(Level.FATAL, message, args);
    }

}
