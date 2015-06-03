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
package com.moarperipherals.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.lang.reflect.Field;

public final class Config {

    public static void init(File file) {
        Configuration config = new Configuration(file);

        // read the config into the properties
        for (Field field : ConfigData.class.getFields()) {
            if (field.isAnnotationPresent(ConfigPropertyBoolean.class)) {
                final ConfigPropertyBoolean details = field.getAnnotation(ConfigPropertyBoolean.class);
                Property property = config.get(details.category(), field.getName(), details.value(), details.comment());
                trySet(field, property.getBoolean());
            } else if (field.isAnnotationPresent(ConfigPropertyInteger.class)) {
                final ConfigPropertyInteger details = field.getAnnotation(ConfigPropertyInteger.class);
                Property property = config.get(details.category(), field.getName(), details.value(), details.comment());
                // make sure it is in range
                int val = Math.max(Math.min(property.getInt(), details.maxValue()), details.minValue());
                trySet(field, val);
                property.set(val);
            } else if (field.isAnnotationPresent(ConfigPropertyDouble.class)) {
                final ConfigPropertyDouble details = field.getAnnotation(ConfigPropertyDouble.class);
                Property property = config.get(details.category(), field.getName(), details.value(), details.comment());
                // make sure it is in range
                double val = Math.max(Math.min(property.getDouble(), details.maxValue()), details.minValue());
                trySet(field, val);
                property.set(val);
            } else if (field.isAnnotationPresent(ConfigPropertyString.class)) {
                final ConfigPropertyString details = field.getAnnotation(ConfigPropertyString.class);
                Property property = config.get(details.category(), field.getName(), details.value(), details.comment());
                trySet(field, property.getString());
            }
        }

        // validate what the user has input
        ConfigData.userDensityMappings = ConfigData.userDensityMappings.trim();
        if (ConfigData.userDensityMappings.toLowerCase().contains("minecraft:")) {
            throw new RuntimeException("Minecraft blocks cannot have a custom density mapping");
        }

        if (config.hasChanged()) {
            config.save();
        }
    }

    private static void trySet(Field field, Object value) {
        try {
            field.set(null, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}