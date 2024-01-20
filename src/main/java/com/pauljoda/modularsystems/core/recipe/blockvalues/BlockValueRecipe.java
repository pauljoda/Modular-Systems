package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.math.collections.Calculation;
import com.pauljoda.nucleus.recipe.CustomRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record BlockValueRecipe(Ingredient inputBlock,
                               Calculation speedCalculation,
                               Calculation efficiencyCalculation,
                               Calculation multiplicityCalculation) implements CustomRecipe<BlockContainerWrapper> {

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
        return inputBlock.test(new ItemStack(pContainer.heldBlock().getBlock()));
    }

    /**
     * Retrieves the input block associated with this recipe.
     *
     * @return The input block.
     */
    @Override
    public Ingredient inputBlock() {
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
}
