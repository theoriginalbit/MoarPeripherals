/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.handler;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.utils.LogUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author theoriginalbit
 * @since 15/10/2014
 */
public class TickHandler {

    public static final TickHandler INSTANCE = new TickHandler();
    private static final ConcurrentLinkedQueue<FutureTask> callbacks = new ConcurrentLinkedQueue<FutureTask>();

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.type == TickEvent.Type.SERVER) {
            FutureTask callback;
            while ((callback = callbacks.poll()) != null) {
                LogUtils.info("Callbacks exist");
                callback.run();
            }
        }
    }

    public static <T> Future<T> addTickCallback(Callable<T> callback) {
        FutureTask<T> task = new FutureTask<T>(callback) {
            @Override
            protected void done() {
                try {
                    LogUtils.info("Running callback");
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

    /*
     * Enabled when anything that uses it is needed.
     */
    public static boolean shouldRegister() {
        return ConfigHandler.enableFireworkLauncher;
    }
}
