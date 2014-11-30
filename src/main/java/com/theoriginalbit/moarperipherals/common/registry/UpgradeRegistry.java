/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.registry;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.upgrade.*;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.turtle.ITurtleUpgrade;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public class UpgradeRegistry {

    public static final UpgradeRegistry INSTANCE = new UpgradeRegistry();
    public static final ArrayList<ITurtleUpgrade> UPGRADES = Lists.newArrayList();

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
        if (ConfigHandler.enableUpgradeShears) {
            register(new UpgradeShears());
        }
        if (ConfigHandler.enableUpgradeCompass) {
            register(new UpgradeCompass());
        }
        if (ConfigHandler.enableUpgradeSolar) {
            register(new UpgradeSolar());
        }
        if (ConfigHandler.enableUpgradeIgniter) {
            register(new UpgradeIgniter());
        }
        if (ConfigHandler.enableUpgradeFurnace) {
            register(new UpgradeFurnace());
        }
        if (ConfigHandler.enableUpgradeFeeder) {
            register(new UpgradeFeeder());
        }
        if (ConfigHandler.enableUpgradeOreScanner) {
            register(new UpgradeDensityScanner());
        }
    }

    private static void register(ITurtleUpgrade upgrade) {
        ComputerCraftAPI.registerTurtleUpgrade(upgrade);
        UPGRADES.add(upgrade);
    }

}
