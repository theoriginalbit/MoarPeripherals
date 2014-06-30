package com.theoriginalbit.minecraft.moarperipherals;

import java.util.ArrayList;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.*;

public final class ChatListener {
	public static final ChatListener instance = new ChatListener();
	
	private ArrayList<IChatListener> chatListeners = Lists.newArrayList();
	private ArrayList<IDeathListener> deathListeners = Lists.newArrayList();
	
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
	
	@ForgeSubscribe
	public void onServerChatEvent(ServerChatEvent event) {
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