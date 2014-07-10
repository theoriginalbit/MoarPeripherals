package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatMessageComponent;

public final class ChatUtils {
	public final static void sendChatToPlayer(String[] to, String message) {
		message = ChatAllowedCharacters.filerAllowedCharacters(message);
		ChatMessageComponent msg = new ChatMessageComponent().addText(message);
		for (String user : to) {
			EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(user);
			if (player != null) {
				player.sendChatToPlayer(msg);
			}
		}
	}
	
	public final static void sendChatToPlayer(String to, String message) {
		sendChatToPlayer(new String[] { to }, message);
	}
}