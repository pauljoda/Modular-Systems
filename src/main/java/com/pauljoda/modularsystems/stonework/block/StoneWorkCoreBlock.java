package com.pauljoda.modularsystems.stonework.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.AbstractCuboidCoreBlock;
import com.pauljoda.modularsystems.stonework.block.entity.StoneWorkCoreBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StoneWorkCoreBlock extends AbstractCuboidCoreBlock {

    public StoneWorkCoreBlock() {
        super();
    }

    public StoneWorkCoreBlock(Properties props) {
        super();
    }

    /**
     * Returns the codec for encoding and decoding FurnaceCoreBlock.
     *
     * @return The codec for FurnaceCoreBlock.
     */
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StoneWorkCoreBlock::new);
    }

    /**
     * Creates a new BlockEntity for the FurnaceCoreBlock.
     *
     * @param pPos   The position of the block.
     * @param pState The state of the block.
     * @return The new BlockEntity.
     */
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StoneWorkCoreBE(pPos, pState);
    }
}