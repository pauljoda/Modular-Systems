package com.pauljoda.modularsystems.furnace.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.block.AbstractCuboidCoreBlock;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBlockEntity;
import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.common.blocks.entity.item.InventoryHandler;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.CommonHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FurnaceCoreBlock extends AbstractCuboidCoreBlock implements IAdvancedToolTipProvider {

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
    public void openContainer(Level pLevel, BlockPos pPos, Player pPlayer) {
        pPlayer.openMenu((MenuProvider) pLevel.getBlockEntity(pPos), pPos);
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

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(LIT)) {
            double d0 = (double)pPos.getX() + 0.5;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + 0.5;
            if (pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52;
            double d4 = pRandom.nextDouble() * 0.6 - 0.3;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : d4;
            double d6 = pRandom.nextDouble() * 6.0 / 16.0;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : d4;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
            pLevel.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
        }
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
        return List.of(ChatFormatting.GREEN + ClientUtils.translate("block.modular_systems.furnace_core.desc"));
    }
}
