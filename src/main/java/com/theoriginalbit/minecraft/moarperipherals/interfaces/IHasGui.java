package com.theoriginalbit.minecraft.moarperipherals.interfaces;

import com.theoriginalbit.minecraft.moarperipherals.gui.GuiType;
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
    public GuiType getGuiId();

}