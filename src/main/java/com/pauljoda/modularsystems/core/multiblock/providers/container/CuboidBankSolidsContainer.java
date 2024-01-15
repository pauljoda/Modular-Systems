package com.pauljoda.modularsystems.core.multiblock.providers.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankSolidsBlockEntity;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class CuboidBankSolidsContainer extends BaseContainer {

    CuboidBankSolidsBlockEntity cuboidBankSolids;

    /**
     * Client Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param extraData       The extra data.
     */
    public CuboidBankSolidsContainer(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory,
                (CuboidBankSolidsBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    /**
     * Server Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param cuboid          The FurnaceCoreBlockEntity.
     */
    public CuboidBankSolidsContainer(int id, Inventory playerInventory, CuboidBankSolidsBlockEntity cuboid) {
        super(Registration.CUBOID_BANK_SOLIDS_CONTAINER.get(),
                id, playerInventory, cuboid.getItemCapability(),
                cuboid.getLevel(), cuboid.getBlockPos(), Registration.CUBOID_BANK_SOLIDS_BLOCK.get());

        // Reference to core
        cuboidBankSolids = cuboid;

        // Add Slots
        addInventoryGrid(8, 19, 9, 0);
        addPlayerInventorySlots(8, 84);
    }
}