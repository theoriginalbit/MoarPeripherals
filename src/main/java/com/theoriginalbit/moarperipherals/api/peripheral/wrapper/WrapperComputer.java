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
package com.theoriginalbit.moarperipherals.api.peripheral.wrapper;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.api.peripheral.interfaces.IPFMount;
import dan200.computercraft.api.peripheral.IComputerAccess;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * This wraps the object annotated with LuaPeripheral that is supplied to
 * it from the Peripheral Provider, it will then wrap any methods annotated
 * with LuaFunction and retain references of methods annotated with OnAttach
 * and OnDetach so that your peripheral will function with ComputerCraft's
 * expected IPeripheral interface.
 * <p/>
 * IMPORTANT:
 * This is a backend class, you should never need to use this, and
 * modifying this may have unexpected results.
 *
 * @author theoriginalbit
 */
public class WrapperComputer extends WrapperGeneric {
    private final ArrayList<IPFMount> mounts = Lists.newArrayList();

    public WrapperComputer(Object peripheral) {
        super(peripheral);

        final Class<?> peripheralClass = peripheral.getClass();
        final LuaPeripheral peripheralLua = peripheralClass.getAnnotation(LuaPeripheral.class);

        // Build the specified mount classes
        for (Class<? extends IPFMount> clazz : peripheralLua.mounts()) {
            IPFMount mount;
            try {
                mount = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            mounts.add(mount);
        }
    }

    @Override
    public void attach(IComputerAccess computer) {
        super.attach(computer);

        // mount anything required to this computer
        if (mounts.isEmpty()) {
            return;
        }

        if (methodAttach != null) {
            try {
                methodAttach.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (IPFMount mount : mounts) {
            computer.mount(mount.getMountLocation(), mount);
        }
    }

    @Override
    public void detach(IComputerAccess computer) {
        super.detach(computer);

        // un-mount anything required to this computer
        if (mounts.isEmpty()) {
            return;
        }

        if (methodDetach != null) {
            try {
                methodDetach.invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        for (IPFMount mount : mounts) {
            computer.unmount(mount.getMountLocation());
        }
    }

}
