package com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.cuboid.container.CuboidIOContainer;
import com.pauljoda.nucleus.util.InventoryUtils;
import com.pauljoda.nucleus.util.LevelUtils;
import com.pauljoda.nucleus.util.TimeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CuboidIOBE extends CuboidProxyBE implements MenuProvider {

    protected boolean pull = false;
    public static final String PULL_NBT = "Pull";
    public static final int PULL_TOGGLE = 0;
    protected boolean push = false;
    public static final String PUSH_NBT = "Push";
    public static final int PUSH_TOGGLE = 1;

    /*******************************************************************************************************************
     * Constructor                                                                                                     *
     *******************************************************************************************************************/
    public CuboidIOBE(BlockPos pos, BlockState state) {
        super(Registration.CUBOID_IO_BLOCK_ENTITY.get(), pos, state);
    }

    /**
     * Getter for the "pull" property
     *
     * @return the value of the "pull" property
     */
     public boolean isPull() {
         return pull;
     }

    /**
     * Getter for the "push" property
     *
     * @return the value of the "push" property
     */
     public boolean isPush() {
         return push;
     }

    /*******************************************************************************************************************
     * BlockEntity                                                                                                     *
     *******************************************************************************************************************/

    /**
     * Loads the data from a CompoundTag into the CuboidIOBE object.
     *
     * @param tag The CompoundTag containing the data to be loaded.
     */
    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        push = tag.getBoolean(PUSH_NBT);
        pull = tag.getBoolean(PULL_NBT);
    }

    /**
     * Saves additional data to the given CompoundTag.
     *
     * @param tag The CompoundTag to save the additional data to.
     */
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putBoolean(PULL_NBT, pull);
        tag.putBoolean(PUSH_NBT, push);
    }

    /**
     * Executes operations on each server tick.
     * If the core block entity is present and it is time to execute the operation (every second), it tries to push or pull items.
     * If push is enabled and there are items in the output slot, it tries to move the items into adjacent inventories using available capabilities.
     * If pull is enabled , it tries to move items from adjacent inventories into the input slot using available capabilities.
     */
    @Override
    public void onServerTick() {
        super.onServerTick();

        // Do Operations
        if(getCore() != null && TimeUtils.onSecond(3) && (push || pull)) {
            var core = getCore();
            var coreItemHandler = getCore().getItemCapability();

            // Try Push
            if(push && !coreItemHandler.getStackInSlot(AbstractCuboidCoreBE.OUTPUT_SLOT).isEmpty()) {
                for (var direction : Direction.values()) {
                    // Nothing to move
                    if(coreItemHandler.getStackInSlot(AbstractCuboidCoreBE.OUTPUT_SLOT).isEmpty())
                        break;

                    if(getLevel().getBlockEntity(getBlockPos().offset(direction.getNormal())) instanceof CuboidProxyBE ||
                        getBlockPos().offset(direction.getNormal()).equals(getCoreLocation()))
                        continue;

                    // Attempt Move
                    if(getLevel().getCapability(Capabilities.ItemHandler.BLOCK, getBlockPos().offset(direction.getNormal()), direction.getOpposite()) != null) {
                        var otherInventory = getLevel().getCapability(Capabilities.ItemHandler.BLOCK, getBlockPos().offset(direction.getNormal()), direction.getOpposite());
                        InventoryUtils.moveItemInto(coreItemHandler, AbstractCuboidCoreBE.OUTPUT_SLOT,
                                otherInventory, -1, 64, direction.getOpposite(),
                                true, false, true);
                    }
                }
            }

            // Try pull
            if(pull) {
                for (var direction : Direction.values()) {
                    if(getLevel().getBlockEntity(getBlockPos().offset(direction.getNormal())) instanceof CuboidProxyBE ||
                            getBlockPos().offset(direction.getNormal()).equals(getCoreLocation()))
                        continue;

                    // Attempt Move
                    if(getLevel().getCapability(Capabilities.ItemHandler.BLOCK, getBlockPos().offset(direction.getNormal()), direction.getOpposite()) != null) {
                        var otherInventory = getLevel().getCapability(Capabilities.ItemHandler.BLOCK, getBlockPos().offset(direction.getNormal()), direction.getOpposite());
                        InventoryUtils.moveItemInto(otherInventory, -1,
                                coreItemHandler, AbstractCuboidCoreBE.INPUT_SLOT, 64, direction.getOpposite(),
                                true, true, false);
                    }
                }
            }
        }
    }

    /*******************************************************************************************************************
     * Syncable                                                                                                        *
     *******************************************************************************************************************/

    /**
     * Sets the value of a field based on the given field id.
     *
     * @param id    The field id.
     * @param value The value of the field.
     */
    @Override
    public void setVariable(int id, double value) {
        switch (id) {
            case PULL_TOGGLE -> pull = !pull;
            case PUSH_TOGGLE -> push = !push;
        }
        markForUpdate(Block.UPDATE_ALL);
    }

    @Override
    public Double getVariable(int id) {
        return 0.0D;
    }

    /*******************************************************************************************************************
     * MenuProvider                                                                                                     *
     *******************************************************************************************************************/

    /**
     * Returns the display name of the component.
     *
     * @return the display name of the component
     */
    @Override
    public Component getDisplayName() {
        return Component.translatable(Registration.CUBOID_IO_BLOCK.get().getDescriptionId());
    }

    /**
     * Creates a menu for the CuboidIOBE block entity.
     *
     * @param pContainerId    The ID of the container.
     * @param pPlayerInventory The inventory of the player.
     * @param pPlayer         The player.
     * @return The created CuboidIOContainer menu.
     */
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CuboidIOContainer(pContainerId, this, getLevel(), getBlockPos());
    }
}
