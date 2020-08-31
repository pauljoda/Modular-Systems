package com.pauljoda.modularsystems.energy.tile;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.manager.TileManager;
import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockCore;
import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockExpansion;
import com.pauljoda.modularsystems.core.multiblock.collections.MultiBlockData;
import com.pauljoda.modularsystems.core.tile.EnergyStorageTile;
import com.pauljoda.modularsystems.energy.container.GeneratorContainer;
import com.pauljoda.nucleus.common.tiles.energy.EnergyHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/20
 */
public class GeneratorTile extends EnergyHandler implements IMultiBlockCore, ICapabilityProvider, INamedContainerProvider {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    // Default storage
    protected static final int DEFAULT_STORAGE = 32000;

    // Max expansions allowed in this multiblock
    protected static final int MAX_EXPANSIONS = 50;

    // Instance of multiblock data
    protected MultiBlockData multiBlockData;

    /*******************************************************************************************************************
     * Constructor                                                                                                     *
     *******************************************************************************************************************/

    public GeneratorTile() {
        super(TileManager.generator);
        multiBlockData = new MultiBlockData();
    }

    /*******************************************************************************************************************
     * IMultiBlockCore                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Called when an expansion is added to this core, add logic to modify here, expansion will not
     *
     * @param expansion Expansion
     */
    @Override
    public void addExpansion(IMultiBlockExpansion expansion) {
        multiBlockData.addNode(expansion.getPos());
    }

    /**
     * Returns an instance of this multiblock's data object
     *
     * @return Instance of data object
     */
    @Override
    public MultiBlockData getMultiBlockData() {
        return multiBlockData;
    }

    /**
     * Tests if the given expansion can join this network, check for things like size and type
     *
     * @param expansion The expansion wanting to join
     *
     * @return True if allowed to connect
     */
    @Override
    public boolean isExpansionAllowed(IMultiBlockExpansion expansion) {
        return multiBlockData.getNodes().size() < MAX_EXPANSIONS && expansion instanceof EnergyStorageTile;
    }

    /**
     * Destroy the network
     */
    @Override
    public void destroyMultiblock() {
        multiBlockData.destroyNetwork(world);
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
        return DEFAULT_STORAGE;
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
     * Is this tile an energy receiver
     *
     * @return True to accept energy
     */
    @Override
    protected boolean isReceiver() {
        return false;
    }

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        multiBlockData.read(compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        multiBlockData.write(compound);
        return compound;
    }

    /*******************************************************************************************************************
     * INamedContainerProvider                                                                                         *
     *******************************************************************************************************************/

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(Reference.MOD_ID + ".generator");
    }

    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new GeneratorContainer(windowID, playerInventory, this);
    }
}
