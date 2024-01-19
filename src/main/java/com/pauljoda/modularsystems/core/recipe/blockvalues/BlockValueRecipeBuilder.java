package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.pauljoda.modularsystems.core.math.collections.Calculation;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class BlockValueRecipeBuilder implements RecipeBuilder {

    private final Ingredient inputBlock;
    private final Calculation speed;
    private final Calculation efficiency;
    private final Calculation multiplicity;

    public BlockValueRecipeBuilder(Block in, Calculation s, Calculation e, Calculation m) {
        this.inputBlock = Ingredient.of(in);
        this.speed = s;
        this.efficiency = e;
        this.multiplicity = m;
    }

    public BlockValueRecipeBuilder(TagKey<Item> in, Calculation s, Calculation e, Calculation m) {
        this.inputBlock = Ingredient.of(in);
        this.speed = s;
        this.efficiency = e;
        this.multiplicity = m;
    }

    public static BlockValueRecipeBuilder of(Block block, Calculation speed, Calculation efficiency, Calculation multiplicity) {
        return new BlockValueRecipeBuilder(block, speed, efficiency, multiplicity);
    }

    public static BlockValueRecipeBuilder of(TagKey<Item> block, Calculation speed, Calculation efficiency, Calculation multiplicity) {
        return new BlockValueRecipeBuilder(block, speed, efficiency, multiplicity);
    }

    @Override
    public void save(RecipeOutput pRecipeOutput, ResourceLocation pId) {
        var recipe = new BlockValueRecipe(inputBlock, speed, efficiency, multiplicity);
        pRecipeOutput.accept(pId, recipe, null);
    }

    @Override
    public RecipeBuilder unlockedBy(String pName, Criterion<?> pCriterion) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return null;
    }
}
