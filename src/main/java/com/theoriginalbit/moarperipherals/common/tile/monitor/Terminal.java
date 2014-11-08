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
package com.theoriginalbit.moarperipherals.common.tile.monitor;

import net.minecraft.network.Packet;

public class Terminal {

    private int width;
    private int height;
    private int cursorX = 0;
    private int cursorY = 0;

    public Terminal(int terminalWidth, int terminalHeight) {
        width = terminalWidth;
        height = terminalHeight;
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public Packet getDiffPacket() {
        return null;
    }

}