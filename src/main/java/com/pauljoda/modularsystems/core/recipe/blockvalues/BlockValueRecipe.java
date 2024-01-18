package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.recipe.blockvalues.BlockContainerWrapper;
import com.pauljoda.modularsystems.core.math.collections.Calculation;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public record BlockValueRecipe(ResourceLocation inputBlock,
                               Calculation speedCalculation,
                               Calculation efficiencyCalculation,
                               Calculation multiplicityCalculation) implements Recipe<BlockContainerWrapper> {

    /*******************************************************************************************************************
     * Recipe                                                                                                          *
     *******************************************************************************************************************/

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param pContainer
     * @param pLevel
     */
    @Override
    public boolean matches(BlockContainerWrapper pContainer, Level pLevel) {
        return pContainer.heldBlock().getBlock() == BuiltInRegistries.BLOCK.get(inputBlock);
    }

    /**
     * Retrieves the input block associated with this recipe.
     *
     * @return The input block.
     */
    @Override
    public ResourceLocation inputBlock() {
        return inputBlock;
    }

    /**
     * Retrieves the Calculation object associated with this recipe.
     *
     * @return The Calculation object.
     */
    @Override
    public Calculation speedCalculation() {
        return speedCalculation;
    }

    /**
     * Retrieves the efficiency calculation associated with this recipe.
     *
     * @return The efficiency calculation.
     */
    @Override
    public Calculation efficiencyCalculation() {
        return efficiencyCalculation;
    }

    /**
     * Retrieves the Calculation object associated with this recipe.
     *
     * @return The Calculation object.
     */
    @Override
    public Calculation multiplicityCalculation() {
        return multiplicityCalculation;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.BLOCK_VALUE_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.BLOCK_VALUE_RECIPE_TYPE.get();
    }

    /*******************************************************************************************************************
     * Not Needed                                                                                                      *
     *******************************************************************************************************************/

    @Override
    public ItemStack assemble(BlockContainerWrapper pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param pWidth
     * @param pHeight
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }
}
