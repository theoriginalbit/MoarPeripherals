/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.client.render;

import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeIcon;
import com.theoriginalbit.moarperipherals.api.upgrade.IUpgradeToolIcon;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.util.List;

/**
 * @author theoriginalbit
 * @since 14/10/2014
 */
public class Icons {

    public static final Icons instance = new Icons();

    private final List<ITurtleUpgrade> UPGRADE_ICONS = Lists.newArrayList();

    public void registerUpgrade(ITurtleUpgrade icon) {
        UPGRADE_ICONS.add(icon);
    }

    @SubscribeEvent
    public void onPreTextureStitch(TextureStitchEvent.Pre event) {
        addTextures(event.map);
    }

    private void addTextures(TextureMap map) {
        boolean terrain = map.getTextureType() == 1;
        if (terrain) {
            for (ITurtleUpgrade upgrade : UPGRADE_ICONS) {
                if (upgrade instanceof IUpgradeIcon) {
                    ((IUpgradeIcon) upgrade).registerIcons(map);
                }
            }
        } else {
            for (ITurtleUpgrade upgrade : UPGRADE_ICONS) {
                if (upgrade instanceof IUpgradeToolIcon) {
                    ((IUpgradeToolIcon) upgrade).registerIcons(map);
                }
            }
        }
    }
}
