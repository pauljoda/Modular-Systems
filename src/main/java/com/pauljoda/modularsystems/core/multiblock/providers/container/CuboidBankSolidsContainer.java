package com.pauljoda.modularsystems.core.multiblock.providers.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankBaseBE;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankSolidsBE;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

/**
 * Represents a container for the CuboidBankSolidsBlockEntity.
 * This class provides methods to handle the interaction between the block entity and the player's inventory.
 */
public class CuboidBankSolidsContainer extends BaseContainer {

    CuboidBankSolidsBE cuboidBankSolids;

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
                (CuboidBankSolidsBE) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    /**
     * Server Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param cuboid          The FurnaceCoreBlockEntity.
     */
    public CuboidBankSolidsContainer(int id, Inventory playerInventory, CuboidBankSolidsBE cuboid) {
        super(Registration.CUBOID_BANK_SOLIDS_CONTAINER.get(),
                id, playerInventory, cuboid.getItemCapability(),
                cuboid.getLevel(), cuboid.getBlockPos(), Registration.CUBOID_BANK_SOLIDS_BLOCK.get());

        // Reference to core
        cuboidBankSolids = cuboid;

        // Add Slots
        addInventoryGrid(8, 19, 9, 0);
        addPlayerInventorySlots(8, 84);
    }


    public CuboidBankBaseBE getBank() {
        return cuboidBankSolids;
    }
}