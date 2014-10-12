/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.api.listener;

/**
 * definition for your class to listen to player join/leave message
 *
 * @author theoriginalbit
 * @since 12/10/2014
 */
public interface IPlayerEventListener {
    /**
     * Invoked when a player joins the server
     *
     * @param username the player that joined
     */
    public void onPlayerJoin(String username);

    /**
     * Invoked when a player leaves the server
     *
     * @param username the player that left
     */
    public void onPlayerLeave(String username);

}
