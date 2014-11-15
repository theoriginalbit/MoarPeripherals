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

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * @author theoriginalbit
 * @since 9/11/14
 */
public class MessageFxIronNote extends MessageGeneric {
    private final ArrayList<PendingNote> notes = Lists.newArrayList();

    public MessageFxIronNote() {
        // required empty constructor
    }

    public void addNote(String instrument, int pitch) {
        notes.add(new PendingNote(instrument, pitch));
    }

    public MessageFxIronNote pack(int dimensionId, int xPos, int yPos, int zPos) {
        final int size = notes.size();
        intData = new int[]{dimensionId, size};
        doubleData = new double[]{xPos, yPos, zPos};

        floatData = new float[size];
        stringData = new String[size];
        for (int i = 0; i < size; ++i) {
            final PendingNote n = notes.get(i);
            floatData[i] = n.pitch;
            stringData[i] = n.instrument;
        }

        return this;
    }

    class PendingNote {
        public final String instrument;
        public final int pitch;

        public PendingNote(String instrument, int pitch) {
            this.instrument = instrument;
            this.pitch = pitch;
        }
    }
}
