package com.pauljoda.modularsystems.core.tile;

import com.pauljoda.modularsystems.core.manager.TileManager;
import com.pauljoda.nucleus.common.tiles.energy.EnergyHandler;

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
public class EnergyStorageTile extends EnergyHandler {

    // Default energy size
    public static final int DEFAULT_ENERGY = 100000;

    /**
     * Main Constructor
     */
    public EnergyStorageTile() {
        super(TileManager.energy_storage);
    }

    /*******************************************************************************************************************
     * EnergyHandler                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Used to define the default size of this energy bank
     *
     * @return The default size of the energy bank
     */
    @Override
    protected int getDefaultEnergyStorageSize() {
        return DEFAULT_ENERGY;
    }

    /**
     * Is this tile an energy provider
     *
     * @return True to allow energy out
     */
    @Override
    protected boolean isProvider() {
        return true;
    }

    /**
     * Is this tile an energy reciever
     *
     * @return True to accept energy
     */
    @Override
    protected boolean isReceiver() {
        return true;
    }

    /**
     * Used to scale the current power level
     *
     * @param scale The scale to move to
     * @return A number from 0 - { @param scale} for current level
     */
    public float getPowerLevelScaled(int scale) {
        return energyStorage.getEnergyStored() * (scale * 1F) / energyStorage.getMaxEnergyStored();
    }

    /*******************************************************************************************************************
     * Tile Entity                                                                                                     *
     *******************************************************************************************************************/

    @Override
    protected void onServerTick() {
        super.onServerTick();
        receiveEnergy(1, false);
    }
}
