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

        // Block Values
        blockValue(outputs,
                Blocks.REDSTONE_BLOCK,
                new Calculation(-2, -50, 1),
                new Calculation(-50, -500, 1),
                Calculation.FLAT,
                "redstone_block");
        blockValue(outputs,
                Blocks.QUARTZ_BLOCK,
                Calculation.FLAT,
                new Calculation(-15,-500, 0),
                new Calculation(0.10, 0, 3),
                "quartz_block");
        blockValue(outputs,
                Blocks.GOLD_BLOCK,
                new Calculation(2, 0, 50),
                new Calculation(20, 0, 500),
                new Calculation(0.25, 0, 3),
                "gold_block");
        blockValue(outputs,
                Blocks.DIAMOND_BLOCK,
                new Calculation(-8, -100, 0),
                new Calculation(15, 0, 300),
                new Calculation(0.50,0, 3),
                "diamond_block");
        blockValue(outputs,
                Blocks.NETHERITE_BLOCK,
                new Calculation(-10, -150, 0),
                new Calculation(20, 0, 200),
                new Calculation(1, 0, 5),
                "netherite_block");
        blockValue(outputs,
                Blocks.LAPIS_BLOCK,
                new Calculation(-1, -25, 0),
                new Calculation(2, 0, 100),
                new Calculation(0.1,0, 2),
                "lapis_block");
        blockValue(outputs,
                Blocks.BRICKS,
                new Calculation(5, 0, 100),
                new Calculation(5, 0, 500),
                Calculation.FLAT,
                "brick_block");
        blockValue(outputs,
                Blocks.IRON_BLOCK,
                new Calculation(10, 0, 100),
                new Calculation(10, 0, 500),
                Calculation.FLAT,
                "iron_block");
        blockValue(outputs,
                Blocks.COPPER_BLOCK,
                new Calculation(10, 0, 200),
                new Calculation(5, 0, 1000),
                Calculation.FLAT,
                "copper_block");
        blockValue(outputs,
                Blocks.EMERALD_BLOCK,
                Calculation.FLAT,
                Calculation.FLAT,
                new Calculation(1, 0, 5),
                "emerald_block");
        blockValue(outputs,
                Blocks.STONE_BRICKS,
                new Calculation(1, 0, 100),
                new Calculation(10, 0, 500),
                Calculation.FLAT,
                "stone_brick");
        blockValue(outputs,
                Blocks.COAL_BLOCK,
                new Calculation(1, 0, 100),
                new Calculation(5, 0, 300),
                Calculation.FLAT,
                "coal_block");
        blockValue(outputs,
                Blocks.NETHER_BRICKS,
                new Calculation(-1, -30, 0),
                new Calculation(-10, -200, 0),
                Calculation.FLAT,
                "nether_brick");
        blockValue(outputs,
                Blocks.COBBLESTONE,
                new Calculation(-0.10, -50, 1),
                new Calculation(-1, -500, 1),
                Calculation.FLAT,
                "cobblestone");
        blockValue(outputs,
                Blocks.STONE,
                new Calculation(-0.30, -40, 0),
                Calculation.FLAT,
                Calculation.FLAT,
                "stone");
        blockValue(outputs,
                Blocks.SANDSTONE,
                new Calculation(-0.10, -50, 1),
                new Calculation(-1, -500, 1),
                Calculation.FLAT,
                "sandstone");
        blockValue(outputs,
                Blocks.NETHERRACK,
                new Calculation(-2, -100, 0),
                new Calculation(-20, -800, 0),
                Calculation.FLAT,
                "netherrack");
        blockValue(outputs,
                Blocks.OBSIDIAN,
                new Calculation(10,  0, 50),
                new Calculation(20, 0, 1600),
                Calculation.FLAT,
                "obsidian");
        blockValue(outputs,
                Blocks.END_STONE,
                new Calculation(-0.5, -75, 0),
                new Calculation(-10, -250, -50),
                new Calculation(0.10, 0, 5),
                "end_stone");
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
     * @param output      The recipe output to add the recipe to.
     * @param block       The block to calculate the value for.
     * @param speed       The calculation for the speed.
     * @param efficiency  The calculation for the efficiency.
     * @param multiplicity The calculation for the multiplicity.
     * @param name        The name of the recipe.
     */
    protected static void blockValue(RecipeOutput output,
                                     Block block,
                                     Calculation speed, Calculation efficiency, Calculation multiplicity,
                                     String name) {
        BlockValueRecipeBuilder.of(block, speed, efficiency, multiplicity)
                .save(output, new ResourceLocation(Reference.MOD_ID, String.format("block_values/%s", name)));
    }
}