package com.pauljoda.modularsystems.core.recipe.stonework;

import com.pauljoda.nucleus.recipe.CustomRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class StoneWorkRecipeBuilder implements CustomRecipeBuilder<StoneWorkRecipe> {

    /**
     * The private final variable input represents the ingredient used in a StoneWorkRecipe.
     * It is used to check if a recipe matches the current crafting inventory and to assemble the recipe.
     * The input can be set using the StoneWorkRecipeBuilder class.
     * The input is passed to the StoneWorkRecipe constructor and stored as a private final field.
     */
    private final Ingredient input;
    /**
     * Represents the result of a StoneWorkRecipe.
     */
    private final Item result;

    /**
     * StoneWorkRecipeBuilder is a class used to build stone work recipes.
     *
     * @param in  the input ingredient for the recipe.
     * @param out  the output item for the recipe.
     */
    public StoneWorkRecipeBuilder(Ingredient in, ItemLike out) {
        this.input = in;
        this.result = out.asItem();
    }

    /**
     * Creates a StoneWorkRecipe.
     *
     * @return The StoneWorkRecipe object that is created.
     */
    @Override
    public StoneWorkRecipe createRecipe() {
        return null;
    }

    /**
     * Creates a StoneWorkRecipeBuilder object with the specified parameters.
     *
     * @param in The input ingredient for the recipe.
     * @param out The output item for the recipe.
     * @return A new StoneWorkRecipeBuilder instance.
     */
    public static StoneWorkRecipeBuilder of(Ingredient in, ItemLike out) {
        return new StoneWorkRecipeBuilder(in, out);
    }
}
