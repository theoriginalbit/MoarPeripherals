/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package com.theoriginalbit.minecraft.moarperipherals.common.tile;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.Computers;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.moarperipherals.common.config.ConfigHandler;
import com.theoriginalbit.minecraft.moarperipherals.common.handler.ChatBoxHandler;
import com.theoriginalbit.minecraft.moarperipherals.api.tile.aware.IBreakAwareTile;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.IChatListener;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.ICommandListener;
import com.theoriginalbit.minecraft.moarperipherals.api.listener.IDeathListener;
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
import openperipheral.api.Ignore;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

@Ignore
@LuaPeripheral("chatbox")
public class TileChatBox extends TileMoarP implements IBreakAwareTile, IChatListener, IDeathListener, ICommandListener {

    private static final String ERROR_TOO_MANY = "too many messages (max %d per second)";
    private static final String ERROR_RANGE_FORMAT = "range must be between -1 (infinite) and %d inclusive";
    private static final String ERROR_RANGE_GENERIC = "range must be -1 (infinite) or higher";
    private static final String ERROR_LABEL = "label must be no more than %d characters long";

    private static final String EVENT_CHAT = "chat_message";
    private static final String EVENT_DEATH = "death_message";
    private static final String EVENT_COMMAND = "chatbox_command";
    private static final String COMMAND_TOKEN = "##";
    private static final int MAX_LABEL_LENGTH = 20;
    private static final int TICKER_INTERVAL = 20;

    private int ticker = 0;
    private int count = 0;
    private boolean registered = false;

    // user runtime configurable
    private String label = "";
    private int rangeSay = ConfigHandler.chatRangeSay;
    private int rangeTell = ConfigHandler.chatRangeTell;
    private int rangeRead = ConfigHandler.chatRangeRead;

    @Computers.List
    public ArrayList<IComputerAccess> computers;

    @LuaFunction
    public boolean say(String message) throws Exception {
        Preconditions.checkArgument(count++ <= ConfigHandler.chatSayRate, ERROR_TOO_MANY, ConfigHandler.chatSayRate);

        final String[] usernames = getPlayerUsernames();
        if (usernames.length == 0) {
            return false;
        }

        ChatUtils.sendChatToPlayer(usernames, buildMessage(message, false));
        return true;
    }

    @LuaFunction
    public boolean tell(String username, String message) throws Exception {
        Preconditions.checkArgument(count++ <= ConfigHandler.chatSayRate, ERROR_TOO_MANY, ConfigHandler.chatSayRate);

        ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();

        if (rangeTell == 0 || (rangeTell > 0 && !entityInRange(scm.getPlayerForUsername(username), rangeTell))) {
            return false;
        }

        ChatUtils.sendChatToPlayer(username, buildMessage(message, true));
        return true;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setLabel(String str) {
        str = ChatAllowedCharacters.filerAllowedCharacters(str.trim());
        if (str.length() > MAX_LABEL_LENGTH) {
            return new Object[]{false, String.format(ERROR_LABEL, MAX_LABEL_LENGTH)};
        }
        label = str;
        return new Object[]{true};
    }

    @LuaFunction
    public String getLabel() {
        return label;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setSayRange(int range) {
        final Object[] valid = validateRange(range, ConfigHandler.chatRangeSay);
        // if the new range was accepted
        if ((Boolean) valid[0]) {
            rangeSay = range;
        }
        return valid;
    }

    @LuaFunction
    public int getSayRange() {
        return rangeSay;
    }

    @LuaFunction
    public int getMaxSayRange() {
        return ConfigHandler.chatRangeSay;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setTellRange(int range) {
        final Object[] valid = validateRange(range, ConfigHandler.chatRangeTell);
        // if the new range was accepted
        if ((Boolean) valid[0]) {
            rangeTell = range;
        }
        return valid;
    }

    @LuaFunction
    public int getTellRange() {
        return rangeTell;
    }

    @LuaFunction
    public int getMaxTellRange() {
        return ConfigHandler.chatRangeTell;
    }

    @LuaFunction(isMultiReturn = true)
    public Object[] setReadRange(int range) {
        final Object[] valid = validateRange(range, ConfigHandler.chatRangeRead);
        // if the new range was accepted
        if ((Boolean) valid[0]) {
            rangeRead = range;
        }
        return valid;
    }

    @LuaFunction
    public int getReadRange() {
        return rangeRead;
    }

    @LuaFunction
    public int getMaxReadRange() {
        return ConfigHandler.chatRangeRead;
    }

    @Override
    public void updateEntity() {
        if (++ticker > TICKER_INTERVAL) {
            ticker = count = 0;
        }

        if (!worldObj.isRemote && !registered) {
            ChatBoxHandler.instance.addChatListener(this);
            ChatBoxHandler.instance.addDeathListener(this);
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
        if (rangeRead < 0 || entityInRange(event.player, rangeRead)) {
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
                    killer = ent.getEntityName();
                }
            }
            if (rangeRead < 0 || entityInRange(event.entity, rangeRead)) {
                computerQueueEvent(EVENT_DEATH, event.entity.getEntityName(), source.getDamageType(), killer);
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
        if (rangeRead < 0 || entityInRange(player, rangeRead)) {
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

    private void unload() {
        // remove the ChatBox to the ChatListener
        if (!worldObj.isRemote) {
            ChatBoxHandler.instance.removeChatListener(this);
            ChatBoxHandler.instance.removeDeathListener(this);
            ChatBoxHandler.instance.removeCommandListener(this);
        }
    }

    private Object[] validateRange(int range, int server) {
        // if they want a value below -1, which is a NO-OP number
        if (range < -1) {
            // if the server range is -1, they can set range to whatever they want, so tell them that otherwise
            // tell them the maximum they can use (which in reality they should check themselves too!)
            return new Object[]{false, server != -1 ? String.format(ERROR_RANGE_FORMAT, server) : ERROR_RANGE_GENERIC};
        }
        // if the server has a max specified that isn't infinite, and they want infinite, reject it
        if (range == -1 && server != -1) {
            return new Object[]{false, String.format(ERROR_RANGE_FORMAT, server)};
        }
        // if the server has a max specified that isn't infinite, and they want a higher value, reject it
        if (range > server && server != -1) {
            return new Object[]{false, String.format(ERROR_RANGE_FORMAT, server)};
        }
        // the number they want is valid
        return new Object[]{true};
    }

    private boolean entityInRange(Entity entity, int range) {
        return entity != null && (entity.getDistance(xCoord, yCoord, zCoord) <= range);
    }

    private String[] getPlayerUsernames() {
        ServerConfigurationManager scm = MinecraftServer.getServer().getConfigurationManager();
        String[] playerNames = scm.getAllUsernames();

        if (rangeSay == 0) {
            return new String[0];
        } else if (rangeSay > 0) {
            List<String> names = Lists.newArrayList();
            for (String s : playerNames) {
                if (entityInRange(scm.getPlayerForUsername(s), rangeSay)) {
                    names.add(s);
                }
            }
            return names.toArray(new String[names.size()]);
        }

        return playerNames;
    }

    private String buildMessage(String msg, boolean pm) {
        return "[" + (label.isEmpty() ? "ChatBox" : label) + (ConfigHandler.displayChatBoxCoords ? String.format(" (%d,%d,%d)", xCoord, yCoord, zCoord) : "") + "] " + (pm ? "[PM] " : "") + msg;
    }

}