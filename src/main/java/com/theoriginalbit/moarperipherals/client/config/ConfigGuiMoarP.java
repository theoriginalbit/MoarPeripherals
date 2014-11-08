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
package com.theoriginalbit.moarperipherals.client.config;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

import static com.theoriginalbit.moarperipherals.common.config.ConfigHandler.*;

/**
 * @author theoriginalbit
 * @since 4/10/2014
 */
public class ConfigGuiMoarP extends GuiConfig {

    private static final String LANG_DOMAIN = ModInfo.CONFIG_DOMAIN + "category.";

    public ConfigGuiMoarP(final GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), ModInfo.ID, false, false, StatCollector.translateToLocal(ModInfo.CONFIG_DOMAIN + "title"));
    }

    private static List<IConfigElement> getConfigElements() {
        final ArrayList<IConfigElement> list = Lists.newArrayList();

        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_ANTENNA)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_CHAT_BOX)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_DICTIONARY)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_IRON_NOTE)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_KEYBOARD)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_PLAYER_DETECTOR)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_PRINTER)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_RENDERER)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_SECURITY)));
        list.add(new ConfigElement<ConfigCategory>(getCategory(CATEGORY_SONIC)));

        return list;
    }

    private static ConfigCategory getCategory(String name) {
        name = name.toLowerCase();
        return config.getCategory(name).setLanguageKey(LANG_DOMAIN + name.replace(" ", ""));
    }

}
