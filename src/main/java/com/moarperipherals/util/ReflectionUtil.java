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

import java.lang.reflect.Method;

public final class ReflectionUtil {
    public static Class<?> getClass(String className) {
        if (Strings.isNullOrEmpty(className)) return null;

        try {
            return Class.forName(className);
        } catch (Exception ignored) {
        }

        return null;
    }

    public static Method getMethod(Class<?> clazz, String method, Class<?>... parameters) {
        if (clazz == null || Strings.isNullOrEmpty(method)) return null;

        try {
            return clazz.getMethod(method, parameters);
        } catch (Exception ignored) {
        }

        return null;
    }

    public static Object callMethod(Object instance, Method method, Object... parameters) {
        if (method == null) return null;

        try {
            return method.invoke(instance, parameters);
        } catch (Exception ignored) {
        }
        return null;
    }

}