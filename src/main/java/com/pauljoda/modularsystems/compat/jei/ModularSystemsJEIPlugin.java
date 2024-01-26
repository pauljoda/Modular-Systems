package com.pauljoda.modularsystems.compat.jei;

import com.pauljoda.modularsystems.compat.jei.category.BlockValueCategory;
import com.pauljoda.modularsystems.compat.jei.category.StoneWorkCategory;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.recipe.blockvalues.BlockValueRecipe;
import com.pauljoda.modularsystems.core.recipe.stonework.StoneWorkRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

@JeiPlugin
public class ModularSystemsJEIPlugin implements IModPlugin {

    public static IGuiHelper jeiGuiHelper;

    public static ResourceLocation BACKGROUND = new ResourceLocation(Reference.MOD_ID, "textures/gui/jei/background.png");

    public static RecipeType<BlockValueRecipe> BLOCK_VALUES =
            new RecipeType<>(Registration.BLOCK_VALUE_RECIPE_TYPE.getId(), BlockValueRecipe.class);

    public static RecipeType<StoneWorkRecipe> STONE_WORK =
            new RecipeType<>(Registration.STONE_WORK_RECIPE_TYPE.getId(), StoneWorkRecipe.class);

    /**
     * The unique ID for this mod plugin.
     * The namespace should be your mod's modId.
     */
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Reference.MOD_ID, "jei");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = getRecipeManager();

        if(recipeManager == null) return;

        registration.addRecipes(BLOCK_VALUES,
                recipeManager.getAllRecipesFor(Registration.BLOCK_VALUE_RECIPE_TYPE.get()).stream().map(RecipeHolder::value).toList());

        registration.addRecipes(STONE_WORK,
                recipeManager.getAllRecipesFor(Registration.STONE_WORK_RECIPE_TYPE.get()).stream().map(RecipeHolder::value)
                        .filter(stoneWorkRecipe -> !stoneWorkRecipe.output().isEmpty())
                        .toList());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        jeiGuiHelper = registration.getJeiHelpers().getGuiHelper();

        // Block Values
        registration.addRecipeCategories(new BlockValueCategory());

        // Stone Work
        registration.addRecipeCategories(new StoneWorkCategory());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // Add Furnace Core to Smelting Handlers
        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACE_CORE_BLOCK.get()), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(new ItemStack(Registration.CUBOID_BANK_SOLIDS_BLOCK.get()), RecipeTypes.FUELING);

        registration.addRecipeCatalyst(new ItemStack(Registration.FURNACE_CORE_BLOCK.get()), BLOCK_VALUES);
        registration.addRecipeCatalyst(new ItemStack(Registration.STONE_WORK_CORE_BLOCK.get()), BLOCK_VALUES);

        registration.addRecipeCatalyst(new ItemStack(Registration.STONE_WORK_CORE_BLOCK.get()), STONE_WORK);
    }

    // region HELPERS
    private RecipeManager getRecipeManager() {

        RecipeManager recipeManager = null;
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            recipeManager = level.getRecipeManager();
        }
        return recipeManager;
    }
    // endregion
}
