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
 * @since 8/11/14
 */
@SuppressWarnings("unused")
public class MessageFxTeleport extends MessageGeneric {

    public MessageFxTeleport() {
        // required empty constructor
    }

    public MessageFxTeleport(World world, int dimensionId, int xPos, int yPos, int zPos) {
        intData = new int[]{dimensionId};
        doubleData = new double[]{xPos, yPos, zPos};
        world.playSoundEffect(xPos, yPos, zPos, "mob.endermen.portal", 0.4f, 1f);
    }
}
