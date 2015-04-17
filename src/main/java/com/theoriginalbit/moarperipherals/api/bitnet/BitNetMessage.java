/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.api.bitnet;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;

import java.util.UUID;

/**
 * The state of a message within a BitNetwork
 */
public final class BitNetMessage {
    private final UUID messageId;
    private final int sendChannel;
    private final int replyChannel;
    private final Object payload;
    private final double distanceTravelled;

    public BitNetMessage(int send, int reply, Object message) throws LuaException {
        messageId = UUID.randomUUID();
        sendChannel = send;
        replyChannel = reply;
        payload = message;
        distanceTravelled = 0;
    }

    public BitNetMessage(BitNetMessage other, double addDistance) {
        messageId = other.getId();
        sendChannel = other.getSendChannel();
        replyChannel = other.getReplyChannel();
        payload = other.getPayload();
        distanceTravelled = other.getDistanceTravelled() + addDistance;
    }

    public UUID getId() {
        return messageId;
    }

    public int getSendChannel() {
        return sendChannel;
    }

    public int getReplyChannel() {
        return replyChannel;
    }

    public Object getPayload() {
        return payload;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public Object[] getEventData(IComputerAccess computer) {
        return new Object[]{computer.getAttachmentName(), getSendChannel(), getReplyChannel(), getPayload(),
                getDistanceTravelled()};
    }

    public String toString() {
        return String.format("{type=BitNet Message, id=#%s payload=%s}", messageId, payload);
    }
}
