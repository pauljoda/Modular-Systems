package com.pauljoda.modularsystems.furnace.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBlockEntity;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FurnaceCoreContainer extends BaseContainer {

    protected FurnaceCoreBlockEntity furnaceCore;

    protected ContainerData data;

    /**
     * Client Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id The container ID.
     * @param playerInventory The player inventory.
     * @param extraData The extra data.
     */
    public FurnaceCoreContainer(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory,
                (FurnaceCoreBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(4));
    }

    /**
     * Server Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id The container ID.
     * @param playerInventory The player inventory.
     * @param furnace The FurnaceCoreBlockEntity.
     * @param data The container data.
     */
    public FurnaceCoreContainer(int id, Inventory playerInventory, FurnaceCoreBlockEntity furnace, ContainerData data) {
        super(Registration.FURNACE_CORE_CONTAINER.get(),
                id, playerInventory, furnace.getItemCapability(),
                furnace.getLevel(), furnace.getBlockPos(), Registration.FURNACE_CORE_BLOCK.get());

        // Reference to core
        furnaceCore = furnace;

        // Hold Data
        this.data = data;

        // Add Slots
        // Input, must add helper methods because the passed capability does not allow extract from input
        addSlot(new SlotItemHandler(furnaceCore.getItemCapability(), 0, 56, 35) {
            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }

            @Override
            public @NotNull ItemStack remove(int amount) {
                return furnaceCore.extractInput(0, amount, false);
            }
        });

        // Output, allow normal IItemhandler slot to manage
        addSlot(new SlotItemHandler(furnaceCore.getItemCapability(), 1, 116, 35));

        // Add player inventory
        addPlayerInventorySlots(8, 84);

        // Add dataslots
        addDataSlots(data);
    }

    /**
     * Retrieves the fuel time of the furnace.
     *
     * @return The fuel time of the furnace.
     */
    public int getFuelTime() {
        return this.data.get(0);
    }

    /**
     * Retrieves the current amount of fuel time provided.
     *
     * @return The current fuel provided time.
     */
    public int getCurrentFuelProvidedTime() {
        return this.data.get(1);
    }

    /**
     * Retrieves the work time of the FurnaceCoreContainer.
     *
     * @return The work time.
     */
    public int getWorkTime() {
        return this.data.get(2);
    }

    /**
     * Retrieves the scaled process time of the Furnace Core container.
     *
     * @return The scaled process time as an integer.
     */
    public int getScaledProcessTime() {
        return this.data.get(3);
    }

    /**
     * Retrieves the FurnaceCoreBlockEntity associated with this container.
     *
     * @return The FurnaceCoreBlockEntity.
     */
    public FurnaceCoreBlockEntity getCore() {
        return furnaceCore;
    }

    /**
     * Determines whether the player is still valid for the given container.
     *
     * @param pPlayer The player to check.
     * @return true if the player is still valid, false otherwise.
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return access.evaluate(
                (level, pos) -> !level.getBlockState(pos).is(blockType)
                        ? false
                        : pPlayer.distanceToSqr((double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5) <= 400,
                true
        );
    }
}
