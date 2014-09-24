/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.theoriginalbit.framework.peripheral.annotation.ComputerList;
import com.theoriginalbit.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.handler.ChatHandler;
import com.theoriginalbit.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.moarperipherals.api.listener.IChatListener;
import com.theoriginalbit.moarperipherals.api.listener.ICommandListener;
import com.theoriginalbit.moarperipherals.api.listener.IDeathListener;
import com.theoriginalbit.moarperipherals.reference.Settings;
import com.theoriginalbit.moarperipherals.utils.ChatUtils;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@LuaPeripheral("chatbox")
public class TileChatBox extends TileMPBase implements IBreakAwareTile, IChatListener, IDeathListener, ICommandListener {

    private static final String EVENT_CHAT = "chat_message";
    private static final String EVENT_DEATH = "death_message";
    private static final String EVENT_COMMAND = "chatbox_command";
    private static final String COMMAND_TOKEN = "##";
    private static final int TICKER_INTERVAL = 20;

    private int ticker = 0;
    private int count = 0;
    private boolean registered = false;

    @ComputerList
    public ArrayList<IComputerAccess> computers;

    @LuaFunction
    public boolean say(String message) throws Exception {
        Preconditions.checkArgument(count++ <= Settings.chatSayRate, "too many messages (max " + Settings.chatSayRate + " per second)");

        String[] usernames = getPlayerUsernames();
        if (usernames.length == 0) {
            return false;
        }

        ChatUtils.sendChatToPlayer(usernames, buildMessage(message, false));
        return true;
    }

    @LuaFunction
    public boolean tell(String username, String message) throws Exception {
        Preconditions.checkArgument(count++ <= Settings.chatSayRate, "too many messages (max " + Settings.chatSayRate + " per second)");

        ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();

        final int range = Settings.chatRangeTell;
        if (range == 0 || (range > 0 && !entityInRange(scm.func_152612_a(username), range))) {
            return false;
        }

        ChatUtils.sendChatToPlayer(username, buildMessage(message, true));
        return true;
    }

    @Override
    public void updateEntity() {
        if (++ticker > TICKER_INTERVAL) {
            ticker = count = 0;
        }

        if (!worldObj.isRemote && !registered) {
            ChatHandler.instance.addChatListener(this);
            ChatHandler.instance.addDeathListener(this);
            try {
                ChatHandler.instance.addCommandListener(this);
            } catch (Exception e) {
                System.err.println(String.format("Failed to register command listener for ChatBox at %d %d %d", xCoord, yCoord, zCoord));
                e.printStackTrace();
            }
            registered = true;
        }
    }

    @Override
    public void onChunkUnload() {
        unload();
    }

    private void unload() {
        // remove the ChatBox to the ChatListener
        if (!worldObj.isRemote) {
            ChatHandler.instance.removeChatListener(this);
            ChatHandler.instance.removeDeathListener(this);
            ChatHandler.instance.removeCommandListener(this);
        }
    }

    @Override
    public void onBreak(int x, int y, int z) {
        unload();
    }

    // IChatListener

    @Override
    public void onServerChatEvent(ServerChatEvent event) {
        if (Settings.chatRangeRead < 0 || entityInRange(event.player, Settings.chatRangeRead)) {
            computerQueueEvent(EVENT_CHAT, event.player.getDisplayName(), event.message);
        }
    }

    // IDeathListener

    @Override
    public void onDeathEvent(LivingDeathEvent event) {
        if (event.entity instanceof EntityPlayerMP) {
            DamageSource source = event.source;
            String killer = null;
            if (source instanceof EntityDamageSource) {
                Entity ent = source.getEntity();
                if (ent != null) {
                    killer = ent.getCommandSenderName();
                }
            }
            if (Settings.chatRangeRead < 0 || entityInRange(event.entity, Settings.chatRangeRead)) {
                computerQueueEvent(EVENT_DEATH, event.entity.getCommandSenderName(), killer, source.getDamageType());
            }
        }
    }

    // ICommandListener

    @Override
    public String getToken() {
        return COMMAND_TOKEN;
    }

    @Override
    public void onServerChatEvent(String message, EntityPlayer player) {
        if (Settings.chatRangeRead < 0 || entityInRange(player, Settings.chatRangeRead)) {
            computerQueueEvent(EVENT_COMMAND, player.getDisplayName(), message);
        }
    }

    protected void computerQueueEvent(String event, Object... args) {
        if (computers == null || computers.isEmpty()) {
            return;
        }
        for (IComputerAccess computer : computers) {
            computer.queueEvent(event, ArrayUtils.add(args, 0, computer.getAttachmentName()));
        }
    }

    // Private methods

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
                if (entityInRange(scm.func_152612_a(s), range)) {
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