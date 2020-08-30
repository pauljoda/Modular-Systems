package com.pauljoda.modularsystems.generator.tile;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.manager.TileManager;
import com.pauljoda.modularsystems.generator.container.GeneratorContainer;
import com.pauljoda.nucleus.common.tiles.EnergyHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/20
 */
public class GeneratorTile extends EnergyHandler implements INamedContainerProvider, IItemHandlerModifiable {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    protected static final int MAX_STORAGE = 32000;

    // List of Inventory contents
    public NonNullList<ItemStack> inventoryContents = NonNullList.withSize(1, ItemStack.EMPTY);

    /*******************************************************************************************************************
     * Constructor                                                                                                     *
     *******************************************************************************************************************/

    public GeneratorTile() {
        super(TileManager.generator);
    }

    /*******************************************************************************************************************
     * EnergyHandler                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Used to define the default size of this energy bank
     *
     * @return The default size of the energy bank
     */
    @Override
    protected int getDefaultEnergyStorageSize() {
        return MAX_STORAGE;
    }

    /**
     * Is this tile an energy provider
     *
     * @return True to allow energy out
     */
    @Override
    protected boolean isProvider() {
        return true;
    }

    /**
     * Is this tile an energy receiver
     *
     * @return True to accept energy
     */
    @Override
    protected boolean isReceiver() {
        return false;
    }

    /*******************************************************************************************************************
     * IItemHandler                                                                                                    *
     *******************************************************************************************************************/

    /**
     * Used to define if an item is valid for a slot
     *
     * @param index The slot id
     * @param stack The stack to check
     * @return True if you can put this there
     */
    protected boolean isItemValidForSlot(int index, ItemStack stack) {
        return inventoryContents.get(0).isEmpty() && index == 0 && ForgeHooks.getBurnTime(stack) > 0;
    }

    /**
     * Makes sure this slot is within our range
     *
     * @param slot Which slot
     */
    protected boolean isValidSlot(int slot) {
        return slot > 0 && slot <= inventoryContents.size();
    }

    /**
     * Returns the number of slots available
     *
     * @return The number of slots available
     **/
    @Override
    public int getSlots() {
        return inventoryContents.size();
    }

