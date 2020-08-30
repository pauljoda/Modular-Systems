package com.pauljoda.modularsystems.generator.block;

import com.pauljoda.modularsystems.core.block.BaseBlock;
import com.pauljoda.modularsystems.generator.tile.GeneratorTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraftforge.common.ToolType;

import static net.minecraft.block.AbstractFurnaceBlock.FACING;
import static net.minecraft.block.AbstractFurnaceBlock.LIT;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/20
 */
public class GeneratorBlock extends BaseBlock {

    /*******************************************************************************************************************
     * Constructor                                                                                                     *
     *******************************************************************************************************************/

    public GeneratorBlock() {
        super(Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F),
                "generator", GeneratorTile.class);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(LIT, Boolean.FALSE));
    }

    /*******************************************************************************************************************
     * Block Methods                                                                                                   *
     *******************************************************************************************************************/

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     * fine.
     */
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }
}
