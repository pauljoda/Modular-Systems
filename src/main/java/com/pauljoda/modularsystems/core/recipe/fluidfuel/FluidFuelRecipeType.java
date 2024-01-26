package com.pauljoda.modularsystems.core.recipe.fluidfuel;

import com.pauljoda.modularsystems.core.lib.Reference;
import net.minecraft.world.item.crafting.RecipeType;

public class FluidFuelRecipeType implements RecipeType<FluidFuelRecipe> {
    @Override
    public String toString() {
        return String.format("%s:fluidfuel", Reference.MOD_ID);
    }
}
