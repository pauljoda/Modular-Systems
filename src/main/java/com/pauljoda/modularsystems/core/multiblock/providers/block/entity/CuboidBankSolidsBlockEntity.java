package com.pauljoda.modularsystems.core.multiblock.providers.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.providers.container.CuboidBankSolidsContainer;
import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.container.IInventoryCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.Nullable;

public class CuboidBankSolidsBlockEntity extends CuboidBankBaseBlockEntity implements MenuProvider {

    protected InventoryHolder inventory;

    public CuboidBankSolidsBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.CUBOID_BANK_SOLIDS_BLOCK_ENTITY.get(), pos, state);

        inventory = new InventoryHolder() {
            @Override
            protected int getInventorySize() {
                return 27;
            }

            @Override
            protected boolean isItemValidForSlot(int index, ItemStack stack) {
                return CommonHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
            }
        };

        inventory.addCallback(new IInventoryCallback() {
            @Override
            public void onInventoryChanged(IItemHandler inventory, int slotNumber) {
                markForUpdate(Block.UPDATE_ALL);
            }
        });
    }

    /*******************************************************************************************************************
     * Solids Bank Methods                                                                                             *
     *******************************************************************************************************************/

    /**
     * Retrieves the count of fuel items in the inventory.
     *
     * @return The count of fuel items.
     */
    private int getFuelCount() {
        var count = 0;
        for (var stack : inventory.inventoryContents) {
            if (!stack.isEmpty() && stack.getCount() > 0)
                count += 1;
        }
        return count;
    }

    /**
     * Consumes fuel from the inventory and returns the fuel time.
     *
     * @param simulate if true, the fuel will not be consumed and only the fuel time will be returned
     * @return the fuel time of the consumed fuel, or 0 if no fuel is found
     */
    private int consumeFuel(boolean simulate) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            var stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getCount() > 0 && CommonHooks.getBurnTime(stack, RecipeType.SMELTING) > 0) {
                var fuelTime = CommonHooks.getBurnTime(stack, RecipeType.SMELTING);
                if(!simulate) {
                    inventory.extractItem(i, 1, false);
                    markForUpdate(Block.UPDATE_ALL);
                }
                return fuelTime;
            }
        }
        return 0;
    }

    /*******************************************************************************************************************
     * Inventory                                                                                                       *
     *******************************************************************************************************************/

    /**
     * Retrieves the item capability of the inventory.
     *
     * @return The item capability of the inventory.
     */
    public IItemHandlerModifiable getItemCapability() {
        return inventory;
    }

    /*******************************************************************************************************************
     * Fuel Provider Methods                                                                                           *
     *******************************************************************************************************************/

    /**
     * Whether the provider can provide any fuel.
     *
     * @return Can this provide
     */
    @Override
    public boolean canProvide() {
        return consumeFuel(true) > 0;
    }

    /**
     * How much fuel will be provided when consume is called. Should be mainly used for display purposes.
     *
     * @return How much would be consumed
     */
    @Override
    public double fuelProvided() {
        return consumeFuel(true);
    }

    /**
     * Consumes fuel and returns how much fuel is provided.
     *
     * @return How much was consumed
     */
    @Override
    public double consume() {
        return consumeFuel(false);
    }

    /**
     * Provides what type of fuel is used for sorting purposes.
     *
     * @return Type of provider
     */
    @Override
    public FuelProviderType type() {
        return FuelProviderType.ITEM;
    }

    /**
     * Used to scale the current power level
     *
     * @param scale The scale to move to
     * @return A number from 0 - { @param scale} for current level
     */
    @Override
    public double getPowerLevelScaled(int scale) {
        return (double) (getFuelCount() * scale) / 27;
    }

    /*******************************************************************************************************************
     * Block Entity Methods                                                                                            *
     *******************************************************************************************************************/

    /**
     * Saves additional data to the provided CompoundTag.
     *
     * @param pTag The CompoundTag to save the additional data to
     */
    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        inventory.save(pTag);
    }

    /**
     * Loads the data of this CuboidBankSolidsBlockEntity instance from the provided CompoundTag.
     *
     * @param pTag The CompoundTag containing the data to load
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        inventory.load(pTag);
    }

    /*******************************************************************************************************************
     * MenuProvider Methods                                                                                            *
     *******************************************************************************************************************/

    /**
     * Retrieves the display name of the component.
     *
     * @return The display name
     */

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.modular_systems.cuboid_bank_solids");
    }

    /**
     * Creates a menu for the given container ID, player inventory, and player.
     *
     * @param pContainerId   The container ID.
     * @param pPlayerInventory   The player inventory.
     * @param pPlayer   The player.
     * @return The created menu.
     */
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CuboidBankSolidsContainer(pContainerId, pPlayerInventory, this);
    }
}
