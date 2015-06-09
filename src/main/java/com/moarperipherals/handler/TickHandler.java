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
package com.moarperipherals.handler;

import com.moarperipherals.config.ConfigData;
import com.moarperipherals.util.LogUtil;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author theoriginalbit
 * @since 15/10/2014
 */
public class TickHandler {
    public static final TickHandler INSTANCE = new TickHandler();
    private static Map<Integer, LinkedBlockingQueue<FutureTask>> callbacks = Collections.synchronizedMap(
            new HashMap<Integer, LinkedBlockingQueue<FutureTask>>()
    );

    public static <T> Future<T> addTickCallback(World world, Callable<T> callback) {
        if (!callbacks.containsKey(world.provider.dimensionId))
            callbacks.put(world.provider.dimensionId, new LinkedBlockingQueue<FutureTask>());

        FutureTask<T> task = new FutureTask<T>(callback) {
            @Override
            protected void done() {
                try {
                    if (!isCancelled()) get();
                } catch (Throwable t) {
                    LogUtil.warn("Exception while executing callback! " + this);
                    t.printStackTrace();
                }
            }
        };

        try {
            callbacks.get(world.provider.dimensionId).put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return task;
    }

    /*
     * Enabled when anything that uses it is needed.
     */
    public static boolean shouldRegister() {
        return ConfigData.enableFireworkLauncher || ConfigData.enableInteractiveSorter;
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.WorldTickEvent event) {
        if (event.type == TickEvent.Type.WORLD) {
            if (callbacks.containsKey(event.world.provider.dimensionId)) {
                Queue<FutureTask> callbackList = callbacks.get(event.world.provider.dimensionId);
                FutureTask callback;
                while ((callback = callbackList.poll()) != null) {
                    callback.run();
                }
            }
        }
    }
}
