package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.handler.TinyPacketHandler.PacketID;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileKeyboard extends TileEntity implements IActivateAwareTile {
	public static final ResourceLocation TEXTURE = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard.png");
	public static final ResourceLocation TEXTURE_ON = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard_On.png");
	public static final ResourceLocation TEXTURE_LOST = new ResourceLocation(ModInfo.RESOURCE_DOMAIN, "/textures/models/Keyboard_Lost.png");
	
	private KeyboardConnection connection = new KeyboardConnection();
	
	@Override
	public boolean onActivated(EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		sendPacketToServer(PacketID.KEYBOARD_TURNON);
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		int x = tag.getInteger("xPos");
		int y = tag.getInteger("yPos");
		int z = tag.getInteger("zPos");
		connection.newConnection(x, y, z);
		System.out.println("ReadNBT x=" + x + " y=" + y + " z=" + z);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		ChunkCoordinates coord = connection.getLocation();
		tag.setInteger("xPos", coord.posX);
		tag.setInteger("yPos", coord.posY);
		tag.setInteger("zPos", coord.posZ);
		System.out.println("WriteNBT x=" + coord.posX + " y=" + coord.posY + " z=" + coord.posZ);
	}
	
	public ResourceLocation getTexture() {
		boolean connected = connection.isConnected();
		boolean valid = connection.isValidConnection();
		boolean range = connection.inRange(xCoord, yCoord, zCoord);
		
		if (connected && valid && range) {
			return TEXTURE_ON;
		} else if (connected && !valid) {
			return TEXTURE_LOST;
		}
		return TEXTURE;
	}
	
	public void onKey(char character, int keyCode) {
		if (canSendPacket()) {
			ByteArrayDataOutput stream = ByteStreams.newDataOutput();
			stream.writeChar(character);
			stream.writeInt(keyCode);
			sendPacketToServer(PacketID.KEYBOARD_ONKEY, stream);
		}
	}
	
	public void terminateComputer() {
		if (canSendPacket()) {
			sendPacketToServer(PacketID.KEYBOARD_TERMINATE);
		}
	}
	
	public void shutdownComputer() {
		if (canSendPacket()) {
			sendPacketToServer(PacketID.KEYBOARD_SHUTDOWN);
		}
	}
	
	public void rebootComputer() {
		if (canSendPacket()) {
			sendPacketToServer(PacketID.KEYBOARD_REBOOT);
		}
	}
	
	public void onPaste(String clipboard) {
		if (canSendPacket()) {
			ByteArrayDataOutput stream = ByteStreams.newDataOutput();
			stream.writeUTF(clipboard);
			sendPacketToServer(PacketID.KEYBOARD_PASTE, stream);
		}
	}
	
	private boolean canSendPacket() {
		return connection.isConnected() && connection.isValidConnection() && connection.inRange(xCoord, yCoord, zCoord);
	}
	
	private void sendPacketToServer(PacketID packetId) {
		sendPacketToServer(packetId, ByteStreams.newDataOutput());
	}
	
	private void sendPacketToServer(PacketID packetId, ByteArrayDataOutput stream) {
		ChunkCoordinates coords = connection.getLocation();
		int dimId = worldObj.provider.dimensionId;
		
		stream.writeInt(dimId);
		stream.writeInt(coords.posX);
		stream.writeInt(coords.posY);
		stream.writeInt(coords.posZ);
		
		Packet131MapData packet = PacketDispatcher.getTinyPacket(MoarPeripherals.instance, (short) packetId.ordinal(), stream.toByteArray());
		PacketDispatcher.sendPacketToServer(packet);
	}
	
	public boolean hasConnection() {
		return connection.isConnected();
	}
	
	public void connectToComputer(EntityPlayer player, int x, int y, int z) {
		ChunkCoordinates coords = connection.getLocation();
		if (coords.posX != x || coords.posY != y || coords.posZ != z) {
			connection.newConnection(x, y, z);
			ByteArrayDataOutput stream = ByteStreams.newDataOutput();
			int dimId = worldObj.provider.dimensionId;
			stream.writeInt(dimId);
			stream.writeInt(xCoord);
			stream.writeInt(yCoord);
			stream.writeInt(zCoord);
			if (worldObj.isRemote) {
				sendPacketToServer(PacketID.KEYBOARD_SYNC, stream);
			} else {
				Packet131MapData packet = PacketDispatcher.getTinyPacket(MoarPeripherals.instance, (short) PacketID.KEYBOARD_SYNC.ordinal(), stream.toByteArray());
				PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
			}
		}
	}
	
	public void disconnectFromComputer() {
		connection.removeConnection();
	}
	
	public boolean isConnectionValid() {
		return connection.isValidConnection();
	}
	
	public boolean isComputerInRange(int x, int y, int z) {
		return connection.inRange(x, y, z);
	}
	
	public ChunkCoordinates getConnectionLocation() {
		return connection.getLocation();
	}
	
	public class KeyboardConnection {
		private Integer xPos, yPos, zPos;
		private boolean activeConnection = false;
		
		public boolean isConnected() {
			return activeConnection;
		}
		
		public boolean isValidConnection() {
			if (xPos == null || yPos == null || zPos == null) {
				return false;
			}
			return ComputerUtils.getTileComputerBase(worldObj, xPos, yPos, zPos) != null;
		}
		
		public boolean inRange(int x, int y, int z) {
			x -= xCoord;
			y -= yCoord;
			z -= zCoord;
			return MathHelper.sqrt_double(x*x + y*y + z*z) <= Settings.keyboardRange;
		}
		
		public ChunkCoordinates getLocation() {
			if (xPos == null || yPos == null || zPos == null) {
				return new ChunkCoordinates();
			}
			return new ChunkCoordinates(xPos, yPos, zPos);
		}
		
		public void newConnection(int x, int y, int z) {
			xPos = x;
			yPos = y;
			zPos = z;
			activeConnection = true;
		}
		
		public void removeConnection() {
			xPos = yPos = zPos = null;
		}
	}
}