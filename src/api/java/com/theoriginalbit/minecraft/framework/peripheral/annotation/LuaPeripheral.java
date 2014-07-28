package com.theoriginalbit.minecraft.framework.peripheral.annotation;

import com.theoriginalbit.minecraft.framework.peripheral.interfaces.IPFMount;
import dan200.computercraft.api.filesystem.IMount;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

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
 * This annotation indicates that you would like your TileEntity to be
 * wrapped as a peripheral. The "value" argument is required as it
 * specifies the type of the peripheral when a player makes a call to
 * peripheral.getType() in-game.
 *
 * @author theoriginalbit
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaPeripheral {

    public String value();

    public Class<? extends IPFMount>[] mounts() default {};

}
