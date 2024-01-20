package com.pauljoda.modularsystems.core.registry;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.recipe.blockvalues.BlockContainerWrapper;
import com.pauljoda.modularsystems.core.recipe.blockvalues.BlockValueRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockValueRegistry {

    // Public Instance
    public static BlockValueRegistry INSTANCE = new BlockValueRegistry();

    /*******************************************************************************************************************
     * Maps                                                                                                            *
     *******************************************************************************************************************/
    // Blocks
    protected final RecipeManager.CachedCheck<BlockContainerWrapper, BlockValueRecipe> blockValues;
    /**
     * The BlockValueRegistry class is responsible for initializing and managing
     * the block and tag values for the module.
     */

    public BlockValueRegistry() {
        blockValues = RecipeManager.createCheck(Registration.BLOCK_VALUE_RECIPE_TYPE.get());
    }

    /*******************************************************************************************************************
     * Registry Functions                                                                                              *
     *******************************************************************************************************************/

    /**
     * Checks if the given block is registered.
     *
     * @param block The block state to check.
     * @return True if the block is registered, false otherwise.
     */

    public boolean isBlockRegistered(BlockState block, Level level) {
        return blockValues.getRecipeFor(new BlockContainerWrapper(block), level).isPresent();
    }

    /**
     * Checks if the given block is registered.
     *
     * @param block The block to check.
     * @param level The level to check in.
     * @return True if the block is registered, false otherwise.
     */
    public boolean isBlockRegistered(ResourceLocation block, Level level) {
        return isBlockRegistered(BuiltInRegistries.BLOCK.get(block).defaultBlockState(), level);
    }

    /**
     * Retrieves the BlockValueRecipe associated with a given block and level.
     *
     * @param block The BlockState to get the BlockValueRecipe for.
     * @param level The Level to check in.
     * @return The RecipeHolder<BlockValueRecipe> object containing the BlockValueRecipe associated with the block and level.
     *         Returns null if no recipe is found.
     */
    public BlockValueRecipe getBlockValueRecipe(BlockState block, Level level) {
        return blockValues.getRecipeFor(new BlockContainerWrapper(block), level).orElse(null).value();
    }

    /**
     * Retrieves the BlockValueRecipe for the given block state and level.
     *
     * @param block The block state to get the recipe for.
     * @param level The level to get the recipe in.
     * @return The BlockValueRecipe corresponding to the block state and level, or null if not found.
     */
    public BlockValueRecipe getBlockValueRecipe(ResourceLocation block, Level level) {
        return getBlockValueRecipe(BuiltInRegistries.BLOCK.get(block).defaultBlockState(), level);
    }

    /**
     * Retrieves the speed value for a given block and count.
     *
     * @param block The block to get the value for.
     * @param count The count of blocks.
     * @return The speed value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getBlockSpeedValue(ResourceLocation block, Level level, int count) {
        if(isBlockRegistered(block, level)) {
            var values = getBlockValueRecipe(block, level);
            return values == null ? 0.0 : values.speedCalculation().F(count);
        }
        return 0.0;
    }

    /**
     * Retrieves the efficiency value for a given block and count.
     *
     * The efficiency value is calculated based on the registered block values.
     *
     * @param block The block to get the value for.
     * @param count The count of blocks.
     * @return The efficiency value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getBlockEfficiencyValue(ResourceLocation block, Level level, int count) {
        if(isBlockRegistered(block, level)) {
            var values = getBlockValueRecipe(block, level);
            return values == null ? 0.0 : values.efficiencyCalculation().F(count);
        }
        return 0.0;
    }

    /**
     * Retrieves the block multiplicity value for a given block, level, and block count.
     *
     * @param block The block to get the multiplicity value for.
     * @param level The level to check in.
     * @param count The count of blocks.
     * @return The multiplicity value for the block, level, and count. Returns 0.0 if the block is not registered or the multiplicity value is not found.
     */
    public double getBlockMultiplicityValue(ResourceLocation block, Level level, int count) {
        if(isBlockRegistered(block, level)) {
            var values = getBlockValueRecipe(block, level);
            return values == null ? 0.0 : values.multiplicityCalculation().F(count);
        }
        return 0.0;
    }

    /**
     * Retrieves the speed value for a given block state and count.
     *
     * @param state The block state to get the value for.
     * @param level The level to get the value in.
     * @param count The count of blocks.
     * @return The speed value for the block state and count. Returns 0.0 if the block is not registered.
     */
    public double getSpeedValue(BlockState state, Level level, int count) {
        return isBlockRegistered(state, level) ?
                getBlockSpeedValue(BuiltInRegistries.BLOCK.getKey(state.getBlock()), level, count) :
                0.0D;
    }

    /**
     * Retrieves the efficiency value for a given block and count.
     *
     * The efficiency value is calculated based on the registered block values.
     *
     * @param state The block state to get the value for.
     * @param level The level to check in.
     * @param count The count of blocks.
     * @return The efficiency value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getEfficiencyValue(BlockState state, Level level, int count) {
        return isBlockRegistered(state, level) ?
                getBlockEfficiencyValue(BuiltInRegistries.BLOCK.getKey(state.getBlock()), level, count) :
                0.0D;
    }

    /**
     * Returns the multiplicity value for a given block state, level, and count.
     *
     * @param state The block state to get the value for.
     * @param level The level to get the value in.
     * @param count The count of blocks.
     * @return The multiplicity value for the block state, level, and count. Returns 0.0 if the block is not registered.
     */
    public double getMultiplicityValue(BlockState state, Level level, int count) {
        return isBlockRegistered(state, level) ?
                getBlockMultiplicityValue(BuiltInRegistries.BLOCK.getKey(state.getBlock()), level, count) :
                0.0D;
    }
}
