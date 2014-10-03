package com.theoriginalbit.moarperipherals.client.config;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author theoriginalbit
 * @since 4/10/2014
 */
public class ConfigGuiMoarP extends GuiConfig {

    public ConfigGuiMoarP(final GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), ModInfo.ID, false, false, "MoarPeripherals Config");
    }

    private static List<IConfigElement> getConfigElements() {
        final ArrayList<IConfigElement> list = Lists.newArrayList();

        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_ANTENNA)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_CHAT_BOX)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_DICTIONARY)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_IRON_NOTE)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_KEYBOARD)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_PLAYER_DETECTOR)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_PRINTER)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_RENDERER)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_SECURITY)));
        list.add(new ConfigElement<ConfigCategory>(ConfigHandler.config.getCategory(ConfigHandler.CATEGORY_SONIC)));

        return list;
    }

}
