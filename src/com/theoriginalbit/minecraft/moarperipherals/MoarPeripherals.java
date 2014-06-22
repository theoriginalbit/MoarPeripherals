package com.theoriginalbit.minecraft.moarperipherals;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = ModInfo.ID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = ModInfo.DEPENDENCIES)
@NetworkMod(clientSideRequired = ModInfo.REQUIRED_CLIENT, serverSideRequired = ModInfo.REQUIRED_SERVER)
public class MoarPeripherals {

}
