package com.pauljoda.modularsystems.core.multiblock.cuboid.container;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidIOBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CuboidIOContainer extends AbstractContainerMenu {

    protected ContainerLevelAccess access;

    protected final CuboidIOBE cuboidIOBE;


    /**
     * Client Constructor
     * Represents a container for the Furnace Core block entity.
     *
     * @param id              The container ID.
     * @param playerInventory The player inventory.
     * @param extraData       The extra data.
     */
    public CuboidIOContainer(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id,
                (CuboidIOBE) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()),
                playerInventory.player.level(),
                extraData.readBlockPos());
    }

    public CuboidIOContainer(int id, CuboidIOBE cuboidIO, @Nullable Level level,
                             @Nullable BlockPos pos) {
        super(Registration.CUBOID_IO_CONTAINER.get(), id);

        this.cuboidIOBE = cuboidIO;

        if (level != null && pos != null)
            access = ContainerLevelAccess.create(level, pos);

    }

    /**
     * Retrieves the CuboidIOBE object associated with the CuboidIOContainer.
     *
     * @return The CuboidIOBE object.
     */
    public CuboidIOBE getCuboidIOBE() {
        return cuboidIOBE;
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     *
     * @param pPlayer
     * @param pIndex
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    /**
     * Determines whether supplied player can use this container
     *
     * @param pPlayer
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return AbstractContainerMenu.stillValid(access, pPlayer, Registration.CUBOID_IO_BLOCK.get());
    }
}
