package com.pauljoda.modularsystems.power.providers.block;

import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.modularsystems.power.providers.block.entity.CuboidBankBaseBlockEntity;
import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.UpdatingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class CuboidBankBaseBlock extends UpdatingBlock {
    public CuboidBankBaseBlock() {
        super(Properties.of().strength(2.0F));
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
            if (pLevel.getBlockEntity(pPos) instanceof CuboidBankBaseBlockEntity) {
                this.openContainer(pLevel, pPos, pPlayer);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
}
