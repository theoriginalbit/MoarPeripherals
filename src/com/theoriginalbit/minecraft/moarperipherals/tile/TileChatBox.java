package com.theoriginalbit.minecraft.moarperipherals.tile;

import java.util.List;

import openperipheral.api.Ignore;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.TilePeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Arg;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.moarperipherals.ChatInteceptor;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.IChatListener;
import com.theoriginalbit.minecraft.moarperipherals.interfaces.listener.IDeathListener;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.ChatUtils;

@Ignore
public class TileChatBox extends TilePeripheral implements IBreakAwareTile, IChatListener, IDeathListener {
	private static final String TYPE = "chatbox";
	private static final String EVENT_CHAT = "chat_message";
	private static final String EVENT_DEATH = "death_message";
	private static final int TICKER_INTERVAL = 20;
	
	private int ticker = 0;
	private int count = 0;
	
	public TileChatBox(World world) {
		super(TYPE);
		// add the ChatBox to the ChatListener
		if (!world.isRemote) {
			ChatInteceptor.instance.addChatListener(this);
			ChatInteceptor.instance.addDeathListener(this);
		}
	}
	
	@Override
	public void onBreak(int x, int y, int z) {
		// remove the ChatBox to the ChatListener
		if (!worldObj.isRemote) {
			ChatInteceptor.instance.removeChatListener(this);
			ChatInteceptor.instance.removeDeathListener(this);
		}
	}
	
	@LuaFunction
	public boolean say(@Arg(LuaType.STRING) String message) throws Exception {
		Preconditions.checkArgument(count++ <= Settings.chatSayRate, "too many messages (max " + Settings.chatSayRate + " per second)");
		
		String[] usernames = getPlayerUsernames();
		if (usernames.length == 0) { return false; }
		
		ChatUtils.sendChatToPlayer(usernames, buildMessage(message, false));
		return true;
	}
	
	@LuaFunction
	public boolean tell(String username, String message) throws Exception {
		Preconditions.checkArgument(count++ <= Settings.chatSayRate, "too many messages (max " + Settings.chatSayRate + " per second)");
		
		ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
		
		final int range = Settings.chatRangeTell;
		if (range == 0 || (range > 0 && !entityInRange(scm.getPlayerForUsername(username), range))) { return false; }
		
		ChatUtils.sendChatToPlayer(username, buildMessage(message, true));
		return true;
	}
	
	@Override
	public void updateEntity() {
		if (++ticker > TICKER_INTERVAL) {
			ticker = count = 0;
		}
	}
	
	@Override
	public void onDeathEvent(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayerMP) {
			DamageSource source = event.source;
			String killer = null;
			if (source instanceof EntityDamageSource) {
				Entity ent = ((EntityDamageSource) source).getEntity();
				if (ent != null) {
					killer = ent.getEntityName();
				}
			}
			if (Settings.chatRangeRead < 0 || entityInRange(event.entity, Settings.chatRangeRead)) {
				computerQueueEvent(EVENT_DEATH, event.entity.getEntityName(), killer, source.getDamageType());
			}
		}
	}

	@Override
	public void onServerChatEvent(ServerChatEvent event) {
		if (Settings.chatRangeRead < 0 || entityInRange(event.player, Settings.chatRangeRead)) {
			computerQueueEvent(EVENT_CHAT, event.player.username, event.message);
		}
	}
	
	private boolean entityInRange(Entity entity, int range) {
		return entity != null && (entity.getDistance(xCoord, yCoord, zCoord) <= range);
	}
	
	private String[] getPlayerUsernames() {
		ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
		String[] playerNames = scm.getAllUsernames();
		
		final int range = Settings.chatRangeSay;
		if (range == 0) {
			return new String[0];
		} else if (range > 0) {
			List<String> names = Lists.newArrayList();
			for (String s : playerNames) {
				if (entityInRange(scm.getPlayerForUsername(s), range)) {
					names.add(s);
				}
			}
			return names.toArray(new String[names.size()]);
		}
		
		return playerNames;
	}
	
	private String buildMessage(String msg, boolean pm) {
		return "<ChatBox" + (Settings.displayChatBoxCoords ? String.format(" (%d,%d,%d)", xCoord, yCoord, zCoord) : "") + "> " + (pm ? "[PM] " : "") + msg;
	}
}