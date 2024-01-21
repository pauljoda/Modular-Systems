package com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.nucleus.util.TimeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CuboidProxyBlockHolderBE extends CuboidProxyBE {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    // Stored BlockState
    private BlockState storedBlockState = null;
    private static final String STORED_BLOCK_STATE = "StoredBlockState";

    /*******************************************************************************************************************
     * Constructor                                                                                                       *
     *******************************************************************************************************************/

    public CuboidProxyBlockHolderBE(BlockPos pos, BlockState state) {
        super(Registration.CUBOID_PROXY_BLOCK_ENTITY.get(), pos, state);
    }

    /*******************************************************************************************************************
     * Proxy Methods                                                                                                   *
     *******************************************************************************************************************/

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
                getLevel().setBlock(getBlockPos(),
                        getStoredBlockState() == null ? Blocks.AIR.defaultBlockState() : getStoredBlockState(),
                        Block.UPDATE_ALL);
        }
    }

    /*******************************************************************************************************************
     * BlockEntity                                                                                                     *
     *******************************************************************************************************************/

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        if(tag.contains(STORED_BLOCK_STATE))
            this.storedBlockState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound(STORED_BLOCK_STATE));
        else
            this.storedBlockState = null;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if(storedBlockState != null)
            tag.put(STORED_BLOCK_STATE, NbtUtils.writeBlockState(storedBlockState));
    }
}
