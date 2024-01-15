package com.pauljoda.modularsystems.power.providers.block.entity;

import com.pauljoda.modularsystems.core.multiblock.FuelProvider;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.nucleus.common.blocks.entity.Syncable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class CuboidBankBaseBlockEntity extends Syncable implements FuelProvider {

    protected int priority = 0;
    protected static final String PRIORITY = "Priority";

    // Core Location
    private BlockPos coreLocation = null;
    private static final String CORE_LOCATION = "CoreLocation";

    public static final int UPDATE_PRIORITY = 0;

    protected int coolDown = 0;

    public CuboidBankBaseBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    /**
     * Used to scale the current power level
     * @param scale The scale to move to
     * @return A number from 0 - { @param scale} for current level
     */
    public abstract double getPowerLevelScaled(int scale);


    /**
     * Retrieves the core block entity associated with the CuboidProxy block.
     *
     * @return The core block entity as an AbstractCuboidCoreBlockEntity object, or null if it is not found.
     */
    public @Nullable AbstractCuboidCoreBlockEntity getCore() {
        return coreLocation != null && getLevel().getBlockEntity(coreLocation) instanceof AbstractCuboidCoreBlockEntity ?
                (AbstractCuboidCoreBlockEntity) getLevel().getBlockEntity(coreLocation)
                : null;
    }

    /**
     * Retrieves the core location of the CuboidProxy block.
     *
     * @return the core location as a BlockPos object
     */
    public BlockPos getCoreLocation() {
        return coreLocation;
    }

    /**
     * Sets the core location of the CuboidProxy block.
     *
     * @param coreLocation the new core location as a BlockPos object
     */
    public void setCoreLocation(BlockPos coreLocation) {
        this.coreLocation = coreLocation;
    }

    /*******************************************************************************************************************
     * Block Entity Methods                                                                                            *
     *******************************************************************************************************************/

    /**
     * Loads the data of this CuboidBankBase instance from the provided CompoundTag.
     *
     * @param pTag The CompoundTag containing the data to load
     */
    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        priority = pTag.getInt(PRIORITY);

        if(pTag.contains(CORE_LOCATION))
            this.coreLocation = NbtUtils.readBlockPos(pTag.getCompound(CORE_LOCATION));
        else
            this.coreLocation = null;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt(PRIORITY, priority);

        if(coreLocation != null)
            pTag.put(CORE_LOCATION, NbtUtils.writeBlockPos(coreLocation));
    }

    /*******************************************************************************************************************
     * Fuel Provider Methods                                                                                           *
     *******************************************************************************************************************/

    /**
     * Returns the priority of the FuelProvider.
     * Higher priority values indicate higher priority.
     *
     * @return the priority of the FuelProvider
     */
    @Override
    public int getPriority() {
        return priority;
    }


    /*******************************************************************************************************************
     * Syncable Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Used to set the value of a field
     *
     * @param id    The field id
     * @param value The value of the field
     */
    @Override
    public void setVariable(int id, double value) {
        switch (id) {
            case UPDATE_PRIORITY:
                priority = (int) value;
                break;
            default:
        }
    }

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     *
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    @Override
    public Double getVariable(int id) {
        return switch (id) {
            case UPDATE_PRIORITY -> (double) priority;
            default -> 0.0;
        };
    }
}
