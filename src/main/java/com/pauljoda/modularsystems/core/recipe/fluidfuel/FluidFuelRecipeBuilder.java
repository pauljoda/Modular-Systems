package com.pauljoda.modularsystems.core.recipe.fluidfuel;

import com.pauljoda.nucleus.recipe.CustomRecipeBuilder;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidFuelRecipeBuilder implements CustomRecipeBuilder<FluidFuelRecipe> {

    /**
     * Represents a fluid stack.
     */
    private final FluidStack fluid;

    /**
     * The burn time of a fluid fuel recipe.
     */
    private final int burnTime;

    /**
     * This class represents a builder for Fluid Fuel recipes.
     * A Fluid Fuel recipe is a recipe that consumes a specific FluidStack as fuel in a certain burn time.
     *
     * The FluidFuelRecipeBuilder class provides a constructor to create a new FluidFuelRecipeBuilder object.
     *
     * @param fluidStack The FluidStack object representing the fuel to be consumed.
     * @param burn The burn time of the fuel in ticks.
     */
    public FluidFuelRecipeBuilder(FluidStack fluidStack, int burn) {
        fluid = fluidStack;
        burnTime = burn;
    }

    /**
     * Creates a new instance of the `FluidFuelRecipe` class using the provided `fluid` and `burnTime` values.
     *
     * @return A new `FluidFuelRecipe` object.
     */
    @Override
    public FluidFuelRecipe createRecipe() {
        return new FluidFuelRecipe(fluid, burnTime);
    }

    /**
     * Creates a FluidFuelRecipeBuilder instance using the provided FluidStack and burn time.
     *
     * @param stack The FluidStack representing the fuel.
     * @param burn The burn time of the fuel.
     * @return A FluidFuelRecipeBuilder object.
     */
    public static FluidFuelRecipeBuilder of(FluidStack stack, int burn) {
        return new FluidFuelRecipeBuilder(stack, burn);
    }
}
