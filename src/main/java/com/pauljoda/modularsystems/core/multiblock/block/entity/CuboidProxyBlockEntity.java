package com.pauljoda.modularsystems.core.multiblock.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.nucleus.common.blocks.entity.UpdatingBlockEntity;
import com.pauljoda.nucleus.util.TimeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CuboidProxyBlockEntity extends UpdatingBlockEntity {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    // Core Location
    private BlockPos coreLocation = null;
    private static final String CORE_LOCATION = "CoreLocation";

    // Stored BlockState
    private BlockState storedBlockState = null;
    private static final String STORED_BLOCK_STATE = "StoredBlockState";

    /*******************************************************************************************************************
     * Constructor                                                                                                       *
     *******************************************************************************************************************/

    public CuboidProxyBlockEntity(BlockPos pos, BlockState state) {
        super(Registration.CUBOID_PROXY_BLOCK_ENTITY.get(), pos, state);
    }

    /*******************************************************************************************************************
     * Proxy Methods                                                                                                   *
     *******************************************************************************************************************/

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

    /**
     * Retrieves the stored block state of the CuboidProxy block.
     *
     * @return the stored block state as a BlockState object
     */
    public BlockState getStoredBlockState() {
        return storedBlockState;
    }

    /**
     * Sets the stored block state of the CuboidProxy.
     *
     * @param storedBlockState the new block state to be stored
     */
    public void setStoredBlockState(BlockState storedBlockState) {
        this.storedBlockState = storedBlockState;
    }

    @Override
    public void onServerTick() {
        super.onServerTick();
        // If somehow core gone
        if(TimeUtils.onSecond(5)) {
            if(getCore() == null && getLevel() != null)
                getLevel().setBlock(getBlockPos(), getStoredBlockState(), Block.UPDATE_ALL);
        }
    }

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

        if(tag.contains(STORED_BLOCK_STATE))
            this.storedBlockState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound(STORED_BLOCK_STATE));
        else
            this.storedBlockState = null;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if(coreLocation != null)
            tag.put(CORE_LOCATION, NbtUtils.writeBlockPos(coreLocation));
        if(storedBlockState != null)
            tag.put(STORED_BLOCK_STATE, NbtUtils.writeBlockState(storedBlockState));
    }
}
