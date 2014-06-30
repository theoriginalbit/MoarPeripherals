package com.theoriginalbit.minecraft.moarperipherals.interfaces.listener;

import net.minecraftforge.event.entity.living.LivingDeathEvent;

public interface IDeathListener {
	public void onDeathEvent(LivingDeathEvent event);
}
