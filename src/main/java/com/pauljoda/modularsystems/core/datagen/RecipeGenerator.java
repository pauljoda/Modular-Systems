package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.recipe.blockvalues.BlockValueRecipeBuilder;
import com.pauljoda.modularsystems.core.math.collections.Calculation;
import com.pauljoda.modularsystems.core.recipe.stonework.StoneWorkRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider {

    public RecipeGenerator(PackOutput generatorIn, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generatorIn, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput outputs) {
        // Items -------------------------------------------------------------------------------------------------------


        // Blocks ------------------------------------------------------------------------------------------------------
        // Furnace Core
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.FURNACE_CORE_BLOCK_ITEM.get())
                .pattern("ISI")
                .pattern("SES")
                .pattern("ISI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.STONE)
                .define('E', Blocks.FURNACE)
                .unlockedBy("has_furnace", has(Blocks.FURNACE))
                .save(outputs);

        // Furnace Core
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.STONE_WORK_CORE_BLOCK_ITEM.get())
                .pattern("ISI")
                .pattern("SES")
                .pattern("ISI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.STONE)
                .define('E', Blocks.BLAST_FURNACE)
                .unlockedBy("has_furnace", has(Blocks.BLAST_FURNACE))
                .save(outputs);

        // Banks
        // Solids
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.CUBOID_BANK_SOLIDS_BLOCK_ITEM.get())
                .pattern("ISI")
                .pattern("SES")
                .pattern("ISI")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Tags.Items.STONE)
                .define('E', Blocks.CHEST)
                .unlockedBy("has_chest", has(Blocks.CHEST))
                .save(outputs);

        // IO
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Registration.CUBOID_IO_BLOCK_ITEM.get())
                .pattern("III")
                .pattern("SES")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('S', Blocks.HOPPER)
                .define('E', Blocks.CHEST)
                .unlockedBy("has_chest", has(Blocks.CHEST))
                .save(outputs);

        // Stone Works
        stoneWork(outputs, Tags.Items.COBBLESTONE, Blocks.STONE, "all_cobble_to_stone");

        // region Block Values
        // region By Block
        blockValue(outputs,
                Blocks.BRICKS,
                new Calculation(5, 0, 100),
                new Calculation(5, 0, 500),
                Calculation.FLAT,
                "brick_block");
        blockValue(outputs,
                Blocks.STONE_BRICKS,
                new Calculation(1, 0, 100),
                new Calculation(10, 0, 500),
                Calculation.FLAT,
                "stone_brick");
        blockValue(outputs,
                Blocks.NETHER_BRICKS,
                new Calculation(-1, -30, 0),
                new Calculation(-10, -200, 0),
                Calculation.FLAT,
                "nether_brick");
        // endregion

        // region By Tag
        blockValue(outputs,
                Tags.Items.COBBLESTONE,
                new Calculation(-0.10, -50, 1),
                new Calculation(-1, -500, 1),
                Calculation.FLAT,
                "cobblestone_tag");
        blockValue(outputs,
                Tags.Items.STONE,
                new Calculation(-0.30, -40, 0),
                Calculation.FLAT,
                Calculation.FLAT,
                "stone_tag");
        blockValue(outputs,
                Tags.Items.SANDSTONE,
                new Calculation(-0.10, -50, 1),
                new Calculation(-1, -500, 1),
                Calculation.FLAT,
                "sandstone_tag");
        blockValue(outputs,
                Tags.Items.NETHERRACK,
                new Calculation(-2, -100, 0),
                new Calculation(-20, -800, 0),
                Calculation.FLAT,
                "netherrack_tag");
        blockValue(outputs,
                Tags.Items.OBSIDIAN,
                new Calculation(10, 0, 50),
                new Calculation(20, 0, 1600),
                Calculation.FLAT,
                "obsidian_tag");
        blockValue(outputs,
                Tags.Items.END_STONES,
                new Calculation(-0.5, -75, 0),
                new Calculation(-10, -250, -50),
                new Calculation(0.10, 0, 5),
                "end_stone_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_COAL,
                new Calculation(1, 0, 100),
                new Calculation(5, 0, 300),
                Calculation.FLAT,
                "coal_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_IRON,
                new Calculation(10, 0, 100),
                new Calculation(10, 0, 500),
                Calculation.FLAT,
                "iron_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_COPPER,
                new Calculation(10, 0, 200),
                new Calculation(5, 0, 1000),
                Calculation.FLAT,
                "copper_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_EMERALD,
                Calculation.FLAT,
                Calculation.FLAT,
                new Calculation(1, 0, 5),
                "emerald_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_REDSTONE,
                new Calculation(-2, -50, 1),
                new Calculation(-50, -500, 1),
                Calculation.FLAT,
                "redstone_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_QUARTZ,
                Calculation.FLAT,
                new Calculation(-15, -500, 0),
                new Calculation(0.10, 0, 3),
                "quartz_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_QUARTZ,
                new Calculation(2, 0, 50),
                new Calculation(20, 0, 500),
                new Calculation(0.25, 0, 3),
                "gold_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_DIAMOND,
                new Calculation(-8, -100, 0),
                new Calculation(15, 0, 300),
                new Calculation(0.50, 0, 3),
                "diamond_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_NETHERITE,
                new Calculation(-10, -150, 0),
                new Calculation(20, 0, 200),
                new Calculation(1, 0, 5),
                "netherite_block_tag");
        blockValue(outputs,
                Tags.Items.STORAGE_BLOCKS_LAPIS,
                new Calculation(-1, -25, 0),
                new Calculation(2, 0, 100),
                new Calculation(0.1, 0, 2),
                "lapis_block_tag");
        // endregion
        // endregion
    }

    /**
     * This method creates a stone work recipe and saves it using the provided parameters.
     *
     * @param outputs the RecipeOutput instance to save the recipe to
     * @param input   the input item for the recipe
     * @param output  the output item for the recipe
     * @param name    the name of the recipe
     */
    protected static void stoneWork(RecipeOutput outputs, ItemLike input, ItemLike output, String name) {
        StoneWorkRecipeBuilder.of(Ingredient.of(input), output)
                .save(outputs, new ResourceLocation(Reference.MOD_ID, String.format("stone_work/%s", name)));
    }

    /**
     * A helper method for creating stone work recipes.
     *
     * @param outputs the recipe output handler
     * @param input   the ingredient required for the recipe
     * @param output  the resulting item of the recipe
     * @param name    the name of the recipe
     */
    protected static void stoneWork(RecipeOutput outputs, TagKey<Item> input, ItemLike output, String name) {
        StoneWorkRecipeBuilder.of(Ingredient.of(input), output)
                .save(outputs, new ResourceLocation(Reference.MOD_ID, String.format("stone_work/%s", name)));
    }

    /**
     * Adds a block value recipe to the provided output with the given parameters.
     *
     * @param output       The recipe output to add the recipe to.
     * @param block        The block to calculate the value for.
     * @param speed        The calculation for the speed.
     * @param efficiency   The calculation for the efficiency.
     * @param multiplicity The calculation for the multiplicity.
     * @param name         The name of the recipe.
     */
    protected static void blockValue(RecipeOutput output,
                                     Block block,
                                     Calculation speed, Calculation efficiency, Calculation multiplicity,
                                     String name) {
        BlockValueRecipeBuilder.of(block, speed, efficiency, multiplicity)
                .save(output, new ResourceLocation(Reference.MOD_ID, String.format("block_values/%s", name)));
    }

    /**
     * Adds a block value recipe to the provided output with the given parameters.
     *
     * @param output       The recipe output to add the recipe to.
     * @param block        The block to calculate the value for.
     * @param speed        The calculation for the speed.
     * @param efficiency   The calculation for the efficiency.
     * @param multiplicity The calculation for the multiplicity.
     * @param name         The name of the recipe.
     */
    protected static void blockValue(RecipeOutput output,
                                     TagKey<Item> block,
                                     Calculation speed, Calculation efficiency, Calculation multiplicity,
                                     String name) {
        BlockValueRecipeBuilder.of(block, speed, efficiency, multiplicity)
                .save(output, new ResourceLocation(Reference.MOD_ID, String.format("block_values/%s", name)));
    }
}