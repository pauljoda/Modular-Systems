package com.pauljoda.modularsystems.core.multiblock;

import com.pauljoda.modularsystems.core.block.BaseBlock;
import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockExpansion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/31/20
 */
public class BaseMultiBlockBlock extends BaseBlock {

    /**
     * Base Constructor
     *
     * @param builder         Properties
     * @param name            Name for this block
     * @param tileEntityClass TileEntity class
     */
    protected BaseMultiBlockBlock(Properties builder, String name, Class<? extends TileEntity> tileEntityClass) {
        super(builder, name, tileEntityClass);
    }


    /**
     * Called when block is clicked, we will be using this to show the gui
     * @return
     */
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos,
                                             PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }
        else {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof IMultiBlockExpansion &&
                    ((IMultiBlockExpansion)tileentity).getCore() != null &&
                    worldIn.getTileEntity(((IMultiBlockExpansion)tileentity).getCore()) instanceof INamedContainerProvider) {
                INamedContainerProvider thisEntity = (INamedContainerProvider) worldIn.getTileEntity(((IMultiBlockExpansion)tileentity).getCore());
                NetworkHooks.openGui((ServerPlayerEntity) player, thisEntity, ((IMultiBlockExpansion)tileentity).getCore());
            }
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Called when the block is broken, allows us to drop items from inventory
     * @param worldIn The world
     * @param pos The pos
     */
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(worldIn.getTileEntity(pos) instanceof IMultiBlockExpansion) {
            IMultiBlockExpansion expansion = (IMultiBlockExpansion) worldIn.getTileEntity(pos);
            expansion.removeFromNetwork(true);
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if(worldIn.getTileEntity(pos) != null) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof AbstractMultiBlockExpansionTile) {
                AbstractMultiBlockExpansionTile expansionTile = (AbstractMultiBlockExpansionTile) tileEntity;
                expansionTile.searchAndConnect();
            }
        }
    }
}
