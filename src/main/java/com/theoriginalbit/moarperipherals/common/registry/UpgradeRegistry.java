/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.upgrades.UpgradeChatBox;
import com.theoriginalbit.moarperipherals.common.upgrades.UpgradeDictionary;
import com.theoriginalbit.moarperipherals.common.upgrades.UpgradeIronNote;
import dan200.computercraft.api.ComputerCraftAPI;

public final class UpgradeRegistry {

    public static void init() {
        if (ConfigHandler.enableChatBox) {
            ComputerCraftAPI.registerTurtleUpgrade(new UpgradeChatBox());
        }

        if (ConfigHandler.enableIronNote) {
            ComputerCraftAPI.registerTurtleUpgrade(new UpgradeIronNote());
        }

        if (ConfigHandler.enableDictionary) {
            ComputerCraftAPI.registerTurtleUpgrade(new UpgradeDictionary());
        }
    }

}
