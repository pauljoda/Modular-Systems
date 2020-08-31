package com.pauljoda.modularsystems.core.multiblock;

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
     * Called when an expansion is added to this core, add logic to modify here, expansion will not
     * @param expansion Expansion
     */
    void addExpansion(IMultiBlockExpansion expansion);
}
