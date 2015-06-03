/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.moarperipherals.api;

import com.moarperipherals.api.bitnet.BitNetMessage;
import com.moarperipherals.api.bitnet.IBitNetNode;
import com.moarperipherals.api.bitnet.IBitNetUniverse;
import com.moarperipherals.api.note.IIronNoteRegistry;

/**
 * The main API access for MoarPeripherals. Use this to get access to various systems of MoarPeripherals at runtime.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public class MoarPeripheralsAPI {
    private static IBitNetUniverse bitNetUniverse = null;
    private static IIronNoteRegistry noteRegistry = null;
    private static IIronNoteRegistry sfxRegistry = null;

    static {
        try {
            Class<?> clazz = Class.forName("com.theoriginalbit.moarperipherals.common.bitnet.BitNetUniverse");
            bitNetUniverse = (IBitNetUniverse) clazz.getField("UNIVERSE").get(clazz);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Class<?> clazz = Class.forName("com.theoriginalbit.moarperipherals.common.registry.IronNoteRegistry");
            noteRegistry = (IIronNoteRegistry) clazz.getField("NOTE").get(clazz);
            sfxRegistry = (IIronNoteRegistry) clazz.getField("SFX").get(clazz);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the {@link IBitNetUniverse} instance so that {@link IBitNetNode}s can be added/removed from the network
     * in order to be able to send and receive {@link BitNetMessage}s
     *
     * @return the BitNetUniverse instance
     */
    public static IBitNetUniverse getBitNetRegistry() {
        return bitNetUniverse;
    }

    /**
     * Gets the {@link IIronNoteRegistry} instance so that you may register new notes for the Iron Note block to play.
     *
     * @return the IronNoteRegistry instance
     */
    public static IIronNoteRegistry getNoteRegistry() {
        return noteRegistry;
    }

    /**
     * Gets the {@link IIronNoteRegistry} instance so that you may register new in-game sound effects for the Iron
     * Note block to play.
     *
     * @return the IronNoteRegistry instance
     */
    public static IIronNoteRegistry getSfxRegistry() {
        return sfxRegistry;
    }
}
