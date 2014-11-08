/**
 * Copyright 2014 Joshua Asbury (@theoriginalbit)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.moarperipherals.common.network;

import com.google.common.collect.Sets;
import com.theoriginalbit.moarperipherals.common.network.message.MessageFxTeleport;
import com.theoriginalbit.moarperipherals.common.network.message.MessageParticle;
import com.theoriginalbit.moarperipherals.common.network.message.MessageSoundEffect;
import com.theoriginalbit.moarperipherals.common.network.message.handler.MessageHandlerFXTeleport;
import com.theoriginalbit.moarperipherals.common.network.message.handler.MessageHandlerParticle;
import com.theoriginalbit.moarperipherals.common.network.message.handler.MessageHandlerSoundEffect;
import com.theoriginalbit.moarperipherals.common.reference.ModInfo;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;

import java.util.Set;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ModInfo.CHANNEL);
    private static int id = 0;

    public static void init() {
        INSTANCE.registerMessage(MessageHandlerParticle.class, MessageParticle.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerSoundEffect.class, MessageSoundEffect.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(MessageHandlerFXTeleport.class, MessageFxTeleport.class, id++, Side.CLIENT);
    }

    public static Set<EntityPlayer> getPlayersWatchingChunk(WorldServer world, int chunkX, int chunkZ) {
        final PlayerManager manager = world.getPlayerManager();

        final Set<EntityPlayer> playerList = Sets.newHashSet();
        for (Object o : world.playerEntities) {
            EntityPlayerMP player = (EntityPlayerMP) o;
            if (manager.isPlayerWatchingChunk(player, chunkX, chunkZ)) playerList.add(player);
        }
        return playerList;
    }

    public static Set<EntityPlayer> getPlayersWatchingBlock(WorldServer world, int blockX, int blockZ) {
        return getPlayersWatchingChunk(world, blockX >> 4, blockZ >> 4);
    }

}
