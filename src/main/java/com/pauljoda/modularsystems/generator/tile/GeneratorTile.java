package com.pauljoda.modularsystems.generator.tile;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.manager.TileManager;
import com.pauljoda.modularsystems.generator.container.GeneratorContainer;
import com.pauljoda.nucleus.common.tiles.energy.EnergyAndItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeHooks;

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
public class GeneratorTile extends EnergyAndItemHandler implements INamedContainerProvider {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    protected static final int MAX_STORAGE = 32000;

    /*******************************************************************************************************************
     * Constructor                                                                                                     *
     *******************************************************************************************************************/

    public GeneratorTile() {
        super(TileManager.generator);
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
        return MAX_STORAGE;
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
     * IItemHandler                                                                                                    *
     *******************************************************************************************************************/

    /**
     * The initial size of the inventory
     *
     * @return How big to make the inventory on creation
     */
    @Override
    protected int getInventorySize() {
        return 1;
    }

    /**
     * Used to define if an item is valid for a slot
     *
     * @param index The slot id
     * @param stack The stack to check
     * @return True if you can put this there
     */
    protected boolean isItemValidForSlot(int index, ItemStack stack) {
        return inventoryContents.get(0).isEmpty() && index == 0 && ForgeHooks.getBurnTime(stack) > 0;
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
