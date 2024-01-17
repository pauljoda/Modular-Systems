package com.pauljoda.modularsystems.core.multiblock.cuboid.container;

import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.AbstractCuboidCoreBE;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCuboidCoreContainer extends BaseContainer {

    protected AbstractCuboidCoreBE core;

    protected ContainerData data;

    /**
     * Client Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id The container ID.
     * @param playerInventory The player inventory.
     * @param extraData The extra data.
     */
    public AbstractCuboidCoreContainer(MenuType<?> type, int id, Inventory playerInventory, FriendlyByteBuf extraData, Block block) {
        this(type, id, playerInventory,
                (AbstractCuboidCoreBE) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(4), block);
    }

    /**
     * Server Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id The container ID.
     * @param playerInventory The player inventory.
     * @param cuboidCore The FurnaceCoreBlockEntity.
     * @param data The container data.
     */
    public AbstractCuboidCoreContainer(MenuType<?> type, int id, Inventory playerInventory,
                                       AbstractCuboidCoreBE cuboidCore, ContainerData data, Block block) {
        super(type,
                id, playerInventory, cuboidCore.getItemCapability(),
                cuboidCore.getLevel(), cuboidCore.getBlockPos(),
                block);

        // Reference to core
        core = cuboidCore;

        // Hold Data
        this.data = data;

        // Add Slots
        // Input, must add helper methods because the passed capability does not allow extract from input
        addSlot(new SlotItemHandler(core.getItemCapability(), 0, 56, 35) {
            @Override
            public boolean mayPickup(Player playerIn) {
                return true;
            }

            @Override
            public @NotNull ItemStack remove(int amount) {
                return core.extractInput(0, amount, false);
            }
        });

        // Output, allow normal IItemhandler slot to manage
        addSlot(new SlotItemHandler(core.getItemCapability(), 1, 116, 35));

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
    public AbstractCuboidCoreBE getCore() {
        return core;
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
