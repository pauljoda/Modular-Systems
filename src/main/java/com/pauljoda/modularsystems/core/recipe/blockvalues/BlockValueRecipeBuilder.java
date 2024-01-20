package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.pauljoda.modularsystems.core.math.collections.Calculation;
import com.pauljoda.nucleus.recipe.CustomRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class BlockValueRecipeBuilder implements CustomRecipeBuilder<BlockValueRecipe> {

    /**
     * Represents an input block for a recipe builder.
     * The input block is used to match against the crafting inventory of a {@link BlockContainerWrapper}.
     */
    private final Ingredient inputBlock;
    /**
     * Represents the speed for a calculation.
     */
    private final Calculation speed;
    /**
     *
     */
    private final Calculation efficiency;
    /**
     * Represents a Calculation object used in various recipe calculations.
     */
    private final Calculation multiplicity;

    /**
     * This method creates a BlockValueRecipeBuilder object with the given parameters.
     *
     * @param in The input block.
     * @param s The speed calculation.
     * @param e The efficiency calculation.
     * @param m The multiplicity calculation.
     */
    public BlockValueRecipeBuilder(Block in, Calculation s, Calculation e, Calculation m) {
        this.inputBlock = Ingredient.of(in);
        this.speed = s;
        this.efficiency = e;
        this.multiplicity = m;
    }

    /**
     * This method is a constructor for the BlockValueRecipeBuilder class. It is used to create a new BlockValueRecipeBuilder object with the given parameters.
     *
     * @param in The input block or item for the recipe.
     * @param s The speed calculation for the recipe.
     * @param e The efficiency calculation for the recipe.
     * @param m The multiplicity calculation for the recipe.
     */
    public BlockValueRecipeBuilder(TagKey<Item> in, Calculation s, Calculation e, Calculation m) {
        this.inputBlock = Ingredient.of(in);
        this.speed = s;
        this.efficiency = e;
        this.multiplicity = m;
    }

    /**
     * Creates a recipe.
     *
     * @return The created recipe.
     */
    @Override
    public BlockValueRecipe createRecipe() {
        return new BlockValueRecipe(inputBlock, speed, efficiency, multiplicity);
    }

    /**
     * Creates a {@code BlockValueRecipeBuilder} instance with the given parameters.
     *
     * @param block        The block to calculate the value for.
     * @param speed        The calculation for the speed.
     * @param efficiency   The calculation for the efficiency.
     * @param multiplicity The calculation for the multiplicity.
     * @return A new {@code BlockValueRecipeBuilder} instance.
     */
    public static BlockValueRecipeBuilder of(Block block, Calculation speed, Calculation efficiency, Calculation multiplicity) {
        return new BlockValueRecipeBuilder(block, speed, efficiency, multiplicity);
    }

    /**
     * Creates a new BlockValueRecipeBuilder instance with the provided parameters.
     *
     * @param block        The block to calculate the value for.
     * @param speed        The calculation for the speed.
     * @param efficiency   The calculation for the efficiency.
     * @param multiplicity The calculation for the multiplicity.
     * @return The created BlockValueRecipeBuilder instance.
     */
    public static BlockValueRecipeBuilder of(TagKey<Item> block, Calculation speed, Calculation efficiency, Calculation multiplicity) {
        return new BlockValueRecipeBuilder(block, speed, efficiency, multiplicity);
    }
}
