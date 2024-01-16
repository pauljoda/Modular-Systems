package com.pauljoda.modularsystems.furnace.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.modularsystems.core.multiblock.container.AbstractCuboidCoreContainer;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBlockEntity;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class FurnaceCoreContainer extends AbstractCuboidCoreContainer {

    /**
     * Client Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param extraData       The extra data.
     */
    public FurnaceCoreContainer(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        super(Registration.FURNACE_CORE_CONTAINER.get(), id, playerInventory, extraData, Registration.FURNACE_CORE_BLOCK.get());
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
    public FurnaceCoreContainer(int id, Inventory playerInventory, AbstractCuboidCoreBlockEntity cuboidCore, ContainerData data) {
        super(Registration.FURNACE_CORE_CONTAINER.get(), id, playerInventory, cuboidCore, data, Registration.FURNACE_CORE_BLOCK.get());
    }
}
