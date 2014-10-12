/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.Computers;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.IChatListener;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.ICommandListener;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.IDeathListener;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.IPlayerEventListener;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.handler.ChatBoxHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.tile.abstracts.TileMoarP;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.ChatUtils;
import com.theoriginalbit.minecraft.moarperipherals.common.utils.LogUtils;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 12/10/2014
 */
@LuaPeripheral("chatbox_admin")
public class TileChatBoxAdmin extends TileMoarP implements IBreakAwareTile, IChatListener, IDeathListener, ICommandListener, IPlayerEventListener {
    private static final String EVENT_CHAT = "chat_message";
    private static final String EVENT_DEATH = "death_message";
    private static final String EVENT_JOIN = "player_join";
    private static final String EVENT_LEAVE = "player_leave";
    private static final String EVENT_COMMAND = "chatbox_command";
    private static final String COMMAND_TOKEN = "##";
    private static final int MAX_LABEL_LENGTH = 20;

    private boolean registered = false;

    // user runtime configurable
    private String label = "";

    @Computers.List
    public ArrayList<IComputerAccess> computers;

    @LuaFunction
    public boolean say(String message) throws Exception {
        ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
        String[] playerNames = scm.getAllUsernames();
        if (playerNames.length == 0) {
            return false;
        }

        ChatUtils.sendChatToPlayer(playerNames, buildMessage(message, false));
        return true;
    }

    @LuaFunction
    public boolean tell(String username, String message) throws Exception {
        ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();

        // check existence of the player
        if (scm.getPlayerForUsername(username) == null) {
            return false;
        }

        ChatUtils.sendChatToPlayer(username, buildMessage(message, true));
        return true;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setLabel(String str) {
        str = ChatAllowedCharacters.filerAllowedCharacters(str.trim());
        if (str.length() > MAX_LABEL_LENGTH) {
            return new Object[]{false, ""};
        }
        label = str;
        return new Object[]{true};
    }

    @LuaFunction
    public String getLabel() {
        return label;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote && !registered) {
            ChatBoxHandler.instance.addChatListener(this);
            ChatBoxHandler.instance.addDeathListener(this);
            ChatBoxHandler.instance.addPlayerEventListener(this);
            try {
                ChatBoxHandler.instance.addCommandListener(this);
            } catch (Exception e) {
                LogUtils.info(String.format("Failed to register command listener for ChatBox at %d %d %d", xCoord, yCoord, zCoord));
                e.printStackTrace();
            }
            registered = true;
        }
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        unload();
    }

    @Override
    public void onBreak(int x, int y, int z) {
        unload();
    }

    // IChatListener

    @Override
    public void onServerChatEvent(ServerChatEvent event) {
        computerQueueEvent(EVENT_CHAT, event.player.getDisplayName(), event.message);
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
                    killer = ent.getEntityName();
                }
            }
            computerQueueEvent(EVENT_DEATH, event.entity.getEntityName(), source.getDamageType(), killer);
        }
    }

    // IPlayerEventListener
    @Override
    public void onPlayerJoin(String username) {
        computerQueueEvent(EVENT_JOIN, username);
    }

    @Override
    public void onPlayerLeave(String username) {
        computerQueueEvent(EVENT_LEAVE, username);
    }

    // ICommandListener

    @Override
    public String getToken() {
        return COMMAND_TOKEN;
    }

    @Override
    public void onServerChatEvent(String message, EntityPlayer player) {
        computerQueueEvent(EVENT_COMMAND, player.getDisplayName(), message);
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

    private void unload() {
        // remove the ChatBox to the ChatListener
        if (!worldObj.isRemote) {
            ChatBoxHandler.instance.removeChatListener(this);
            ChatBoxHandler.instance.removeDeathListener(this);
            ChatBoxHandler.instance.removeCommandListener(this);
            ChatBoxHandler.instance.removePlayerEventListener(this);
        }
    }

    private String buildMessage(String msg, boolean pm) {
        return "[" + (label.isEmpty() ? "ChatBox" : label) + (ConfigHandler.displayChatBoxCoords ? String.format(" (%d,%d,%d)", xCoord, yCoord, zCoord) : "") + "] " + (pm ? "[PM] " : "") + msg;
    }

}