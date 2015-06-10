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
package com.moarperipherals.tile.printer;

import com.moarperipherals.ModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public enum CartridgeContents {
    EMPTY,
    CYAN,
    MAGENTA,
    YELLOW,
    BLACK;

    private static final String TEXTURE_PATH = "%s:inkCartridge%c";
    private static final String TOOLTIP_PATH = "tooltip.moarperipherals.inkCartridge.ink.%s";
    private static final String MODEL_PATH = "%s:textures/models/items/inkCartridge/InkCartridge%c.png";

    private final String textureName;
    private final String tooltipName;
    private final ResourceLocation modelTexture;
    private IIcon icon;

    CartridgeContents() {
        char ch = name().charAt(0);
        textureName = String.format(TEXTURE_PATH, ModInfo.RESOURCE_DOMAIN, ch);
        tooltipName = String.format(TOOLTIP_PATH, name().toLowerCase());
        modelTexture = new ResourceLocation(String.format(MODEL_PATH, ModInfo.RESOURCE_DOMAIN, ch));
    }

    public static CartridgeContents valueOf(int index) {
        return values()[index];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        icon = registry.registerIcon(textureName);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        return icon;
    }

    @SideOnly(Side.CLIENT)
    public String getLocalisedName() {
        return StatCollector.translateToLocal(tooltipName);
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getTexture() {
        return modelTexture;
    }
}
