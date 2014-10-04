/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatMessageComponent;

public final class ChatUtils {

    public static void sendChatToPlayer(String[] to, String message) {
        message = ChatAllowedCharacters.filerAllowedCharacters(message);
        ChatMessageComponent msg = new ChatMessageComponent().addText(message);
        for (String user : to) {
            EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(user);
            if (player != null) {
                player.sendChatToPlayer(msg);
            }
        }
    }

    public static void sendChatToPlayer(String to, String message) {
        sendChatToPlayer(new String[]{to}, message);
    }

}