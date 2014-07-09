package com.theoriginalbit.minecraft.moarperipherals.tile;

import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.world.World;
import openperipheral.api.Ignore;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.moarperipherals.MoarPeripherals;
import com.theoriginalbit.minecraft.moarperipherals.handler.TinyPacketHandler.PacketID;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;

import cpw.mods.fml.common.network.PacketDispatcher;

@Ignore
public class TileIronNote extends TilePeripheral {
	private static final String TYPE = "iron_note";
	private static final int MIN_INST = 0;
	private static final int MAX_INST = 4;
	private static final int MIN_PITCH = 0;
	private static final int MAX_PITCH = 24;
	private static final int MAX_TICK = 5; // this is 5 notes per tick, allowing for 5 note chords
	private int ticker = 0;

	public TileIronNote() {
		super(TYPE);
	}
	
	@LuaFunction
	public void playNote(int instrument, int pitch) throws Exception {
		Preconditions.checkArgument(instrument >= MIN_INST && instrument <= MAX_INST, "Expected instrument 0-4");
		Preconditions.checkArgument(pitch >= MIN_PITCH && pitch <= MAX_PITCH, "Expected pitch 0-24");
		Preconditions.checkArgument(ticker++ <= MAX_TICK, "Too many notes (over " + MAX_TICK + " per tick)");
		Preconditions.checkArgument(Settings.noteRange > 0, "The Iron Note blocks range has been disabled, please contact your server owner");
		
		int dimId = worldObj.provider.dimensionId;
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		stream.writeDouble(xCoord);
		stream.writeDouble(yCoord);
		stream.writeDouble(zCoord);
		stream.writeByte(instrument);
		stream.writeByte(pitch);
		stream.writeByte(dimId);
		
		play(worldObj, xCoord, yCoord, zCoord, instrument, pitch);
		
		Packet131MapData packet = PacketDispatcher.getTinyPacket(MoarPeripherals.instance, (short) PacketID.IRON_NOTE.ordinal(), stream.toByteArray());
		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, Settings.noteRange, dimId, packet);
	}
	
	@Override
	public void updateEntity() {
		ticker = 0;
	}
	
	public static void play(World world, double x, double y, double z, int instrument, int pitch) {
		float f = (float) Math.pow(2.0D, (double) (pitch - 12) / 12.0D);
		String s;
		switch (instrument) {
			case 1: s = "bd"; break;
			case 2: s = "snare"; break;
			case 3: s = "hat"; break;
			case 4: s = "bassattack"; break;
			default: s = "harp"; break;
		}
		
		world.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "note." + s, 3.0F, f);
		world.spawnParticle("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, (double) pitch / 24.0D, 0.0D, 0.0D);
	}
}