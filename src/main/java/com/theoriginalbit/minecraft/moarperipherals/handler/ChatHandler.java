package com.theoriginalbit.minecraft.moarperipherals.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.IChatListener;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.ICommandListener;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.IDeathListener;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
public final class ChatHandler {

    public static final ChatHandler instance = new ChatHandler();

    private final ArrayList<IChatListener> chatListeners = Lists.newArrayList();
    private final ArrayList<IDeathListener> deathListeners = Lists.newArrayList();
    private final HashMap<String, ArrayList<ICommandListener>> commandListeners = Maps.newHashMap();

    public void addChatListener(IChatListener listener) {
        if (!chatListeners.contains(listener)) {
            chatListeners.add(listener);
        }
    }

    public void removeChatListener(IChatListener listener) {
        if (chatListeners.contains(listener)) {
            chatListeners.remove(listener);
        }
    }

    public void addDeathListener(IDeathListener listener) {
        if (!deathListeners.contains(listener)) {
            deathListeners.add(listener);
        }
    }

    public void removeDeathListener(IDeathListener listener) {
        if (deathListeners.contains(listener)) {
            deathListeners.remove(listener);
        }
    }

    public void addCommandListener(ICommandListener listener) throws Exception {
        final String token = listener.getToken();

        if (!commandListeners.containsKey(token)) {
            commandListeners.put(token, new ArrayList<ICommandListener>());
        }
        commandListeners.get(token).add(listener);
    }

    public void removeCommandListener(ICommandListener listener) {
        final String token = listener.getToken();

        if (commandListeners.containsKey(token)) {
            commandListeners.get(token).remove(listener);
        }
    }

    @ForgeSubscribe
    @SuppressWarnings("unused")
    public void onServerChatEvent(ServerChatEvent event) {
        // lets just ignore canceled events
        if (event.isCanceled()) {
            return;
        }

        // check if it was a command first, if it is, chat listeners shouldn't get this!
        for (Entry<String, ArrayList<ICommandListener>> entry : commandListeners.entrySet()) {
            final String token = entry.getKey();
            final int tokenLength = token.length();
            if (event.message.substring(0, tokenLength).equals(token)) {
                for (ICommandListener listener : entry.getValue()) {
                    listener.onServerChatEvent(event.message.substring(tokenLength).trim(), event.player);
                    event.setCanceled(true);
                }
            }
        }

        // the event was once valid, but a ICommandListener used the event
        if (event.isCanceled()) {
            return;
        }

        // it wasn't a command, IChatListeners can have it now
        for (IChatListener listener : chatListeners) {
            listener.onServerChatEvent(event);
        }
    }

    @ForgeSubscribe
    @SuppressWarnings("unused")
    public void onLivingDeathEvent(LivingDeathEvent event) {
        for (IDeathListener listener : deathListeners) {
            listener.onDeathEvent(event);
        }
    }

}