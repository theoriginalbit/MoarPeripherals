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
package com.theoriginalbit.moarperipherals.api.listener;

import net.minecraft.entity.player.EntityPlayer;

/**
 * definition for your class to listen to the player chat
 *
 * @author theoriginalbit
 */
public interface ICommandListener {
    /**
     * Returns the command identifier, for example minecraft uses / you could
     * define yours to use $$
     */
    public String getToken();

    /**
     * Invoked when the start of a players message matches your defined token
     *
     * @param message the message that was sent
     * @param player  the player that sent the message
     */
    public void onServerChatEvent(String message, EntityPlayer player);

}
