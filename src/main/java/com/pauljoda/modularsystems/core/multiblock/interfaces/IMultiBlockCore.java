package com.pauljoda.modularsystems.core.multiblock.interfaces;

import com.pauljoda.modularsystems.core.multiblock.collections.MultiBlockData;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/30/20
 */
public interface IMultiBlockCore {

    /**
     * Returns an instance of this multiblock's data object
     * @return Instance of data object
     */
    MultiBlockData getMultiBlockData();

    /**
     * Tests if the given expansion can join this network, check for things like size and type
     * @param expansion The expansion wanting to join
     * @return True if allowed to connect
     */
    boolean isExpansionAllowed(IMultiBlockExpansion expansion);

    /**
     * Destroy the network
     */
    void destroyMultiblock();

    /**
     * Called when an expansion is added to this core, add logic to modify here, expansion will not
     * @param expansion Expansion
     */
    default void addExpansion(IMultiBlockExpansion expansion) {
        getMultiBlockData().addNode(expansion.getPos());
    }

    /**
     * Removes given expansion from the network
     * @param expansion The expansion to remove
     * @return True if removed
     */
    default boolean removeExpansion(IMultiBlockExpansion expansion) {
        return getMultiBlockData().deleteNode(expansion);
    }
}
