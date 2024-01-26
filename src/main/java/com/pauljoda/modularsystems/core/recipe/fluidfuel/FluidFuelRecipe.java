package com.pauljoda.modularsystems.core.recipe.fluidfuel;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.nucleus.recipe.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public record FluidFuelRecipe(@NonNull FluidStack input, int burntime) implements CustomRecipe<FluidStackContainerWrapper> {

    @Override
    public FluidStack input() {
        return input;
    }

    public int burntime() {
        return burntime;
    }

    /**
     * Tests if the given FluidStackContainerWrapper matches the provided Level.
     *
     * @param fluidStackContainerWrapper The FluidStackContainerWrapper to be tested.
     * @param level                      The Level to be compared with the FluidStackContainerWrapper.
     * @return True if the FluidStackContainerWrapper's fluid stack is equal to the input fluid stack and its amount is greater than or equal to the input amount, otherwise false
     *.
     */

    @Override
    public boolean matches(FluidStackContainerWrapper fluidStackContainerWrapper, @NotNull Level level) {
        return fluidStackContainerWrapper.fluidStack().isFluidEqual(input) &&
                fluidStackContainerWrapper.fluidStack().getAmount() >= input.getAmount();
    }

    /**
     * Tests if this is valid fuel, regardless of amount, for inserting
     * @param stack Stack to check
     * @return True if valid fluid
     */
    public boolean isValidFluid(@NonNull FluidStack stack) {
        return stack.isFluidEqual(input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.FLUID_FUEL_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.FLUID_FUEL_RECIPE_TYPE.get();
    }
}
