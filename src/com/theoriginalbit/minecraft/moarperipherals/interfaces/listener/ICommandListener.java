package com.theoriginalbit.minecraft.moarperipherals.interfaces.listener;

import net.minecraft.entity.player.EntityPlayer;

/**
 * definition for your class to listen to the player chat
 * 
 * @author theoriginalbit
 */
public interface ICommandListener {
	/**
	 * Returns the command identifier, for example minecraft uses / you could
	 * define yours to use $$
	 */
	public String getToken();

	/**
	 * Invoked when the start of a players message matches your defined token
	 * 
	 * @param message	the message that was sent
	 * @param player	the player that sent the message
	 */
	public void onServerChatEvent(String message, EntityPlayer player);
}
