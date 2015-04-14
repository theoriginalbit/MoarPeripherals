/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.theoriginalbit.moarperipherals.common.handler;

public interface IPlayerEventHook {
    void onPlayerJoin(String username);

    void onPlayerLeave(String username);
}
