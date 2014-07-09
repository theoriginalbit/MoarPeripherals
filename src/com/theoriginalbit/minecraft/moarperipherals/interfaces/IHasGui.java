package com.theoriginalbit.minecraft.moarperipherals.interfaces;

import com.theoriginalbit.minecraft.moarperipherals.handler.GuiHandler.Gui;

import net.minecraft.tileentity.TileEntity;

/**
 * Register that your {@link TileEntity} has a GUI
 * 
 * @author theoriginalbit
 */
public interface IHasGui {
	/**
	 * Returns the ID of the GUI
	 */
	public Gui getGuiId();
}