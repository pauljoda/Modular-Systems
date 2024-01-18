package com.pauljoda.modularsystems.core.recipe.stonework;

import com.pauljoda.modularsystems.core.lib.Registration;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record StoneWorkRecipe(Ingredient input, ItemStack output) implements Recipe<Container> {
    @Override
    public Ingredient input() {
        return input;
    }

    @Override
    public ItemStack output() {
        return output;
    }

    /*******************************************************************************************************************
     * Recipe                                                                                                          *
     *******************************************************************************************************************/

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param pLevel
     */
    @Override
    public boolean matches(Container container, Level pLevel) {
        return input.test(container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param pWidth
     * @param pHeight
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Registration.STONE_WORK_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Registration.STONE_WORK_RECIPE_TYPE.get();
    }

    /*******************************************************************************************************************
     * Helpers                                                                                                         *
     *******************************************************************************************************************/

    @Override
    public String toString() {
        return String.format("StoneWorkRecipe: %s -> %s", input, output);
    }
}
