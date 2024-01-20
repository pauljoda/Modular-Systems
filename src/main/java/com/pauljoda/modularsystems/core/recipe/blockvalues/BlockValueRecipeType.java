package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.pauljoda.modularsystems.core.lib.Reference;
import net.minecraft.world.item.crafting.RecipeType;

public class BlockValueRecipeType implements RecipeType<BlockValueRecipe> {
    @Override
    public String toString() {
        return String.format("%s:block_values", Reference.MOD_ID);
    }
}
