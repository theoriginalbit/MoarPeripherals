/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.reference;

public final class ModInfo {

    public static final String ID = "MoarPeripherals";
    public static final String NAME = ID;
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL = ID.toLowerCase();
    public static final String RESOURCE_DOMAIN = ID.toLowerCase();
    public static final String CONFIG_DOMAIN = RESOURCE_DOMAIN + ".config.";

    private static final String COM_PACKAGE = "com.theoriginalbit";
    private static final String MOD_PACKAGE = COM_PACKAGE + ".moarperipherals";

    public static final String GUI_FACTORY = MOD_PACKAGE + ".common.config.ConfigFactoryMoarP";
    public static final String PROXY_CLIENT = MOD_PACKAGE + ".client.ProxyClient";
    public static final String PROXY_SERVER = MOD_PACKAGE + ".common.ProxyCommon";

    public static final String DEPENDENCIES = "required-after:ComputerCraft@1.64,);";

}