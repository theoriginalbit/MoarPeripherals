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
package com.moarperipherals.api.note;

import com.moarperipherals.api.MoarPeripheralsAPI;

/**
 * A registry containing all the notes {@link MoarPeripheralsAPI#getNoteRegistry()} and sound effects {@link
 * MoarPeripheralsAPI#getSfxRegistry()} for an Iron Note block, allowing registration of extra sounds by mod authors.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public interface IIronNoteRegistry {
    /**
     * Registers a sound for use with Iron Note blocks, each sound can be accessed via their user accessible name or
     * via a mapped index.
     *
     * @param name  the user accessible name e.g. chirp
     * @param sound the sound name e.g. modid:chirp
     * @return if registration was successfull
     */
    boolean register(String name, String sound);

    /**
     * Returns whether the supplied user accessible name has a mapping in the registry.
     *
     * @param name the user accessible name e.g. chirp
     * @return presence of the mapping
     */
    boolean contains(String name);

    /**
     * Returns whether the supplied index has a mapping.
     *
     * @param index the index to check
     * @return presence of the mapping
     */
    boolean contains(int index);

    /**
     * Returns the name of the sound for the supplied user accessible name.
     *
     * @param name the user accessible name e.g. chirp
     * @return the sound name e.g. modid:chirp
     */
    String getSound(String name);

    /**
     * Returns the name of the sound for the supplied index mapping.
     *
     * @param index the index to check
     * @return the sound name e.g. modid:chirp
     */
    String getSound(int index);

    /**
     * @return the number of mappings in this registry
     */
    int size();
}
