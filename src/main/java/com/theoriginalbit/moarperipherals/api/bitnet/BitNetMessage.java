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
        sendChannel = checkChannel(send);
        replyChannel = checkChannel(reply);
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

    /**
     * Makes sure that the supplied channel is between the specified range
     *
     * @param channel the channel to check
     * @return the valid channel
     * @throws LuaException if the channel is invalid an error will occur
     */
    private static int checkChannel(int channel) throws LuaException {
        if (channel >= 0 && channel <= 65535) {
            return channel;
        }
        throw new LuaException("Expected number in range 0-65535");
    }
}
