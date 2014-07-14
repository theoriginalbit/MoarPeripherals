package com.theoriginalbit.minecraft.moarperipherals.tile;

import openperipheral.api.Ignore;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@Ignore
public class TileKeyboard extends TileEntity implements IActivateAwareTile {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard.png");
	public static final ResourceLocation TEXTURE_ON = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard_On.png");
	public static final ResourceLocation TEXTURE_LOST = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard_Lost.png");
	
	private Integer targetX, targetY, targetZ;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("targetX") && tag.hasKey("targetY") && tag.hasKey("targetZ")) {
        	targetX = tag.getInteger("targetX");
        	targetY = tag.getInteger("targetY");
        	targetZ = tag.getInteger("targetZ");
        }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound tag) {
    	super.writeToNBT(tag);
    	if (targetX != null && targetY != null && targetZ != null) {
    		tag.setInteger("targetX", targetX);
    		tag.setInteger("targetY", targetY);
    		tag.setInteger("targetZ", targetZ);
    	}
    }
    
	@Override
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ComputerUtils.turnOn(getTileComputerBase());
		return true;
	}
	
	public boolean hasConnection() {
		return ComputerUtils.getTileComputerBase(worldObj, targetX, targetY, targetZ) != null;
	}
	
	public boolean targetInRange() {
		if (targetX == null || targetY == null || targetZ == null) { return false; }
		return MathHelper.sqrt_double(getDistanceFrom(targetX, targetY, targetZ)) <= Settings.keyboardRange;
	}
	
	public ResourceLocation getTextureForRender() {
		if (hasConnection() && targetInRange()) {
			return TEXTURE_ON;
		} else if (hasConnection() && !targetInRange()) {
			return TEXTURE_LOST;
		}
		return TEXTURE;
	}

	public void configureTarget(int x, int y, int z) {
		targetX = x;
		targetY = y;
		targetZ = z;
	}
	
	public void terminateTarget() {
		if (targetInRange()) {
			ComputerUtils.terminate(getTileComputerBase());
		}
	}

	public void rebootTarget() {
		if (targetInRange()) {
			ComputerUtils.reboot(getTileComputerBase());
		}
	}

	public void shutdownTarget() {
		if (targetInRange()) {
			ComputerUtils.shutdown(getTileComputerBase());
		}
	}

	public void queueEventToTarget(String event, Object... args) {
		if (targetInRange()) {
			ComputerUtils.queueEvent(getTileComputerBase(), event, args);
		}
	}
	
	private final TileEntity getTileComputerBase() {
		return ComputerUtils.getTileComputerBase(worldObj, targetX, targetY, targetZ);
	}
}