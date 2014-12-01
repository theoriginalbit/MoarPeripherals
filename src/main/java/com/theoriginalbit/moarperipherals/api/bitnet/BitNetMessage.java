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
package com.theoriginalbit.moarperipherals.api.bitnet;

import java.util.UUID;

public class BitNetMessage {

    private final UUID messageId;
    private final Object payload;
    private double distanceTravelled = 0;

    public BitNetMessage(Object message) {
        messageId = UUID.randomUUID();
        payload = message;
    }

    public BitNetMessage(BitNetMessage other) {
        messageId = other.getMessageId();
        payload = other.getPayload();
        distanceTravelled = other.getDistanceTravelled();
    }

    public UUID getMessageId() {
        return messageId;
    }

    public Object getPayload() {
        return payload;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public BitNetMessage addDistance(double dist) {
        distanceTravelled += dist;
        return this;
    }

    public String toString() {
        return String.format("{type=BitNet Message, id=#%s payload=%s}", messageId, payload);
    }

}
