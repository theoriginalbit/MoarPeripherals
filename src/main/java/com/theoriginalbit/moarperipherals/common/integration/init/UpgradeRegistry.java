/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.integration.init;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.integration.upgrade.*;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.turtle.ITurtleUpgrade;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
public final class UpgradeRegistry {
    public static final ArrayList<ITurtleUpgrade> UPGRADES = Lists.newArrayList();

    private UpgradeRegistry() {
        // prevent instances
    }

    public static void register() {
        if (ConfigData.enableChatBox) {
            register(new UpgradeChatBox());
        }
        if (ConfigData.enableIronNote) {
            register(new UpgradeIronNote());
        }
        if (ConfigData.enableUpgradeShears) {
            register(new UpgradeShears());
        }
        if (ConfigData.enableUpgradeCompass) {
            register(new UpgradeCompass());
        }
        if (ConfigData.enableUpgradeSolar) {
            register(new UpgradeSolar());
        }
        if (ConfigData.enableUpgradeIgniter) {
            register(new UpgradeIgniter());
        }
        if (ConfigData.enableUpgradeFurnace) {
            register(new UpgradeFurnace());
        }
        if (ConfigData.enableUpgradeFeeder) {
            register(new UpgradeFeeder());
        }
        if (ConfigData.enableUpgradeOreScanner) {
            register(new UpgradeDensityScanner());
        }
    }

    private static void register(ITurtleUpgrade upgrade) {
        ComputerCraftAPI.registerTurtleUpgrade(upgrade);
        UPGRADES.add(upgrade);
    }
}
