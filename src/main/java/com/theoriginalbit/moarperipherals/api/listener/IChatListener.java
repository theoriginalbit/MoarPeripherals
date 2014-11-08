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

import net.minecraftforge.event.ServerChatEvent;

/**
 * definition for your class to listen to the player chat
 *
 * @author theoriginalbit
 */
public interface IChatListener {
    /**
     * Invoked when a player sends a chat message and it was not an
     * {@link ICommandListener}
     *
     * @param event the information about the chat event
     */
    public void onServerChatEvent(ServerChatEvent event);

}
