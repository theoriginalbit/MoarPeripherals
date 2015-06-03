/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moarperipherals.tile;

import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.moarperipherals.client.gui.GuiType;
import com.moarperipherals.client.gui.IHasGui;
import com.moarperipherals.Constants;
import com.moarperipherals.tile.printer.PaperState;
import com.moarperipherals.tile.printer.PrinterState;

@LuaPeripheral("advanced_printer")
public class TilePrinter extends TileInventory implements IHasGui {
    private static final int[] SLOTS_BOTTOM = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static final int[] SLOTS_SIDE = {14};
    private static final int[] SLOTS_TOP = {0, 1, 2, 3};

    public TilePrinter() {
        super(15);
    }

    public PrinterState getPrinterState() {
        return PrinterState.IDLE;
    }

    public PaperState getPaperState() {
        if (hasPaperInput() && hasPaperOutput()) {
            return PaperState.PAPER_BOTH;
        } else if (hasPaperInput()) {
            return PaperState.PAPER_INPUT;
        } else if (hasPaperOutput()) {
            return PaperState.PAPER_OUTPUT;
        }
        return PaperState.PAPER_NONE;
    }

    @LuaFunction("hasPaper")
    public boolean hasPaperInput() {
        return true;
    }

    private boolean hasPaperOutput() {
        return true;
    }

    @Override
    public GuiType getGuiId() {
        return GuiType.PRINTER;
    }

    @Override
    public String getInventoryName() {
        return Constants.GUI.PRINTER.getLocalised();
    }

    @Override
    public int getInventoryStackLimit() {
        return 16;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        switch (side) {
            case 0:
                return SLOTS_BOTTOM;
            case 1:
                return SLOTS_TOP;
        }
        return SLOTS_SIDE;
    }
}