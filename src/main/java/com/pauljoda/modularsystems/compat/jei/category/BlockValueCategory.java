package com.pauljoda.modularsystems.compat.jei.category;

import com.pauljoda.modularsystems.compat.jei.ModularSystemsJEIPlugin;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.recipe.blockvalues.BlockValueRecipe;
import com.pauljoda.nucleus.util.ClientUtils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.IGuiGraphicsExtension;

import java.awt.*;

public class BlockValueCategory implements IRecipeCategory<BlockValueRecipe> {

    protected final IDrawable background;

    public BlockValueCategory() {
        background =
                ModularSystemsJEIPlugin.jeiGuiHelper
                        .drawableBuilder(ModularSystemsJEIPlugin.BACKGROUND, 0, 0, 150, 90).build();
    }

    /**
     * @return the type of recipe that this category handles.
     * @since 9.5.0
     */
    @Override
    public RecipeType<BlockValueRecipe> getRecipeType() {
        return ModularSystemsJEIPlugin.BLOCK_VALUES;
    }

    /**
     * Returns a text component representing the name of this recipe type.
     * Drawn at the top of the recipe GUI pages for this category.
     *
     * @since 7.6.4
     */
    @Override
    public Component getTitle() {
        return Component.translatable("modular_systems.blockvalues.jei.title");
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
     * You can use {@link IGuiHelper#createDrawableIngredient(IIngredientType, Object)}
     * to create a drawable from an ingredient.
     *
     * @return icon to draw on the category tab, max size is 16x16 pixels.
     */
    @Override
    public IDrawable getIcon() {
        return ModularSystemsJEIPlugin.jeiGuiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Registration.FURNACE_CORE_BLOCK.get()));
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
    public void setRecipe(IRecipeLayoutBuilder builder, BlockValueRecipe recipe, IFocusGroup focuses) {
        var input = recipe.inputBlock();
        builder.addSlot(RecipeIngredientRole.INPUT, (150 / 2) - 8, 0).addIngredients(input);
    }

    @Override
    public void draw(BlockValueRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        var font = Minecraft.getInstance().font;

        // Speed
        guiGraphics.drawString(font, ClientUtils.translate("modular_systems.speed.menu"), 0, 17, Color.RED.getRGB(), false);
        guiGraphics.drawString(font, String.format("%s ticks | %s <-> %s",
                        recipe.speedCalculation().scaleFactor(),
                        recipe.speedCalculation().floor(),
                        recipe.speedCalculation().ceiling()),
                3, 27, IGuiGraphicsExtension.DEFAULT_BACKGROUND_COLOR, false);

        // Efficiency
        guiGraphics.drawString(font, ClientUtils.translate("modular_systems.efficiency.menu"), 0, 37, Color.BLUE.getRGB(), false);
        guiGraphics.drawString(font, String.format("%s ticks | %s <-> %s",
                        recipe.efficiencyCalculation().scaleFactor(),
                        recipe.efficiencyCalculation().floor(),
                        recipe.efficiencyCalculation().ceiling()),
                3, 47, IGuiGraphicsExtension.DEFAULT_BACKGROUND_COLOR, false);

        // Multiplicity
        guiGraphics.drawString(font, ClientUtils.translate("modular_systems.multiplicity.menu"), 0, 57, new Color(61, 121, 46).getRGB(), false);
        guiGraphics.drawString(font, String.format("%sX | %s <-> %s",
                        recipe.multiplicityCalculation().scaleFactor(),
                        recipe.multiplicityCalculation().floor(),
                        recipe.multiplicityCalculation().ceiling()),
                3, 67, IGuiGraphicsExtension.DEFAULT_BACKGROUND_COLOR, false);

        // Legend
        var helpString = "(Per Block | Min <-> Max)";
        guiGraphics.drawString(font, helpString, (150 / 2) - font.width(helpString) / 2, 80, IGuiGraphicsExtension.DEFAULT_BACKGROUND_COLOR, false);
    }
}
