package com.pauljoda.modularsystems.furnace.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.math.function.BlockCountFunction;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

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
        return furnaceRecipeCache.getRecipeFor(new RecipeWrapper(this),
                getLevel()).get().value().getResultItem(getLevel().registryAccess());
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

    }

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

    @Override
    protected boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return i == INPUT_SLOT;
    }

    @Override
    public void setVariable(int i, double v) {

    }

    @Override
    public Double getVariable(int i) {
        return null;
    }
}
