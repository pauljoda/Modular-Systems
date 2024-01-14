package com.pauljoda.modularsystems.core.multiblock.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.block.entity.CuboidProxyBlockEntity;
import com.pauljoda.nucleus.common.UpdatingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CuboidProxyBlock extends UpdatingBlock {

    /**
     * Main Constructor
     */
    public CuboidProxyBlock() {
        super(Properties.of().strength(2.0F));
    }

    /**
     * Stub, just for codec
     * @param props
     */
    public CuboidProxyBlock(Properties props) {
        this();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CuboidProxyBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuboidProxyBlockEntity(pPos, pState);
    }
}
