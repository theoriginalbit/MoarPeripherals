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
package com.moarperipherals.bitnet;

import com.google.common.collect.Maps;
import com.moarperipherals.api.bitnet.IBitNetUniverse;
import com.moarperipherals.api.bitnet.IBitNetWorld;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;

import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class BitNetUniverse implements IBitNetUniverse {
    /**
     * The BitNetUniverse instance
     */
    public static final IBitNetUniverse UNIVERSE = new BitNetUniverse();

    /**
     * A mapping between the game worlds and the networks that run in that world
     */
    private final Map<World, BitNetWorld> networks = Maps.newHashMap();

    private BitNetUniverse() {
        // prevent instances
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IBitNetWorld getBitNetWorld(World world) {
        BitNetWorld bitNetWorld = networks.get(world);
        if (bitNetWorld == null) {
            networks.put(world, bitNetWorld = new BitNetWorld(this, world));
        }
        return bitNetWorld;
    }

    /**
     * Invoked once per server-tick, each tick the delayed messages are informed so that they can reduce their
     * counter, once their counter is complete they will dispatch themselves.
     *
     * @param event the FML event
     */
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (BitNetWorld network : networks.values()) {
                network.tick();
            }
        }
    }
}
