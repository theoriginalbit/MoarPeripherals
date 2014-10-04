/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.api.listener;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * definition for your class to listen to death messages
 *
 * @author theoriginalbit
 */
public interface IDeathListener {
    /**
     * Invoked when a player (or named entity) dies
     *
     * @param event the information about the death event
     */
    public void onDeathEvent(LivingDeathEvent event);

}
