package com.theoriginalbit.minecraft.moarperipherals.interfaces.listener;

import net.minecraftforge.event.ServerChatEvent;

/**
 * definition for your class to listen to the player chat
 * 
 * @author theoriginalbit
 */
public interface IChatListener {
	/**
	 * Invoked when a player sends a chat message and it was not an
	 * {@link ICommandListener}
	 * 
	 * @param event 	the information about the chat event
	 */
	public void onServerChatEvent(ServerChatEvent event);
}
