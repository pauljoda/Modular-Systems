package com.pauljoda.modularsystems.core.recipe.fluidfuel;

import com.pauljoda.nucleus.recipe.ValueContainerWrapper;
import net.neoforged.neoforge.fluids.FluidStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public record FluidStackContainerWrapper(@NonNull FluidStack fluidStack) implements ValueContainerWrapper {
    @NonNull
    @Override
    public FluidStack fluidStack() {
        return fluidStack;
    }
}
