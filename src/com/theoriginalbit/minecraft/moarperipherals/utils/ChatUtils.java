package com.theoriginalbit.minecraft.moarperipherals.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class ChatUtils {
	private static final char[] DISALLOWED = new char[] { '\u00a7', (char) 0x03, (char) 0x02, (char) 0x1D, (char) 0x1F, (char) 0x16, '\n', '\r', '\b', '\f', '\t' };
	
	private static String sanatise(String str) {
		// well-known client crash in FontRenderer ... apparently
		while (str.endsWith("\u00a7")) {
			str.substring(0, str.length() - 1);
		}
		
		for (int i = 0; i < DISALLOWED.length; ++i) {
			str = str.replace(DISALLOWED[i], ' ');
		}
		
		return str;
	}
	
	public static void sendChatToPlayer(String[] to, String message) {
		ChatMessageComponent msg = new ChatMessageComponent().addText(sanatise(message));
		for (String user : to) {
			EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(user);
			if (player != null) {
				player.sendChatToPlayer(msg);
			}
		}
	}
	
	public static void sendChatToPlayer(String to, String message) {
		sendChatToPlayer(new String[] { to }, message);
	}

}
