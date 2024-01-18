package com.pauljoda.modularsystems.stonework.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.AbstractCuboidCoreBE;
import com.pauljoda.modularsystems.core.recipe.stonework.StoneWorkRecipe;
import com.pauljoda.modularsystems.stonework.container.StoneWorkContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StoneWorkCoreBE extends AbstractCuboidCoreBE {

    RecipeManager.CachedCheck<Container, StoneWorkRecipe> stoneWorkRecipes;

    /**
     * Initializes a new instance of the AbstractCuboidCore class.
     *
     * @param pos              The position of the block.
     * @param state            The state of the block.
     */
    public StoneWorkCoreBE(BlockPos pos, BlockState state) {
        super(Registration.STONE_WORK_CORE_BLOCK_ENTITY.get(), pos, state);

        stoneWorkRecipes = RecipeManager.createCheck(Registration.STONE_WORK_RECIPE_TYPE.get());
    }

    /**
     * Applies a recipe to the given stack.
     *
     * @param stack The item stack to apply the recipe to.
     * @return The resulting item stack after applying the recipe.
     */
    @Override
    public ItemStack recipe(ItemStack stack) {
        var recipeHolder = new SimpleContainer(stack,
                inventory.getStackInSlot(OUTPUT_SLOT));
        return stoneWorkRecipes.getRecipeFor(recipeHolder, getLevel()).isPresent() ?
                stoneWorkRecipes.getRecipeFor(recipeHolder,
                        getLevel()).get().value().getResultItem(getLevel().registryAccess()) :
                ItemStack.EMPTY;
    }

    /*******************************************************************************************************************
     * Syncable Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Creates a menu for the given container ID, player inventory, and player.
     *
     * @param pContainerId The container ID.
     * @param pPlayerInventory The player inventory.
     * @param pPlayer The player.
     * @return The created AbstractContainerMenu.
     */
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new StoneWorkContainer(pContainerId, pPlayerInventory, this, coreData);
    }
}
