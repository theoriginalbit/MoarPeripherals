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
package com.theoriginalbit.moarperipherals.common.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.moarperipherals.common.reference.Mods;
import com.theoriginalbit.moarperipherals.common.util.LogUtil;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public final class ChatBoxHandler {
    public static final ChatBoxHandler instance = new ChatBoxHandler();

    public static void init() {
        if (Loader.isModLoaded(Mods.OPENPERIPHERALADDON)) {
            LogUtil.info("Detected OpenPeripheral-Addons installed. Registering the terminal glasses command as a " +
                    "ChatBox command so it is ignored by ChatBoxes.");
            instance.commandBlacklist.add("$$");
        }
    }

    private final ArrayList<IChatHook> chatListeners = Lists.newArrayList();
    private final ArrayList<IDeathHook> deathListeners = Lists.newArrayList();
    private final ArrayList<IPlayerEventHook> playerListeners = Lists.newArrayList();
    private final ArrayList<String> commandBlacklist = Lists.newArrayList();
    private final HashMap<String, ArrayList<ICommandHook>> commandListeners = Maps.newHashMap();

    public void addChatHook(IChatHook listener) {
        synchronized (chatListeners) {
            if (!chatListeners.contains(listener)) {
                chatListeners.add(listener);
            }
        }
    }

    public void removeChatHook(IChatHook listener) {
        synchronized (chatListeners) {
            if (chatListeners.contains(listener)) {
                chatListeners.remove(listener);
            }
        }
    }

    public void addDeathHook(IDeathHook listener) {
        synchronized (deathListeners) {
            if (!deathListeners.contains(listener)) {
                deathListeners.add(listener);
            }
        }
    }

    public void removeDeathHook(IDeathHook listener) {
        synchronized (deathListeners) {
            if (deathListeners.contains(listener)) {
                deathListeners.remove(listener);
            }
        }
    }

    public void addPlayerEventHook(IPlayerEventHook listener) {
        synchronized (playerListeners) {
            if (!playerListeners.contains(listener)) {
                playerListeners.add(listener);
            }
        }
    }

    public void removePlayerEventHook(IPlayerEventHook listener) {
        synchronized (playerListeners) {
            if (playerListeners.contains(listener)) {
                playerListeners.remove(listener);
            }
        }
    }

    public void addCommandHook(ICommandHook listener) throws Exception {
        final String token = listener.getToken();

        synchronized (commandListeners) {
            if (!commandListeners.containsKey(token)) {
                commandListeners.put(token, new ArrayList<ICommandHook>());
            }
            commandListeners.get(token).add(listener);
        }
    }

    public void removeCommandHook(ICommandHook listener) {
        final String token = listener.getToken();

        synchronized (commandListeners) {
            if (commandListeners.containsKey(token)) {
                commandListeners.get(token).remove(listener);
            }
        }
    }

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        // lets just ignore canceled events
        if (event.isCanceled()) {
            return;
        }

        // make sure we ignore any chat messages that we don't want visible to the ChatBoxes
        for (String token : commandBlacklist) {
            if (event.message.startsWith(token)) {
                return;
            }
        }

        // check if it is a registered command, if it is, chat listeners shouldn't get this!
        synchronized (commandListeners) {
            for (Entry<String, ArrayList<ICommandHook>> entry : commandListeners.entrySet()) {
                final String token = entry.getKey();
                if (event.message.startsWith(token)) {
                    for (ICommandHook listener : entry.getValue()) {
                        listener.onServerChatEvent(event.message.substring(token.length()).trim(), event.player);
                    }
                    event.setCanceled(true);
                    return;
                }
            }
        }

        // it wasn't a command, IChatListeners can have it now
        synchronized (chatListeners) {
            for (IChatHook listener : chatListeners) {
                listener.onServerChatEvent(event);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        synchronized (deathListeners) {
            for (IDeathHook listener : deathListeners) {
                listener.onDeathEvent(event);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        synchronized (playerListeners) {
            for (IPlayerEventHook listener : playerListeners) {
                listener.onPlayerJoin(event.player.getDisplayName());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent event) {
        synchronized (playerListeners) {
            for (IPlayerEventHook listener : playerListeners) {
                listener.onPlayerLeave(event.player.getDisplayName());
            }
        }
    }

}