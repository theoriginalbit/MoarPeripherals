/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.moarperipherals.api.bitnet;

/**
 * The representation of a relay on the network, a relay is able to send and recieve messages through the network of
 * the world it exists in
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public interface IBitNetRelay extends IBitNetNode {
    /**
     * Gets the type of the relay, this determines the distance that the message can be sent over
     *
     * @return the relay type
     */
    RelayType getRelayType();

    /**
     * Invoked when this relay was within range of a message that was sent
     *
     * @param payload the message that was sent
     */
    void receive(BitNetMessage payload);

    /**
     * The relay type. This is used to determine the maximum distance a message can be sent
     */
    enum RelayType {
        SHORT_RANGE, LONG_RANGE, ORBITAL
    }
}
