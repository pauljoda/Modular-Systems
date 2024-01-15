package com.pauljoda.modularsystems.power.providers.block;

import com.mojang.serialization.MapCodec;
import com.pauljoda.modularsystems.core.multiblock.block.entity.AbstractCuboidCoreBlockEntity;
import com.pauljoda.modularsystems.power.providers.block.entity.CuboidBankSolidsBlockEntity;
import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CuboidBankSolidsBlock extends CuboidBankBaseBlock implements IAdvancedToolTipProvider {

    public CuboidBankSolidsBlock() {
        super();
    }

    public CuboidBankSolidsBlock(Properties props) {
        this();
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
        pPlayer.openMenu((MenuProvider) pLevel.getBlockEntity(pPos), pPos);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(CuboidBankSolidsBlock::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CuboidBankSolidsBlockEntity(pPos, pState);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (pNewState.getBlock() != this) {
            if(pLevel.getBlockEntity(pPos) instanceof CuboidBankSolidsBlockEntity bank) {
                // Drop Ourselves
                var inventory = (InventoryHolder) bank.getItemCapability();
                Containers.dropContents(pLevel, pPos, inventory.inventoryContents);

                // Alert the core
                if(bank.getCore() != null) {
                    var core = bank.getCore();
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
        return List.of(ChatFormatting.GREEN + ClientUtils.translate("block.modular_systems.cuboid_bank_solids.desc"));
    }
}
