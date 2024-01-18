package com.pauljoda.modularsystems.core.recipe.blockvalues;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;

public record BlockContainerWrapper(@NonNull BlockState heldBlock) implements Container {
    @Override
    @NonNull
    public BlockState heldBlock() {
        return heldBlock;
    }

    /*******************************************************************************************************************
     * Container                                                                                                       *
     *******************************************************************************************************************/

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param pSlot
     */
    @Override
    public ItemStack getItem(int pSlot) {
        return ItemStack.EMPTY;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     *
     * @param pSlot
     * @param pAmount
     */
    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ItemStack.EMPTY;
    }

    /**
     * Removes a stack from the given slot and returns it.
     *
     * @param pSlot
     */
    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ItemStack.EMPTY;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param pSlot
     * @param pStack
     */
    @Override
    public void setItem(int pSlot, ItemStack pStack) {

    }

    /**
     * For block entities, ensures the chunk containing the block entity is saved to disk later - the game won't think it hasn't changed and skip it.
     */
    @Override
    public void setChanged() {

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     *
     * @param pPlayer
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void clearContent() {

    }
}
