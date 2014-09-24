/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;

public final class ChatUtils {

    public static void sendChatToPlayer(String[] to, String message) {
        message = ChatAllowedCharacters.filerAllowedCharacters(message);
        final ChatComponentText msg = new ChatComponentText(message);
        for (String user : to) {
            final EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(user);
            if (player != null) {
                player.addChatComponentMessage(msg);
            }
        }
    }

    public static void sendChatToPlayer(String to, String message) {
        sendChatToPlayer(new String[]{to}, message);
    }

}