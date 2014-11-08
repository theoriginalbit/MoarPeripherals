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

/**
 * definition for your class to listen to player join/leave message
 *
 * @author theoriginalbit
 * @since 12/10/2014
 */
public interface IPlayerEventListener {
    /**
     * Invoked when a player joins the server
     *
     * @param username the player that joined
     */
    public void onPlayerJoin(String username);

    /**
     * Invoked when a player leaves the server
     *
     * @param username the player that left
     */
    public void onPlayerLeave(String username);

}
