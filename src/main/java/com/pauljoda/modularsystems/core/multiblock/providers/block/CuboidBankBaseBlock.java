package com.pauljoda.modularsystems.core.multiblock.providers.block;

import com.google.common.collect.ImmutableMap;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankBaseBE;
import com.pauljoda.nucleus.common.UpdatingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public abstract class CuboidBankBaseBlock extends UpdatingBlock {

    public static VoxelShape SHAPE = Shapes.or(
            Block.box(0, 14, 0, 16, 16, 2),
            Block.box(0, 14, 2, 2, 16, 14),
            Block.box(14, 14, 2, 16, 16, 14),
            Block.box(0, 14, 14, 16, 16, 16),
            Block.box(0, 0, 0, 16, 2, 2),
            Block.box(0, 0, 14, 16, 2, 16),
            Block.box(0, 0, 2, 2, 2, 14),
            Block.box(14, 0, 2, 16, 2, 14),
            Block.box(0, 2, 0, 2, 14, 2),
            Block.box(14, 2, 0, 16, 14, 2),
            Block.box(14, 2, 14, 16, 14, 16),
            Block.box(0, 2, 14, 2, 14, 16),
            Block.box(6, 12, 1, 10, 13, 2),
            Block.box(6, 3, 1, 10, 4, 2),
            Block.box(6, 4, 1, 7, 12, 2),
            Block.box(9, 4, 1, 10, 12, 2),
            Block.box(7, 4, 1.8, 9, 12, 1.8),
            Block.box(1, 12, 6, 2, 13, 10),
            Block.box(1, 3, 6, 2, 4, 10),
            Block.box(1, 4, 6, 2, 12, 7),
            Block.box(1, 4, 9, 2, 12, 10),
            Block.box(1.8, 4, 7, 1.8, 12, 9),
            Block.box(6, 12, 14, 10, 13, 15),
            Block.box(6, 3, 14, 10, 4, 15),
            Block.box(6, 4, 14, 7, 12, 15),
            Block.box(9, 4, 14, 10, 12, 15),
            Block.box(7, 4, 14.2, 9, 12, 14.2),
            Block.box(14, 12, 6, 15, 13, 10),
            Block.box(14, 3, 6, 15, 4, 10),
            Block.box(14, 4, 9, 15, 12, 10),
            Block.box(14, 4, 6, 15, 12, 7),
            Block.box(14.2, 4, 7, 14.2, 12, 9),
            Block.box(2, 2, 2, 14, 14, 14));

    public CuboidBankBaseBlock() {
        super(Properties.of().strength(2.0F).noOcclusion());
    }

    public CuboidBankBaseBlock(Properties props) {
        this();
    }

    /**
     * Opens the container associated with the given position and allows the player to interact with it.
     *
     * @param pLevel  The level in which the container exists.
     * @param pPos    The position of the container.
     * @param pPlayer The player interacting with the container.
     */
    protected abstract void openContainer(Level pLevel, BlockPos pPos, Player pPlayer);

    /*******************************************************************************************************************
     * Block                                                                                                           *
     *******************************************************************************************************************/

    /**
     * Retrieves the render shape of the block state.
     *
     * @param state The block state.
     * @return The render shape of the block state.
     */
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    /**
     * Retrieves the shape of the block state for rendering and collision purposes.
     *
     * @param pState   The block state.
     * @param pLevel   The level in which the block exists.
     * @param pPos     The position of the block.
     * @param pContext The collision context.
     * @return The shape of the block state.
     */
    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    /**
     * Executes the use action of the block.
     *
     * @param pState  The block state of the block being used.
     * @param pLevel  The level in which the block is used.
     * @param pPos    The position of the block being used.
     * @param pPlayer The player using the block.
     * @param pHand   The player's hand used to interact with the block.
     * @param pHit    The hit result of the interaction with the block.
     * @return The result of the use action.
     */
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (pLevel.getBlockEntity(pPos) instanceof CuboidBankBaseBE) {
                this.openContainer(pLevel, pPos, pPlayer);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
}
