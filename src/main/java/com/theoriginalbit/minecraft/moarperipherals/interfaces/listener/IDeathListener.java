package com.theoriginalbit.minecraft.moarperipherals.interfaces.listener;

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
