package com.pauljoda.modularsystems.core.multiblock.block;

import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.UpdatingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public abstract class AbstractCuboidCoreBlock extends UpdatingBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public AbstractCuboidCoreBlock() {
        super(Properties.of().strength(2.0F));

        registerDefaultState(getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LIT, false));
    }

    public AbstractCuboidCoreBlock(Properties props) {
        this();
    }

    /**
     * Opens the container associated with the given position and allows the player to interact with it.
     *
     * @param pLevel  The level in which the container exists.
     * @param pPos    The position of the container.
     * @param pPlayer The player interacting with the container.
     */
    public abstract void openContainer(Level pLevel, BlockPos pPos, Player pPlayer);

    /*******************************************************************************************************************
     * Block                                                                                                           *
     *******************************************************************************************************************/

    /**
     * Adds the FACING and LIT properties to the block's state definition.
     *
     * @param blockBlockStateBuilder The builder for the block's state definition.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING).add(LIT);
    }

    /**
     * Returns the block state to be placed based on the given placement context.
     *
     * @param context The context in which the block is being placed.
     * @return The block state for placement.
     */
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction).setValue(LIT, false);
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
            if(pLevel.getBlockEntity(pPos) instanceof AbstractCuboidCoreBlockEntity cuboid) {
                if(cuboid.values.isWellFormed()) {
                    this.openContainer(pLevel, pPos, pPlayer);
                    return InteractionResult.CONSUME;
                } else {
                    cuboid.values.setDirty(true);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pNewState.getBlock() != this) {
            if(pLevel.getBlockEntity(pPos) instanceof AbstractCuboidCoreBlockEntity core) {
                // Drop our stuff
                var inventory = (InventoryHolder) core.getItemCapability();
                Containers.dropContents(pLevel, pPos, inventory.inventoryContents);

                // Revert everyone
                core.deconstructMultiblock();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }
}
