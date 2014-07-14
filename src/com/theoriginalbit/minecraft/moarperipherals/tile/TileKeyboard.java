package com.theoriginalbit.minecraft.moarperipherals.tile;

import openperipheral.api.Ignore;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@Ignore
public class TileKeyboard extends TileEntity implements IActivateAwareTile {
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/models/Keyboard.png");
	public static final ResourceLocation TEXTURE_ON = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/models/Keyboard_On.png");
	public static final ResourceLocation TEXTURE_LOST = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "textures/models/Keyboard_Lost.png");
	
	private Integer targetX, targetY, targetZ;
	
	/**
	 * Read the target information from NBT
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        if (tag.hasKey("targetX") && tag.hasKey("targetY") && tag.hasKey("targetZ")) {
        	targetX = tag.getInteger("targetX");
        	targetY = tag.getInteger("targetY");
        	targetZ = tag.getInteger("targetZ");
        }
    }
	
	/**
	 * Write the target information to NBT
	 */
	@Override
    public void writeToNBT(NBTTagCompound tag) {
    	super.writeToNBT(tag);
    	if (targetX != null && targetY != null && targetZ != null) {
    		tag.setInteger("targetX", targetX);
    		tag.setInteger("targetY", targetY);
    		tag.setInteger("targetZ", targetZ);
    	}
    }
	
	/**
	 * If a player begins watching the chunk, send the target info for correct rendering
	 */
	@Override
	public Packet getDescriptionPacket() {
		if (targetX == null || targetY == null || targetZ == null) {
			return null;
		}
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("targetX", targetX);
		tag.setInteger("targetY", targetY);
		tag.setInteger("targetZ", targetZ);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, tag);
    }
	
	/**
	 * Recieve the target info packet
	 */
	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		NBTTagCompound tag = packet.data;
		targetX = tag.getInteger("targetX");
		targetY = tag.getInteger("targetY");
		targetZ = tag.getInteger("targetZ");
	}
    
	/**
	 * When the Keybaord is right-clicked, it shall turn on the target computer if it is not on
	 */
	@Override
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ComputerUtils.turnOn(getTileComputerBase());
		return true;
	}
	
	/**
	 * Whether the keyboard has a valid connection or not
	 */
	public final boolean hasConnection() {
		return ComputerUtils.getTileComputerBase(worldObj, targetX, targetY, targetZ) != null;
	}
	
	/**
	 * Used by the renderer to get which texture to display based on the connection status
	 */
	public final ResourceLocation getTextureForRender() {
		if (hasConnection() && targetInRange()) {
			return TEXTURE_ON;
		} else if (hasConnection() && !targetInRange()) {
			return TEXTURE_LOST;
		}
		return TEXTURE;
	}

	public final void configureTarget(int x, int y, int z) {
		targetX = x;
		targetY = y;
		targetZ = z;
	}
	
	public final void terminateTarget() {
		if (targetInRange()) {
			ComputerUtils.terminate(getTileComputerBase());
		}
	}

	public final void rebootTarget() {
		if (targetInRange()) {
			ComputerUtils.reboot(getTileComputerBase());
		}
	}

	public final void shutdownTarget() {
		if (targetInRange()) {
			ComputerUtils.shutdown(getTileComputerBase());
		}
	}

	public final void queueEventToTarget(String event, Object... args) {
		if (targetInRange()) {
			ComputerUtils.queueEvent(getTileComputerBase(), event, args);
		}
	}
	
	private final TileEntity getTileComputerBase() {
		return ComputerUtils.getTileComputerBase(worldObj, targetX, targetY, targetZ);
	}
	
	private final boolean targetInRange() {
		if (targetX == null || targetY == null || targetZ == null) { return false; }
		return MathHelper.sqrt_double(getDistanceFrom(targetX, targetY, targetZ)) <= Settings.keyboardRange;
	}
}