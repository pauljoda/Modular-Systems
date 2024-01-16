package com.pauljoda.modularsystems.core.multiblock.providers.block.entity;

import com.pauljoda.modularsystems.core.multiblock.FuelProvider;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBE;
import com.pauljoda.modularsystems.core.multiblock.block.entity.CuboidProxyBE;
import com.pauljoda.nucleus.common.blocks.entity.Syncable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class CuboidBankBaseBE extends CuboidProxyBE implements FuelProvider {

    protected int priority = 0;
    protected static final String PRIORITY = "Priority";

    public static final int UPDATE_PRIORITY = 0;

    protected int coolDown = 0;

    public CuboidBankBaseBE(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    /**
     * Used to scale the current power level
     * @param scale The scale to move to
     * @return A number from 0 - { @param scale} for current level
     */
    public abstract double getPowerLevelScaled(int scale);

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
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt(PRIORITY, priority);
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
                markForUpdate(Block.UPDATE_ALL);
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
