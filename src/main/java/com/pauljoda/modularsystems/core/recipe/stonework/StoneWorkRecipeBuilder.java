package com.pauljoda.modularsystems.core.recipe.stonework;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public class StoneWorkRecipeBuilder implements RecipeBuilder {

    private final Ingredient input;
    private final Item result;

    public StoneWorkRecipeBuilder(Ingredient in, ItemLike out) {
        this.input = in;
        this.result = out.asItem();
    }

    public static StoneWorkRecipeBuilder of(Ingredient in, ItemLike out) {
        return new StoneWorkRecipeBuilder(in, out);
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return null;
    }

    public StoneWorkRecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        return this;
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        StoneWorkRecipe recipe = new StoneWorkRecipe(input, new ItemStack(result));
        pRecipeOutput.accept(pId, recipe, null);
    }
}
