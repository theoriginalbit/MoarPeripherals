package com.theoriginalbit.minecraft.moarperipherals.api;

import java.util.UUID;

/**
 * A Minecraft mod that adds more peripherals into the ComputerCraft mod.
 * Official Thread:
 * http://www.computercraft.info/forums2/index.php?/topic/19357-
 * Official Wiki:
 * http://wiki.theoriginalbit.com/moarperipherals/
 * <p/>
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
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
