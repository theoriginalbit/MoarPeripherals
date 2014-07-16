package com.theoriginalbit.minecraft.moarperipherals.reference.lookup;

import net.minecraft.util.StatCollector;

public enum Tooltips {
	
	SHIFT_INFO("moarperipherals.tooltip.generic.information");
	
	private final String rawName;
	
	private Tooltips(String name) {
		rawName = name;
	}
	
	public String getLocalised() {
		return StatCollector.translateToLocal(rawName);
	}
	
	public String getFormattedLocalised(Object... args) {
		return StatCollector.translateToLocalFormatted(rawName, args);
	}
}