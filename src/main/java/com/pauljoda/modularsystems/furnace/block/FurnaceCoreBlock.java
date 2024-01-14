package com.pauljoda.modularsystems.furnace.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.block.AbstractCuboidCoreBlock;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
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
     * Opens the container associated with the given position and allows the player to interact with it.
     *
     * @param pLevel  The level in which the container exists.
     * @param pPos    The position of the container.
     * @param pPlayer The player interacting with the container.
     */
    @Override
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {

    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(FurnaceCoreBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FurnaceCoreBlockEntity(pPos, pState);
    }
}
