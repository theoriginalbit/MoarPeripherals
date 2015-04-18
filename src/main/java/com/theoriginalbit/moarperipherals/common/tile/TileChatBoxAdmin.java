/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.tile;

import com.theoriginalbit.framework.peripheral.annotation.Computers;
import com.theoriginalbit.framework.peripheral.annotation.function.LuaFunction;
import com.theoriginalbit.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.moarperipherals.common.handler.IPlayerEventHook;
import com.theoriginalbit.framework.peripheral.annotation.function.MultiReturn;
import com.theoriginalbit.moarperipherals.common.config.ConfigData;
import com.theoriginalbit.moarperipherals.common.handler.ChatBoxHandler;
import com.theoriginalbit.moarperipherals.common.handler.IChatHook;
import com.theoriginalbit.moarperipherals.common.handler.ICommandHook;
import com.theoriginalbit.moarperipherals.common.handler.IDeathHook;
import com.theoriginalbit.moarperipherals.common.base.TileMoarP;
import com.theoriginalbit.moarperipherals.common.util.ChatUtil;
import com.theoriginalbit.moarperipherals.common.util.LogUtil;
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
public class TileChatBoxAdmin extends TileMoarP implements IChatHook, IDeathHook, ICommandHook, IPlayerEventHook {
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

        ChatUtil.sendChatToPlayer(playerNames, buildMessage(message, false));
        return true;
    }

    @LuaFunction
    public boolean tell(String username, String message) throws Exception {
        ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();

        // check existence of the player
        if (scm.func_152612_a(username) == null) {
            return false;
        }

        ChatUtil.sendChatToPlayer(username, buildMessage(message, true));
        return true;
    }

    @LuaFunction
    @MultiReturn
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
            ChatBoxHandler.instance.addChatHook(this);
            ChatBoxHandler.instance.addDeathHook(this);
            ChatBoxHandler.instance.addPlayerEventHook(this);
            try {
                ChatBoxHandler.instance.addCommandHook(this);
            } catch (Exception e) {
                LogUtil.info(String.format("Failed to register command listener for ChatBox at %d %d %d", xCoord,
                        yCoord, zCoord));
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
    public void blockBroken(int x, int y, int z) {
        unload();
    }

    @Override
    public void onServerChatEvent(ServerChatEvent event) {
        computerQueueEvent(EVENT_CHAT, event.player.getDisplayName(), event.message);
    }

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
            computerQueueEvent(EVENT_DEATH, event.entity.getCommandSenderName(), source.getDamageType(), killer);
        }
    }

    @Override
    public void onPlayerJoin(String username) {
        computerQueueEvent(EVENT_JOIN, username);
    }

    @Override
    public void onPlayerLeave(String username) {
        computerQueueEvent(EVENT_LEAVE, username);
    }

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
            ChatBoxHandler.instance.removeChatHook(this);
            ChatBoxHandler.instance.removeDeathHook(this);
            ChatBoxHandler.instance.removeCommandHook(this);
            ChatBoxHandler.instance.removePlayerEventHook(this);
        }
    }

    private String buildMessage(String msg, boolean pm) {
        return "[" + (label.isEmpty() ? "ChatBox" : label) + (ConfigData.displayChatBoxCoordinate ? String.format(" (%d,%d,%d)", xCoord, yCoord, zCoord) : "") + "] " + (pm ? "[PM] " : "") + msg;
    }

}