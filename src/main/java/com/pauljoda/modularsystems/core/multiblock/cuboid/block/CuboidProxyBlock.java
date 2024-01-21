package com.pauljoda.modularsystems.core.multiblock.cuboid.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidProxyBlockHolderBE;
import com.pauljoda.nucleus.connected.UpdatingConnectedTextureBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CuboidProxyBlock extends UpdatingConnectedTextureBlock {

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
        return new CuboidProxyBlockHolderBE(pPos, pState);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_309084_, BlockGetter p_309133_, BlockPos p_309097_) {
        return true;
    }

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
            if(pLevel.getBlockEntity(pPos) instanceof CuboidProxyBlockHolderBE proxy &&
                proxy.getCore() != null) {
                var core = proxy.getCore();
                if(core.values.isWellFormed()) {
                    ((AbstractCuboidCoreBlock) pLevel.getBlockState(core.getBlockPos()).getBlock())
                            .openContainer(pLevel, core.getBlockPos(), pPlayer);
                    return InteractionResult.CONSUME;
                } else {
                    core.values.setDirty(true);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Called when this block is removed from the world.
     *
     * @param pState The current state of the block being removed.
     * @param pLevel The level the block is being removed from.
     * @param pPos The position of the block being removed.
     * @param pNewState The new state of the block replacing the removed block.
     * @param pMovedByPiston True if the block was moved by a piston, false otherwise.
     */
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pNewState.getBlock() != this) {
            if(pLevel.getBlockEntity(pPos) instanceof CuboidProxyBlockHolderBE proxy) {
                // Alert the core
                if(proxy.getCore() != null) {
                    var core = proxy.getCore();
                    core.deconstructMultiblock();
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
