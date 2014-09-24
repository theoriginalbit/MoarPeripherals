/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api.listener;

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
     * @param event the information about the chat event
     */
    public void onServerChatEvent(ServerChatEvent event);

}
