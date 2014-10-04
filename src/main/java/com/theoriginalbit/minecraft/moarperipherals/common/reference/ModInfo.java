/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.reference;

public final class ModInfo {

    public static final String ID = "MoarPeripherals";
    public static final String NAME = ID;
    public static final String VERSION = "@VERSION@";
    public static final String RESOURCE_DOMAIN = ID.toLowerCase();
    public static final String PROXY_CLIENT = "com.theoriginalbit.minecraft.moarperipherals.client.ProxyClient";
    public static final String PROXY_SERVER = "com.theoriginalbit.minecraft.moarperipherals.common.ProxyCommon";
    public static final String DEPENDENCIES = "required-after:ComputerCraft;after:CCTurtle;";
    public static final boolean REQUIRED_CLIENT = true;
    public static final boolean REQUIRED_SERVER = false;

}