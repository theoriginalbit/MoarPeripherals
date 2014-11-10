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
package com.theoriginalbit.moarperipherals.common.network.message;

import net.minecraft.world.World;

/**
 * @author theoriginalbit
 * @since 13/10/2014
 */
@SuppressWarnings("unused")
public class MessageParticle extends MessageGeneric {

    public MessageParticle() {
        // required empty constructor
    }

    public MessageParticle(World world, String name, double x, double y, double z) {
        this(world, name, x, y, z, 1f);
    }

    public MessageParticle(World world, String name, double x, double y, double z, double velX) {
        this(world, name, x, y, z, velX, 0f, 0f);
    }

    public MessageParticle(World world, String name, double x, double y, double z, double velX, double velY, double velZ) {
        // make the packet for the clients
        stringData = new String[]{name};
        intData = new int[]{world.provider.dimensionId};
        doubleData = new double[]{x, y, z, velX, velY, velZ};
    }

}
