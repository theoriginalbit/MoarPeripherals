package com.theoriginalbit.minecraft.moarperipherals.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.*;

public final class ChatHandler {
	public static final ChatHandler instance = new ChatHandler();
	
	private final ArrayList<IChatListener> chatListeners = Lists.newArrayList();
	private final ArrayList<IDeathListener> deathListeners = Lists.newArrayList();
	private final HashMap<String, ICommandListener> commandListeners = Maps.newHashMap();
	
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
		
		if (commandListeners.containsKey(token)) {
			throw new Exception("ICommandListener already exists for the token " + token);
		}
		
		commandListeners.put(token, listener);
	}
	
	public void removeCommandListener(ICommandListener listener) {
		final String token = listener.getToken();
		
		if (commandListeners.containsKey(token)) {
			commandListeners.remove(token);
		}
	}
	
	@ForgeSubscribe
	public void onServerChatEvent(ServerChatEvent event) {
		// lets just ignore canceled events
		if (event.isCanceled()) {
			return;
		}
		
		// check if it was a command first, if it is, chat listeners shouldn't get this!
		for (Entry<String, ICommandListener> entry : commandListeners.entrySet()) {
			final String token = entry.getKey();
			final int tokenLength = token.length();
			if (event.message.substring(0, tokenLength).equals(token)) {
				entry.getValue().onServerChatEvent(event.message.substring(tokenLength).trim(), event.player);
				event.setCanceled(true);
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
	public void onLivingDeathEvent(LivingDeathEvent event) {
		for (IDeathListener listener : deathListeners) {
			listener.onDeathEvent(event);
		}
	}
}