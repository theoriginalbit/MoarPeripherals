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
package com.theoriginalbit.moarperipherals.common.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.theoriginalbit.moarperipherals.api.note.IIronNoteRegistry;
import com.theoriginalbit.moarperipherals.common.util.LogUtil;

import java.util.List;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class IronNoteRegistry implements IIronNoteRegistry {
    public static final IronNoteRegistry NOTE = new IronNoteRegistry();
    public static final IronNoteRegistry SFX = new IronNoteRegistry();

    static {
        NOTE.register("harp", "note.harp");
        NOTE.register("bd", "note.bd");
        NOTE.register("snare", "note.snare");
        NOTE.register("hat", "note.hat");
        NOTE.register("bassattack", "note.bassattack");
        NOTE.register("pling", "note.pling");
        NOTE.register("bass", "note.bass");

        SFX.register("eat", "random.eat");
        SFX.register("drink", "random.drink");
        SFX.register("burp", "random.burp");
        SFX.register("break", "random.break");
        SFX.register("anvil_use", "random.anvil_use");
        SFX.register("anvil_break", "random.anvil_break");
        SFX.register("anvil_land", "random.anvil_land");
        SFX.register("door_open", "random.door_open");
        SFX.register("door_close", "random.door_close");
        SFX.register("chest_open", "random.chestopen");
        SFX.register("chest_closed", "random.chestclosed");
        SFX.register("pop", "random.pop");
        SFX.register("fizz", "random.fizz");
        SFX.register("splash", "random.splash");
        SFX.register("orb", "random.orb");
        SFX.register("level_up", "random.levelup");
        SFX.register("bow", "random.bow");
        SFX.register("bow_hit", "random.bowhit");
        SFX.register("explode", "random.explode");
        SFX.register("click", "random.click");
        SFX.register("wood_click", "random.wood_click");
        SFX.register("pound", "moarperipherals:densityScan");
    }

    private final BiMap<String, String> sounds = HashBiMap.create();
    private final List<String> indexLookup = Lists.newArrayList();

    private IronNoteRegistry() {
        // prevent instances
    }

    @Override
    public boolean register(String name, String sound) {
        if (sounds.containsKey(name)) {
            LogUtil.error("'" + name + "' already in use for '" + sounds.get(name) + "'");
            return false;
        }

        if (sounds.containsValue(sound)) {
            LogUtil.error("'" + sound + "' already registered under '" + sounds.inverse().get(sound) + "'");
            return false;
        }

        indexLookup.add(name);
        sounds.put(name, sound);
        return true;
    }

    @Override
    public boolean contains(String name) {
        return sounds.containsKey(name);
    }

    @Override
    public boolean contains(int index) {
        return index >= 0 && index < indexLookup.size();
    }

    @Override
    public String getSound(String name) {
        if (contains(name)) {
            return sounds.get(name);
        }
        return null;
    }

    @Override
    public String getSound(int index) {
        if (contains(index)) {
            return getSound(indexLookup.get(index));
        }
        return null;
    }

    @Override
    public int size() {
        return sounds.size();
    }
}
