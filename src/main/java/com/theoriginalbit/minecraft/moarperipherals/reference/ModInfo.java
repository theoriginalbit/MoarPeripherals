package com.theoriginalbit.minecraft.moarperipherals.reference;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
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
public final class ModInfo {

    public static final String ID = "MoarPeripherals";
    public static final String NAME = ID;
    public static final String VERSION = "@VERSION@";
    public static final String RESOURCE_DOMAIN = ID.toLowerCase();
    public static final String PROXY_CLIENT = "com.theoriginalbit.minecraft.moarperipherals.proxy.ProxyClient";
    public static final String PROXY_SERVER = "com.theoriginalbit.minecraft.moarperipherals.proxy.ProxyServer";
    public static final String DEPENDENCIES = "required-after:ComputerCraft;after:CCTurtle;";
    public static final boolean REQUIRED_CLIENT = true;
    public static final boolean REQUIRED_SERVER = false;

}