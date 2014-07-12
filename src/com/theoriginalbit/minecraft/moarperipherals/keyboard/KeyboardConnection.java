package com.theoriginalbit.minecraft.moarperipherals.keyboard;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import com.theoriginalbit.minecraft.moarperipherals.utils.ComputerUtils;

public class KeyboardConnection {
	private final TileEntity tile;
	private Integer xPos, yPos, zPos;
	private boolean activeConnection;
	
	public KeyboardConnection(TileEntity tileEntity) {
		tile = tileEntity;
		activeConnection = false;
	}
	
	public boolean isConnected() {
		return activeConnection;
	}
	
	public boolean isValidConnection() {
		if (xPos == null || yPos == null || zPos == null) { return false; }
		return ComputerUtils.getTileComputerBase(tile.worldObj, xPos, yPos, zPos) != null;
	}
	
	public ChunkCoordinates getLocation() {
		if (xPos == null || yPos == null || zPos == null) { return new ChunkCoordinates(); }
		return new ChunkCoordinates(xPos, yPos, zPos);
	}
	
	public void openConnection(int x, int y, int z) {
		xPos = x;
		yPos = y;
		zPos = z;
		activeConnection = true;
	}
	
	public void closeConnection() {
		xPos = yPos = zPos = null;
		activeConnection = false;
	}
}