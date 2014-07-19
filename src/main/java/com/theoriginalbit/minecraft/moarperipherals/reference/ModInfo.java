package com.theoriginalbit.minecraft.moarperipherals.reference;

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