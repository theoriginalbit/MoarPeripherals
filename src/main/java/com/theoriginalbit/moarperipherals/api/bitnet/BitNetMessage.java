/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
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
        messageId = other.getId();
        payload = other.getPayload();
        distanceTravelled = other.getDistanceTravelled();
    }

    public UUID getId() {
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
