package com.theoriginalbit.minecraft.moarperipherals.tile;

import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IActivateAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.keyboard.KeyboardConnection;
import com.theoriginalbit.minecraft.moarperipherals.keyboard.KeyboardEvent;
import com.theoriginalbit.minecraft.moarperipherals.packet.PacketKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.reference.ModInfo;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.PacketUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
		PacketKeyboard packet = new PacketKeyboard(KeyboardEvent.TURN_ON);
		PacketUtils.sendToServer(packet);
		return true;
	}
	
	@Override
	@SideOnly(Side.SERVER)
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		int x = tag.getInteger("xPos");
		int y = tag.getInteger("yPos");
		int z = tag.getInteger("zPos");
		connection.openConnection(x, y, z);
	}
	
	@Override
	@SideOnly(Side.SERVER)
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		ChunkCoordinates coord = connection.getLocation();
		tag.setInteger("xPos", coord.posX);
		tag.setInteger("yPos", coord.posY);
		tag.setInteger("zPos", coord.posZ);
	}
	
	public final void sync(int targetX, int targetY, int targetZ) {
		
	}
	
	public ResourceLocation getTexture() {
		// TODO: return texture based on state
		return TEXTURE;
	}
	
	public final boolean isComputerInRange(ChunkCoordinates target) {
		double distance = getDistanceFrom(target.posX, target.posY, target.posZ);
		return MathHelper.sqrt_double(distance) <= Settings.keyboardRange;
	}
}