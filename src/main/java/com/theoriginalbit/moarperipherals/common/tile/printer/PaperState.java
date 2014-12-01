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
package com.theoriginalbit.moarperipherals.common.tile.printer;

/**
 * @author theoriginalbit
 * @since 16/11/14
 */
public enum PaperState {
    PAPER_NONE(2), PAPER_INPUT(3), PAPER_OUTPUT(4), PAPER_BOTH(5);

    private final int textureId;

    private PaperState(int id) {
        textureId = id;
    }

    public int getTextureId() {
        return textureId;
    }
}
