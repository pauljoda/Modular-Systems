package com.pauljoda.modularsystems.core.recipe.stonework;

import com.pauljoda.modularsystems.core.lib.Reference;
import net.minecraft.world.item.crafting.RecipeType;

public class StoneWorkRecipeType implements RecipeType<StoneWorkRecipe> {

    @Override
    public String toString() {
        return String.format("%s:stonework", Reference.MOD_ID);
    }
}
