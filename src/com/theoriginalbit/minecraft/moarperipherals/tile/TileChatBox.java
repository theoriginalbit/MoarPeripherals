package com.theoriginalbit.minecraft.moarperipherals.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.moarperipherals.api.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.reference.Settings;
import com.theoriginalbit.minecraft.moarperipherals.utils.ChatUtils;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;

public class TileChatBox extends TilePeripheral implements IBreakAwareTile {
	private static final String TYPE = "chatbox";
	private static final String[] METHOD_NAMES = new String[]{"say", "tell"};
	private static final String EVENT_CHAT = "chat_message";
	private static final String EVENT_DEATH = "death_message";
	private static final int TICKER_INTERVAL = 20;
	
	private int ticker = 0;
	private int count = 0;
	
	public TileChatBox(World world) {
		super(TYPE, METHOD_NAMES);
		// add the ChatBox to the ChatListener
		if (!world.isRemote) {
			ChatListener.instance.chatboxes.add(this);
		}
	}
	
	@Override
	public void onBreak(int x, int y, int z) {
		// remove the ChatBox to the ChatListener
		if (!worldObj.isRemote) {
			ChatListener.instance.chatboxes.remove(this);
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
	
	public Object[] say(String message) throws Exception {
		Preconditions.checkArgument(count++ <= Settings.chatSayRate, "too many messages (max " + Settings.chatSayRate + " per second)");
		
		String[] usernames = getPlayerUsernames();
		if (usernames.length == 0) {
			return new Object[]{ false };
		}
		
		ChatUtils.sendChatToPlayer(usernames, buildMessage(message, false));
		return new Object[]{ true };
	}
	
	public Object[] tell(String username, String message) throws Exception {
		Preconditions.checkArgument(count++ <= Settings.chatSayRate, "too many messages (max " + Settings.chatSayRate + " per second)");
		
		ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
		
		final int range = Settings.chatRangeTell;
		if (range == 0 || (range > 0 && !entityInRange(scm.getPlayerForUsername(username), range))) {
			return new Object[]{ false };
		}
		
		ChatUtils.sendChatToPlayer(username, buildMessage(message, true));
		return new Object[]{ true };
	}
	
	@Override
	public void updateEntity() {
		if (++ticker > TICKER_INTERVAL) {
			ticker = count = 0;
		}
	}
	
	public void onChatEvent(EntityPlayerMP player, String message) {
		if (Settings.chatRangeRead < 0 || entityInRange(player, Settings.chatRangeRead)) {
			computerQueueEvent(EVENT_CHAT, player.username, message);
		}
	}
	
	public void onDeathEvent(Entity entity, String killer, String damage) {
		if (Settings.chatRangeRead < 0 || entityInRange(entity, Settings.chatRangeRead)) {
			computerQueueEvent(EVENT_DEATH, entity.getEntityName(), killer, damage);
		}
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		switch (method) {
		// say
		case 0:
			Preconditions.checkArgument(arguments.length == 1, "expected string");
			Preconditions.checkArgument(arguments[0] instanceof String, "expected string");
			return say((String) arguments[0]);
		// tell
		case 1:
			Preconditions.checkArgument(arguments.length == 2, "expected string");
			Preconditions.checkArgument(arguments[0] instanceof String, "expected string");
			Preconditions.checkArgument(arguments[1] instanceof String, "expected string");
			return tell((String) arguments[0], (String) arguments[1]);
		}
		return null;
	}
	
	public static final class ChatListener {
		public static final ChatListener instance = new ChatListener();
		public ArrayList<TileChatBox> chatboxes = Lists.newArrayList();
		
		@ForgeSubscribe
		public void onServerChatEvent(ServerChatEvent event) {
			for (TileChatBox tile : chatboxes) {
				tile.onChatEvent(event.player, event.message);
			}
		}
		
		@ForgeSubscribe
		public void onLivingDeathEvent(LivingDeathEvent event) {
			if (event.entity instanceof EntityPlayerMP) {
				DamageSource source = event.source;
				String killer = null;
				if (source instanceof EntityDamageSource) {
					Entity ent = ((EntityDamageSource) source).getEntity();
					if (ent != null)
						killer = ent.getEntityName();
				}
				String damage = source.getDamageType();
				
				for (TileChatBox tile : chatboxes) {
					tile.onDeathEvent(event.entity, killer, damage);
				}
			}
		}
	}
}