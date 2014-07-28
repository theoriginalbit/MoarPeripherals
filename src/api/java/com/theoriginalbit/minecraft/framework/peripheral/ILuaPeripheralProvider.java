package com.theoriginalbit.minecraft.framework.peripheral;

/**
 * Peripheral Framework is an open-source framework that has the aim of
 * allowing developers to implement their ComputerCraft peripherals faster,
 * easier, and cleaner; allowing them to focus more on developing their
 * content.
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

/**
 * In the event that you don't want to have your peripheral implementation
 * in your TileEntity you can implement this interface and return an object
 * that is an instance of your peripheral implementation.
 *
 * See the example program for usage
 *
 * @author theoriginalbit
 */
public interface ILuaPeripheralProvider {

    /**
     * @return an instance of an object which is annotated with
     * LuaPeripheral and pertains to your TileEntity
     */
    public Object getPeripheral();

}
