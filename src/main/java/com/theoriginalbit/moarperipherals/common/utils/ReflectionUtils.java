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

import java.lang.reflect.Method;

public final class ReflectionUtils {

    public static Class<?> getClass(String className) {
        if (className == null || className.isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className);
        } catch (Exception ignored) {
        }
        return null;
    }

    public static Method getMethod(Class<?> instance, String method, Class<?>... parameters) {
        if (instance != null && method != null) {
            try {
                return instance.getMethod(method, parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Object callMethod(Object instance, Method method, Object... parameters) {
        if (method != null) {
            try {
                return method.invoke(instance, parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}