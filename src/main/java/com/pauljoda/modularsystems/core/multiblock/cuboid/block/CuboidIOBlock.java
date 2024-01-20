package com.pauljoda.modularsystems.core.multiblock.cuboid.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidIOBE;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.common.UpdatingBlock;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CuboidIOBlock extends UpdatingBlock implements IAdvancedToolTipProvider {

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
            Block.box(2, 2, 2, 14, 14, 14));

    public CuboidIOBlock() {
        super(Properties.of().strength(2.0F).noOcclusion());
    }

    public CuboidIOBlock(Properties props) {
        this();
    }

    /*******************************************************************************************************************
     * Block                                                                                                           *
     *******************************************************************************************************************/

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CuboidIOBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuboidIOBE(pPos, pState);
    }

    /**
     * Opens the container associated with the given position and allows the player to interact with it.
     *
     * @param pLevel  The level in which the container exists.
     * @param pPos    The position of the container.
     * @param pPlayer The player interacting with the container.
     */
    protected void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {
        pPlayer.openMenu((MenuProvider) pLevel.getBlockEntity(pPos), pPos);
    }

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
            if (pLevel.getBlockEntity(pPos) instanceof CuboidIOBE) {
                this.openContainer(pLevel, pPos, pPlayer);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    /**
     * Removes a block from the world, performing necessary actions such as dropping items and alerting the core in the case of a CuboidBankSolidsBlock.
     *
     * @param pState The current block state.
     * @param pLevel The level in which the block exists.
     * @param pPos   The position of the block.
     * @param pNewState The new block state after removal.
     * @param pMovedByPiston Whether the block was moved by a piston.
     */
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pLevel.isClientSide && pNewState.getBlock() != this) {
            if(pLevel.getBlockEntity(pPos) instanceof CuboidIOBE ioBlock) {
                // Alert the core
                if(ioBlock.getCore() != null) {
                    var core = ioBlock.getCore();
                    core.deconstructMultiblock();
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    /*******************************************************************************************************************
     * IAdvancedToolTipProvided                                                                                        *
     *******************************************************************************************************************/

    /**
     * Get the tool tip to present when shift is pressed
     *
     * @param itemStack The itemstack
     * @return The list to display
     */
    @Nullable
    @Override
    public List<String> getAdvancedToolTip(@NotNull ItemStack itemStack) {
        return List.of(ChatFormatting.GREEN + ClientUtils.translate("block.modular_systems.cuboid_io.desc"));
    }
}
