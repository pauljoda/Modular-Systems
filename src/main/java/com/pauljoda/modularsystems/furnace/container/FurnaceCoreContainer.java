package com.pauljoda.modularsystems.furnace.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBlockEntity;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.neoforged.neoforge.items.SlotItemHandler;

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
        addSlot(new SlotItemHandler(furnaceCore.getItemCapability(), 0, 56, 35) {
            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }
        });
        addSlot(new SlotItemHandler(furnaceCore.getItemCapability(), 1, 116, 35));
        addPlayerInventorySlots(8, 84);

        // Add dataslots
        addDataSlots(data);
    }

    public int getFuelTime() {
        return this.data.get(0);
    }

    public int getCurrentFuelProvidedTime() {
        return this.data.get(1);
    }

    public int getWorkTime() {
        return this.data.get(2);
    }

    public int getScaledProcessTime() {
        return this.data.get(3);
    }

    public FurnaceCoreBlockEntity getCore() {
        return furnaceCore;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return access.evaluate(
                (p_38916_, p_38917_) -> !p_38916_.getBlockState(p_38917_).is(blockType)
                        ? false
                        : pPlayer.distanceToSqr((double)p_38917_.getX() + 0.5, (double)p_38917_.getY() + 0.5, (double)p_38917_.getZ() + 0.5) <= 400,
                true
        );
    }
}
