package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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
    }
}