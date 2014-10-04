/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api.listener;

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
     * @param message the message that was sent
     * @param player  the player that sent the message
     */
    public void onServerChatEvent(String message, EntityPlayer player);

}
