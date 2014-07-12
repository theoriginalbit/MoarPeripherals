package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.keyboard.KeyboardConnection;
import com.theoriginalbit.minecraft.moarperipherals.keyboard.KeyboardEvent;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.PacketUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileKeyboard extends TileEntity implements IActivateAwareTile {
	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard.png");
	public static final ResourceLocation TEXTURE_ON = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard_On.png");
	public static final ResourceLocation TEXTURE_LOST = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard_Lost.png");

	private KeyboardConnection connection;

	public TileKeyboard() {
		connection = new KeyboardConnection(this);
	}

	@Override
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		PacketUtils.sendToServer(createEventPacket(KeyboardEvent.TURN_ON));
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		int targetX = tag.getInteger("targetX");
		int targetY = tag.getInteger("targetY");
		int targetZ = tag.getInteger("targetZ");
		
		System.out.println("Read NBT");

		connection.openConnection(targetX, targetY, targetZ);

		int dimId = worldObj.provider.dimensionId;
		PacketKeyboard packet = new PacketKeyboard(KeyboardEvent.SYNC);
		packet.intData = new int[] { dimId, xCoord, yCoord, zCoord, targetX, targetY, targetZ };
		PacketUtils.sendToAllPlayers(packet);
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		System.out.println("Write NBT");

		ChunkCoordinates coord = connection.getLocation();
		tag.setInteger("targetX", coord.posX);
		tag.setInteger("targetY", coord.posY);
		tag.setInteger("targetZ", coord.posZ);
	}
	
	public ResourceLocation getTexture() {
		boolean isConnected = connection.isConnected();
		boolean isValid = connection.isValidConnection();
		boolean inRange = isComputerInRange(connection.getLocation());

		if (isConnected && isValid && inRange) {
			return TEXTURE_ON;
		} else if (isConnected && !isValid) {
			return TEXTURE_LOST;
		}

		return TEXTURE;
	}
	
	public boolean hasConnection() {
		return connection.isConnected();
	}
	
	public boolean isConnectionValid() {
		return connection.isValidConnection();
	}
	
	public ChunkCoordinates getConnectionLocation() {
		return connection.getLocation();
	}
	
	public void closeConnection() {
		connection.closeConnection();
		syncToServer();
	}
	
	public void openConnection(int x, int y, int z) {
		connection.openConnection(x, y, z);
		syncToServer();
	}
	
	public final boolean isComputerInRange(ChunkCoordinates target) {
		double distance = getDistanceFrom(target.posX, target.posY, target.posZ);
		return MathHelper.sqrt_double(distance) <= Settings.keyboardRange;
	}
	
	// Packet handling methods
	
	public final void sync(Integer targetX, Integer targetY, Integer targetZ) {
		connection.openConnection(targetX, targetY, targetZ);
	}
	
	public void terminateTarget() {
		if (canSendPacket()) {
			PacketUtils.sendToServer(createEventPacket(KeyboardEvent.TERMINATE));
		}
	}

	public void rebootTarget() {
		if (canSendPacket()) {
			PacketUtils.sendToServer(createEventPacket(KeyboardEvent.REBOOT));
		}
	}

	public void shutdownTarget() {
		if (canSendPacket()) {
			PacketUtils.sendToServer(createEventPacket(KeyboardEvent.SHUTDOWN));
		}
	}

	public void onKeyPress(char ch, int keyCode) {
		if (canSendPacket()) {
			ChunkCoordinates coords = connection.getLocation();
			int dimId = worldObj.provider.dimensionId;
			PacketKeyboard packet = new PacketKeyboard(KeyboardEvent.KEY_PRESS);
			packet.intData = new int[] { dimId, coords.posX, coords.posY, coords.posZ, keyCode };
			packet.charData = new char[] { ch };
			PacketUtils.sendToServer(packet);
		}
	}

	public void onPaste(String clipboard) {
		if (canSendPacket()) {
			PacketKeyboard packet = createEventPacket(KeyboardEvent.PASTE);
			packet.stringData = new String[]{ clipboard };
			PacketUtils.sendToServer(packet);
		}
	}
	
	private boolean canSendPacket() {
		return connection.isConnected() && connection.isValidConnection() && isComputerInRange(connection.getLocation());
	}
	
	private final void syncToServer() {
		ChunkCoordinates coords = connection.getLocation();
		int dimId = worldObj.provider.dimensionId;
		PacketKeyboard packet = new PacketKeyboard(KeyboardEvent.SYNC);
		packet.intData = new int[] { dimId, xCoord, yCoord, zCoord, coords.posX, coords.posY, coords.posZ };
		PacketUtils.sendToServer(packet);
	}
	
	private final PacketKeyboard createEventPacket(KeyboardEvent event) {
		ChunkCoordinates coords = connection.getLocation();
		int dimId = worldObj.provider.dimensionId;
		PacketKeyboard packet = new PacketKeyboard(event);
		packet.intData = new int[] { dimId, coords.posX, coords.posY, coords.posZ };
		return packet;
	}
}