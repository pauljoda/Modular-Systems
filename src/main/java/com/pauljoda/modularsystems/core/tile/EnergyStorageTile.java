package com.pauljoda.modularsystems.core.tile;

import com.pauljoda.modularsystems.core.manager.TileManager;
import com.pauljoda.modularsystems.core.multiblock.AbstractMultiBlockExpansionTile;
import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockCore;
import com.pauljoda.modularsystems.energy.tile.GeneratorTile;
import com.pauljoda.nucleus.common.tiles.energy.EnergyHandler;
import com.pauljoda.nucleus.energy.implementations.EnergyBank;

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
public class EnergyStorageTile extends AbstractMultiBlockExpansionTile {

    /*******************************************************************************************************************
     * Class Variables                                                                                                 *
     *******************************************************************************************************************/

    // Default value for how much is added when attached to a network
    protected static final int ADDED_STORAGE_VALUE = 32000;

    /**
     * Main Constructor
     */
    public EnergyStorageTile() {
        super(TileManager.energy_storage);
    }

    /*******************************************************************************************************************
     * AbstractMultiBlockExpansion                                                                                     *
     *******************************************************************************************************************/

    /**
     * Called when this expansion is added to a network, called after core has become aware of us
     */
    @Override
    public void addedToNetwork() {
        if(getCore() != null) {
            EnergyHandler coreHandler = (EnergyHandler) world.getTileEntity(getCore());
            coreHandler.energyStorage.setMaxStored(coreHandler.getMaxEnergyStored() + ADDED_STORAGE_VALUE);
        }
    }

    /**
     * Called when this tile is removed from the network, only modify the core values needed, should mirror
     * what was done when added to network
     */
    @Override
    public void removedFromNetwork() {
        EnergyHandler energyHandler = ((EnergyHandler)world.getTileEntity(getCore()));
        energyHandler.energyStorage.setMaxStored(Math.max(0, energyHandler.getMaxEnergyStored() - ADDED_STORAGE_VALUE));
    }

    /**
     * Tests if this block can join another multiblock based on core type,
     *
     * @param otherCore Core to check against
     *
     * @return True if able to join that network
     */
    @Override
    public boolean canJoinMultiBlock(IMultiBlockCore otherCore) {
        return otherCore instanceof GeneratorTile;
    }

    /*******************************************************************************************************************
     * EnergyStorageTile                                                                                               *
     *******************************************************************************************************************/

    /**
     * Scales the current stored energy used for outside bar
     * @param scale Value to scale to
     * @return Scaled percentage full
     */
    public float getPowerLevelScaled(int scale) {
        if(getCore() == null || world.getTileEntity(core) == null)
            return 0;
        EnergyBank energyStorage = ((EnergyHandler) world.getTileEntity(core)).energyStorage;
        return energyStorage.getEnergyStored() * (scale * 1F) / energyStorage.getMaxEnergyStored();
    }
}
