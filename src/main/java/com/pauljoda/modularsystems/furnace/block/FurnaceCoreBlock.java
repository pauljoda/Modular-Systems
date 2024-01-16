package com.pauljoda.modularsystems.furnace.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.block.AbstractCuboidCoreBlock;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FurnaceCoreBlock extends AbstractCuboidCoreBlock {

    public FurnaceCoreBlock() {
        super();
    }

    public FurnaceCoreBlock(Properties props) {
        super();
    }

    /**
     * Returns the codec for encoding and decoding FurnaceCoreBlock.
     *
     * @return The codec for FurnaceCoreBlock.
     */
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(FurnaceCoreBlock::new);
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
        return new FurnaceCoreBlockEntity(pPos, pState);
    }
}
