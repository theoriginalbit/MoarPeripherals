/**
 * Copyright (c) 2013-2014, Joshua Asbury (@theoriginalbit)
 * http://wiki.theoriginalbit.com/moarperipherals/
 *
 * MoarPeripherals is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
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
