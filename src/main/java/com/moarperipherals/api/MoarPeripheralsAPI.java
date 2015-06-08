/**
 * This file is part of the official MoarPeripherals API. Please see the usage
 * guide and restrictions on use in the package-info file.
 */
package com.moarperipherals.api;

import com.moarperipherals.api.bitnet.BitNetMessage;
import com.moarperipherals.api.bitnet.IBitNetNode;
import com.moarperipherals.api.bitnet.IBitNetUniverse;
import com.moarperipherals.api.note.IIronNoteRegistry;
import com.moarperipherals.api.sorter.IInteractiveSorterRegistry;

/**
 * The main API access for MoarPeripherals. Use this to get access to various systems of MoarPeripherals at runtime.
 *
 * @author Joshua Asbury (@theoriginalbit)
 */
@SuppressWarnings("unused")
public class MoarPeripheralsAPI {
    private static final String REGISTRY_PACKAGE = "com.theoriginalbit.moarperipherals.common.registry.";
    private static IBitNetUniverse bitNetUniverse = null;
    private static IIronNoteRegistry noteRegistry = null;
    private static IIronNoteRegistry sfxRegistry = null;
    private static IInteractiveSorterRegistry sorterRegistry = null;

    static {
        try {
            Class<?> clazz = Class.forName("com.theoriginalbit.moarperipherals.common.bitnet.BitNetUniverse");
            bitNetUniverse = (IBitNetUniverse) clazz.getField("UNIVERSE").get(clazz);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Class<?> clazz = Class.forName(REGISTRY_PACKAGE + "IronNoteRegistry");
            noteRegistry = (IIronNoteRegistry) clazz.getField("NOTE").get(clazz);
            sfxRegistry = (IIronNoteRegistry) clazz.getField("SFX").get(clazz);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Class<?> clazz = Class.forName(REGISTRY_PACKAGE + "InteractiveSorterRegistry");
            sorterRegistry = (IInteractiveSorterRegistry) clazz.getField("INSTANCE").get(clazz);
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

    /**
     * Gets the {@link IInteractiveSorterRegistry} instance so that you may register other sorter outputs in the
     * event that the interactive sorter cannot output to a desired output.
     *
     * @return the InteractiveSorterRegistry instance
     */
    public static IInteractiveSorterRegistry getInteractiveSorterRegistry() {
        return sorterRegistry;
    }
}