    /**
     * Returns the ItemStack in a given slot.
     * <p>
     * The result's stack size may be greater than the itemstacks max size.
     * <p>
     * If the result is null, then the slot is empty.
     * If the result is not null but the stack size is zero, then it represents
     * an empty slot that will only accept* a specific itemstack.
     * <p>
     * IMPORTANT: This ItemStack MUST NOT be modified. This method is not for
     * altering an inventories contents. Any implementers who are able to detect
     * modification through this method should throw an exception.
     * p
     * SERIOUSLY: DO NOT MODIFY THE RETURNED ITEMSTACK
     *
     * @param slot Slot to query
     * @return ItemStack in given slot. May not be null.
     **/
    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot) {
        if (!isValidSlot(slot))
            return ItemStack.EMPTY;
        return inventoryContents.get(slot);
    }

    /**
     * Inserts an ItemStack into the given slot and return the remainder.
     * The ItemStack should not be modified in this function!
     * Note: This behaviour is subtly different from IFluidHandlers.fill()
     *
     * @param slot     Slot to insert into.
     * @param stack    ItemStack to insert.
     * @param simulate If true, the insertion is only simulated
     * @return The remaining ItemStack that was not inserted (if the entire stack is accepted, then return null).
     * May be the same as the input ItemStack if unchanged, otherwise a new ItemStack.
     **/
    @Nonnull
    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!isItemValidForSlot(slot, stack))
            return stack;

        if (stack.isEmpty() || !isValidSlot(slot))
            return stack;

        ItemStack existing = this.inventoryContents.get(slot);

        int limit = getSlotLimit(slot);

        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;

            limit -= existing.getCount();
        }

        if (limit <= 0)
            return stack;

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            if (existing.isEmpty()) {
                this.inventoryContents.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.setCount(existing.getCount() + (reachedLimit ? limit : stack.getCount()));
            }
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    /**
     * Extracts an ItemStack from the given slot. The returned value must be null
     * if nothing is extracted, otherwise it's stack size must not be greater than amount or the
     * itemstacks getMaxStackSize().
     *
     * @param slot     Slot to extract from.
     * @param amount   Amount to extract (may be greater than the current stacks max limit)
     * @param simulate If true, the extraction is only simulated
     * @return ItemStack extracted from the slot, must be null, if nothing can be extracted
     **/
    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;

        if (!isValidSlot(slot))
            return ItemStack.EMPTY;
        ItemStack existing = this.inventoryContents.get(slot);

        if (existing.isEmpty())
            return ItemStack.EMPTY;

        int toExtract = Math.min(amount, existing.getMaxStackSize());

        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.inventoryContents.set(slot, ItemStack.EMPTY);
            }
            return existing;
        } else {
            if (!simulate) {
                this.inventoryContents.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            }

            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }

    /**
     * Retrieves the maximum stack size allowed to exist in the given slot.
     *
     * @param slot Slot to query.
     * @return The maximum stack size allowed in the slot.
     */
    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }

    /**
     * <p>
     * This function re-implements the vanilla function {@link IInventory#isItemValidForSlot(int, ItemStack)}.
     * It should be used instead of simulated insertions in cases where the contents and state of the inventory are
     * irrelevant, mainly for the purpose of automation and logic (for instance, testing if a minecart can wait
     * to deposit its items into a full inventory, or if the items in the minecart can never be placed into the
     * inventory and should move on).
     * </p>
     * <ul>
     * <li>isItemValid is false when insertion of the item is never valid.</li>
     * <li>When isItemValid is true, no assumptions can be made and insertion must be simulated case-by-case.</li>
     * <li>The actual items in the inventory, its fullness, or any other state are <strong>not</strong> considered by isItemValid.</li>
     * </ul>
     * @param slot    Slot to query for validity
     * @param stack   Stack to test with for validity
     *
     * @return true if the slot can insert the ItemStack, not considering the current state of the inventory.
     *         false if the slot can never insert the ItemStack in any situation.
     */
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return isItemValidForSlot(slot, stack);
    }

    /*******************************************************************************************************************
     * IItemHandlerModifiable                                                                                          *
     *******************************************************************************************************************/

    /**
     * Overrides the stack in the given slot. This method is used by the
     * standard Forge helper methods and classes. It is not intended for
     * general use by other mods, and the handler may throw an error if it
     * is called unexpectedly.
     *
     * @param slot  Slot to modify
     * @param stack ItemStack to set slot to (may be null)
     * @throws RuntimeException if the handler is called in a way that the handler
     *                          was not expecting.
     **/
    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (!isValidSlot(slot))
            return;
        if (ItemStack.areItemStacksEqual(this.inventoryContents.get(slot), stack))
            return;
        this.inventoryContents.set(slot, stack);
    }

    /*******************************************************************************************************************
     * TileEntity                                                                                                      *
     *******************************************************************************************************************/

    /**
     * Used to save the inventory to an NBT tag
     *
     * @param compound The tag to save to
     */
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, inventoryContents);

        ListNBT nbttaglist = new ListNBT();

        for (int i = 0; i < inventoryContents.size(); ++i) {
            ItemStack itemstack = inventoryContents.get(i);
            CompoundNBT nbttagcompound = new CompoundNBT();
            nbttagcompound.putByte("Slot", (byte) i);
            itemstack.write(nbttagcompound);
            nbttaglist.add(nbttagcompound);
        }

        if (!nbttaglist.isEmpty()) {
            compound.put("Items", nbttaglist);
        }

        return compound;
    }

    /**
     * Used to read the inventory from an NBT tag compound
     *
     * @param compound The tag to read from
     */
    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);
        ItemStackHelper.loadAllItems(compound, inventoryContents);
    }

    private LazyOptional<?> lazyHandler = LazyOptional.of(() -> this);

    /**
     * Used to access the capability
     *
     * @param capability The capability
     * @param facing     Which face
     * @param <T>        The object to case
     * @return Us as INSTANCE of T
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (LazyOptional<T>) lazyHandler;
        else
            return super.getCapability(capability, facing);
    }

    /*******************************************************************************************************************
     * INamedContainerProvider                                                                                         *
     *******************************************************************************************************************/

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent(Reference.MOD_ID + ".generator");
    }

    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new GeneratorContainer(windowID, playerInventory, this);
    }
}
