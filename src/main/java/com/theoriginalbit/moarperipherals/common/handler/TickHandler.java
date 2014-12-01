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
package com.theoriginalbit.moarperipherals.common.handler;

import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import java.util.EnumSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author theoriginalbit
 */
public class TickHandler implements ITickHandler {
    public static final TickHandler INSTANCE = new TickHandler();
    private static final ConcurrentLinkedQueue<FutureTask> callbacks = new ConcurrentLinkedQueue<FutureTask>();

    @Override
    public void tickStart(EnumSet<TickType> enumSet, Object... objects) {
        // NO-OP
    }

    @Override
    public void tickEnd(EnumSet<TickType> enumSet, Object... objects) {
        FutureTask callback;
        while ((callback = callbacks.poll()) != null) {
            callback.run();
        }
    }

    public static <T> Future<T> addTickCallback(Callable<T> callback) {
        FutureTask<T> task = new FutureTask<T>(callback) {
            @Override
            protected void done() {
                try {
                    if (!isCancelled()) get();
                } catch (Throwable t) {
                    LogUtils.warn("Exception while executing callback! " + this);
                    t.printStackTrace();
                }
            }
        };
        callbacks.add(task);
        return task;
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel() {
        return "MoarPeripheral|TickHandler";
    }
}
