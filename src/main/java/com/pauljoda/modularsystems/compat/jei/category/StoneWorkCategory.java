package com.pauljoda.modularsystems.compat.jei.category;

import com.pauljoda.modularsystems.compat.jei.ModularSystemsJEIPlugin;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.recipe.stonework.StoneWorkRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Collections;

public class StoneWorkCategory implements IRecipeCategory<StoneWorkRecipe> {

    private static final ResourceLocation guiTexture = new ResourceLocation(Reference.MOD_ID, "textures/gui/stone_work_core.png");
    private static final int u = 47;
    private static final int v = 21;

    protected final IDrawable background;
    protected final IDrawableAnimated arrow;
    protected final IDrawableAnimated flame;

    public StoneWorkCategory() {
        background =
                ModularSystemsJEIPlugin.jeiGuiHelper
                        .drawableBuilder(
                                guiTexture, u, v, 95, 48).build();

        var staticArrow = ModularSystemsJEIPlugin.jeiGuiHelper
                .drawableBuilder(
                        guiTexture, 176, 14, 24, 17).build();
        arrow = ModularSystemsJEIPlugin.jeiGuiHelper
                .createAnimatedDrawable(staticArrow, 200, IDrawableAnimated.StartDirection.LEFT, false);

        var staticFlame = ModularSystemsJEIPlugin.jeiGuiHelper
                .drawableBuilder(guiTexture, 176, 0, 14, 14).build();
        flame = ModularSystemsJEIPlugin.jeiGuiHelper
                .createAnimatedDrawable(staticFlame, 1600, IDrawableAnimated.StartDirection.TOP, true);
    }

    /**
     * @return the type of recipe that this category handles.
     * @since 9.5.0
     */
    @Override
    public RecipeType<StoneWorkRecipe> getRecipeType() {
        return ModularSystemsJEIPlugin.STONE_WORK;
    }

    /**
     * Returns a text component representing the name of this recipe type.
     * Drawn at the top of the recipe GUI pages for this category.
     *
     * @since 7.6.4
     */
    @Override
    public Component getTitle() {
        return Component.translatable("block.modular_systems.stone_work_core");
    }

    /**
     * Returns the drawable background for a single recipe in this category.
     */
    @Override
    public IDrawable getBackground() {
        return background;
    }

    /**
     * Icon for the category tab.
     *
     * @return icon to draw on the category tab, max size is 16x16 pixels.
     */
    @Override
    public IDrawable getIcon() {
        return ModularSystemsJEIPlugin.jeiGuiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.STONE_WORK_CORE_BLOCK.get()));
    }

    /**
     * Sets all the recipe's ingredients by filling out an instance of {@link IRecipeLayoutBuilder}.
     * This is used by JEI for lookups, to figure out what ingredients are inputs and outputs for a recipe.
     *
     * @param builder
     * @param recipe
     * @param focuses
     * @since 9.4.0
     */
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, StoneWorkRecipe recipe, IFocusGroup focuses) {
        var input = recipe.input();
        var output = recipe.output();

        builder.addSlot(RecipeIngredientRole.INPUT, 56 - u, 35 - v).addIngredients(input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 116 - u, 35 - v).addIngredients(Ingredient.of(output));
    }

    @Override
    public void draw(StoneWorkRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

        arrow.draw(guiGraphics, 79 - u, 34 - v);
        flame.draw(guiGraphics, 81 - u, 53 - v);
    }
}
