package com.theoriginalbit.minecraft.moarperipherals.reference.lookup;

import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;

import net.minecraft.util.ResourceLocation;

public enum ModelTextures {
	KEYBOARD("textures/models/blocks/keyboard/Keyboard"),
	KEYBOARD_ON("textures/models/blocks/keyboard/Keyboard_On"),
	KEYBOARD_LOST("textures/models/blocks/keyboard/Keyboard_Lost"),
	PRINTER("textures/models/blocks/printer/Printer_Printing"),
	PRINTER_IDLE("textures/models/blocks/printer/Printer_Idle"),
	PRINTER_ERROR("textures/models/blocks/printer/Printer_Error"),
	PRINTER_PRINT_ERROR("textures/models/blocks/printer/Printer_Printing_Error"),
	INK_CARTRIDGE("textures/models/items/inkCartridge/InkCartridge%s", false);
	
	private final String pathToResource;
	private final ResourceLocation resource;
	
	private ModelTextures(String path) {
		this(path, true);
	}
	
	private ModelTextures(String path, boolean load) {
		pathToResource = path + ".png";
		resource = load ? new ResourceLocation(ModInfo.RESOURCE_DOMAIN, pathToResource) : null;
	}
	
	public final ResourceLocation getTexture() {
		return resource;
	}
	
	public final String getPath() {
		return pathToResource;
	}
}