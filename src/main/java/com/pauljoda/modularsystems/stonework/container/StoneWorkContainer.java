package com.pauljoda.modularsystems.stonework.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.AbstractCuboidCoreBE;
import com.pauljoda.modularsystems.core.multiblock.cuboid.container.AbstractCuboidCoreContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class StoneWorkContainer extends AbstractCuboidCoreContainer {
    /**
     * Client Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param extraData       The extra data.
     */
    public StoneWorkContainer(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        super(Registration.STONE_WORK_CORE_CONTAINER.get(), id, playerInventory, extraData, Registration.STONE_WORK_CORE_BLOCK.get());
    }

    /**
     * Server Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param cuboidCore      The FurnaceCoreBlockEntity.
     * @param data            The container data.
     */
    public StoneWorkContainer(int id, Inventory playerInventory, AbstractCuboidCoreBE cuboidCore, ContainerData data) {
        super(Registration.STONE_WORK_CORE_CONTAINER.get(), id, playerInventory, cuboidCore, data, Registration.STONE_WORK_CORE_BLOCK.get());
    }
}
