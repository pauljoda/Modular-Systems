package com.pauljoda.modularsystems.core.block;

import com.pauljoda.nucleus.util.WorldUtils;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

/**
 * This file was created for AssistedProgression
 * <p>
 * AssistedProgression is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/27/2019
 */
public class BaseBlock extends ContainerBlock {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    // Tile class, will make new instance per block created
    private Class<? extends TileEntity> tileClass;

    // Name in the registry
    public String registryName;

    /**
     * Base Constructor
     * @param builder Properties
     * @param name Name for this block
     * @param tileEntityClass TileEntity class
     */
    protected BaseBlock(Properties builder, String name, Class<? extends TileEntity> tileEntityClass) {
        super(builder);
        this.tileClass = tileEntityClass;
        this.registryName = name;

        setRegistryName(name);
    }

    /*******************************************************************************************************************
     * Block Methods                                                                                                   *
     *******************************************************************************************************************/

    /**
     * Called when the block is broken, allows us to drop items from inventory
     * @param worldIn The world
     * @param pos The pos
     */
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!worldIn.isRemote) {
            if(worldIn.getTileEntity(pos) != null
                    && worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent()) {
                // Drop the inventory
                WorldUtils.dropStacksInInventory(worldIn.getTileEntity(pos)
                        .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElseGet(null), worldIn, pos);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
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
            if (tileentity instanceof INamedContainerProvider) {
                INamedContainerProvider grinder = (INamedContainerProvider) tileentity;
                NetworkHooks.openGui((ServerPlayerEntity) player, grinder, pos);
            }
            return ActionResultType.SUCCESS;
        }
    }

    /**
     * Things with containers default to not use the normal models, kinda dumb so lets put it back to normal
     */
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /*******************************************************************************************************************
     * ContainerBlock                                                                                                  *
     *******************************************************************************************************************/

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        if(tileClass != null)
            try {
                return tileClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        return null;
    }
}