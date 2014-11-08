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
