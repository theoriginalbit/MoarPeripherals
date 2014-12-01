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
package com.theoriginalbit.moarperipherals.common.utils;

import com.theoriginalbit.moarperipherals.common.network.packet.PacketGeneric;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;

@SuppressWarnings("unused")
public final class PacketUtils {

    public static void sendToServer(PacketGeneric packet) {
        PacketDispatcher.sendPacketToServer(packet.toPacket());
    }

    public static void sendToAllPlayers(PacketGeneric packet) {
        PacketDispatcher.sendPacketToAllPlayers(packet.toPacket());
    }

    public static void sendToPlayer(PacketGeneric packet, EntityPlayer player) {
        PacketDispatcher.sendPacketToPlayer(packet.toPacket(), (Player) player);
    }

    public static void sendToPlayersAround(PacketGeneric packet, double x, double y, double z, double range, int dimId) {
        PacketDispatcher.sendPacketToAllAround(x, y, z, range, dimId, packet.toPacket());
    }

    public static void sendToPlayersInDimension(PacketGeneric packet, int dimId) {
        PacketDispatcher.sendPacketToAllInDimension(packet.toPacket(), dimId);
    }

}