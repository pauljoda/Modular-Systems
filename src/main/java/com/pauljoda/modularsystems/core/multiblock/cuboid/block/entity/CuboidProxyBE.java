package com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity;

import com.pauljoda.nucleus.common.blocks.entity.Syncable;
import com.pauljoda.nucleus.util.TimeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class CuboidProxyBE extends Syncable {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    // Core Location
    private BlockPos coreLocation = null;
    private static final String CORE_LOCATION = "CoreLocation";

    /*******************************************************************************************************************
     * Constructor                                                                                                       *
     *******************************************************************************************************************/

    public CuboidProxyBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    /*******************************************************************************************************************
     * Proxy Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Retrieves the core block entity associated with the CuboidProxy block.
     *
     * @return The core block entity as an AbstractCuboidCoreBlockEntity object, or null if it is not found.
     */
    public @Nullable AbstractCuboidCoreBE getCore() {
        return coreLocation != null && getLevel().getBlockEntity(coreLocation) instanceof AbstractCuboidCoreBE ?
                (AbstractCuboidCoreBE) getLevel().getBlockEntity(coreLocation)
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
        getLevel().invalidateCapabilities(getBlockPos());
        this.coreLocation = coreLocation;
    }

    @Override
    public void onServerTick() {
        super.onServerTick();
        // If somehow core gone
        if(TimeUtils.onSecond(5)) {
            if(getCore() == null && getLevel() != null)
                setCoreLocation(null);
        }
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
    public void setVariable(int id, double value) {}

    /**
     * Used to get the field on the server, this will fetch the server value and overwrite the current
     *
     * @param id The field id
     * @return The value on the server, now set to ourselves
     */
    @Override
    public Double getVariable(int id) { return 0.0; }

    /*******************************************************************************************************************
     * BlockEntity                                                                                                     *
     *******************************************************************************************************************/

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        if(tag.contains(CORE_LOCATION))
            this.coreLocation = NbtUtils.readBlockPos(tag.getCompound(CORE_LOCATION));
        else
            this.coreLocation = null;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if(coreLocation != null)
            tag.put(CORE_LOCATION, NbtUtils.writeBlockPos(coreLocation));
    }
}