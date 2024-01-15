package com.pauljoda.modularsystems.furnace.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.math.function.BlockCountFunction;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.modularsystems.core.registry.BlockValueRegistry;
import com.pauljoda.modularsystems.furnace.container.FurnaceCoreContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

public class FurnaceCoreBlockEntity extends AbstractCuboidCoreBlockEntity {

    RecipeManager.CachedCheck<Container, SmeltingRecipe> furnaceRecipeCache;

    /**
     * Initializes a new instance of the AbstractCuboidCore class.
     *
     * @param pos              The position of the block.
     * @param state            The state of the block.
     */
    public FurnaceCoreBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.FURNACE_CORE_BLOCK_ENTITY.get(), pos, state);

        furnaceRecipeCache = RecipeManager.createCheck(RecipeType.SMELTING);
    }

    /**
     * Applies a recipe to the given stack.
     *
     * @param stack The item stack to apply the recipe to.
     * @return The resulting item stack after applying the recipe.
     */
    @Override
    public ItemStack recipe(ItemStack stack) {
        return furnaceRecipeCache.getRecipeFor(new RecipeWrapper(this.getItemCapability()),
                getLevel()).isPresent() ?
                furnaceRecipeCache.getRecipeFor(new RecipeWrapper(this.getItemCapability()),
                        getLevel()).get().value().getResultItem(getLevel().registryAccess()) :
                ItemStack.EMPTY;
    }

    /**
     * Checks if a given block state is banned.
     *
     * @param blockState The block state to check.
     * @return true if the block is banned, false otherwise.
     */
    @Override
    public boolean isBlockBanned(BlockState blockState) {
        return blockState.hasBlockEntity();
    }

    /**
     * Generates values using the provided BlockCountFunction.
     *
     * @param function The BlockCountFunction used to generate values.
     */
    @Override
    public void generateValues(BlockCountFunction function) {
        // Calculate from Blocks
        for (var block : function.getBlockSet()) {
            if(BlockValueRegistry.INSTANCE.isBlockRegistered(block)) {
                values.addSpeed(BlockValueRegistry.INSTANCE.getBlockSpeedValue(block, function.getBlockCount(block)));
                values.addEfficiency(BlockValueRegistry.INSTANCE.getBlockEfficiencyValue(block, function.getBlockCount(block)));
                values.addMultiplicity(BlockValueRegistry.INSTANCE.getBlockMultiplicityValue(block, function.getBlockCount(block)));
            }
        }

        // Calculate from Tags
        for (var tag : function.getTagSet()) {
            if(BlockValueRegistry.INSTANCE.hasBlockTagRegistered(tag)) {
                values.addSpeed(BlockValueRegistry.INSTANCE.getTagSpeedValue(tag, function.getTagCount(tag)));
                values.addEfficiency(BlockValueRegistry.INSTANCE.getTagEfficiencyValue(tag, function.getTagCount(tag)));
                values.addMultiplicity(BlockValueRegistry.INSTANCE.getTagMultiplicityValue(tag, function.getTagCount(tag)));
            }
        }
    }

    /*******************************************************************************************************************
     * Inventory Methods                                                                                            *
     *******************************************************************************************************************/

    /**
     * Retrieves the redstone output of the cuboid core.
     * <p>
     * Value 0 - 16
     *
     * @return The redstone output value.
     */
    @Override
    public int getRedstoneOutput() {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(this);
    }

    /**
     * Checks if the given item is valid for the specified slot.
     *
     * @param i The slot index to check.
     * @param itemStack The ItemStack to be checked.
     * @return true if the item is valid for the slot, false otherwise.
     */
    @Override
    protected boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == INPUT_SLOT;
    }

    /*******************************************************************************************************************
     * Syncable Methods                                                                                                *
     *******************************************************************************************************************/

    @Override
    public void setVariable(int i, double v) {

    }

    @Override
    public Double getVariable(int i) {
        return null;
    }

    /**
     * Retrieves the display name of the component.
     *
     * @return The display name of the component.
     */

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.modular_systems.furnace_core");
    }

    /**
     * Creates a menu for the given container ID, player inventory, and player.
     *
     * @param pContainerId The container ID.
     * @param pPlayerInventory The player inventory.
     * @param pPlayer The player.
     * @return The created AbstractContainerMenu.
     */
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FurnaceCoreContainer(pContainerId, pPlayerInventory, this, coreData);
    }
}
