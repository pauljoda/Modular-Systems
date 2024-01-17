package com.pauljoda.modularsystems.furnace.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.AbstractCuboidCoreBE;
import com.pauljoda.modularsystems.furnace.container.FurnaceCoreContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;

public class FurnaceCoreBE extends AbstractCuboidCoreBE {

    RecipeManager.CachedCheck<Container, SmeltingRecipe> furnaceRecipeCache;

    /**
     * Initializes a new instance of the AbstractCuboidCore class.
     *
     * @param pos              The position of the block.
     * @param state            The state of the block.
     */
    public FurnaceCoreBE(BlockPos pos, BlockState state) {
        super(Registration.FURNACE_CORE_BLOCK_ENTITY.get(), pos, state);

        furnaceRecipeCache = RecipeManager.createCheck(RecipeType.SMELTING);
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
        return furnaceRecipeCache.getRecipeFor(recipeHolder, getLevel()).isPresent() ?
                furnaceRecipeCache.getRecipeFor(recipeHolder,
                        getLevel()).get().value().getResultItem(getLevel().registryAccess()) :
                ItemStack.EMPTY;
    }

    /*******************************************************************************************************************
     * Syncable Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Retrieves the display name of the component.
     *
     * @return The display name of the component.
     */

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.modular_systems.furnace_core");
    }

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
        return new FurnaceCoreContainer(pContainerId, pPlayerInventory, this, coreData);
    }
}
