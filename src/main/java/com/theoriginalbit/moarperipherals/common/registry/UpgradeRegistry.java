/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.theoriginalbit.moarperipherals.client.render.Icons;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.upgrades.*;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.turtle.ITurtleUpgrade;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class UpgradeRegistry {

    public static final UpgradeRegistry INSTANCE = new UpgradeRegistry();

    private UpgradeRegistry() {
        // prevent other instances being constructed
    }

    public final void register() {
        if (ConfigHandler.enableChatBox) {
            register(new UpgradeChatBox());
        }
        if (ConfigHandler.enableIronNote) {
            register(new UpgradeIronNote());
        }
        if (ConfigHandler.enableDictionary) {
            register(new UpgradeDictionary());
        }
        if (ConfigHandler.enableUpgradeShears) {
            register(new UpgradeShears());
        }
        if (ConfigHandler.enableUpgradeCompass) {
            register(new UpgradeCompass());
        }
    }

    private static void register(ITurtleUpgrade upgrade) {
        ComputerCraftAPI.registerTurtleUpgrade(upgrade);
        Icons.instance.registerUpgrade(upgrade);
    }

}
