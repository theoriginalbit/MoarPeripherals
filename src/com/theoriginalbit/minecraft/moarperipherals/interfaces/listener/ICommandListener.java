package com.theoriginalbit.minecraft.moarperipherals.interfaces.listener;

import net.minecraft.entity.player.EntityPlayer;

public interface ICommandListener {
	public String getToken();
	public boolean onServerChatEvent(String message, EntityPlayer player);
}
