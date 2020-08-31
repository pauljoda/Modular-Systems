package com.pauljoda.modularsystems.core.multiblock.interfaces;

import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockCore;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import java.util.List;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/30/20
 */
public interface IMultiBlockExpansion {

    /*******************************************************************************************************************
     * Interface Methods                                                                                               *
     *******************************************************************************************************************/

    /**
     * Get block pos, should merge with tile method
     * @return BlockPos for tile
     */
    BlockPos getPos();

    /**
     * Get an instance of the core, null if not found
     * @return Core position or null
     */
    BlockPos getCore();

    /**
     * Sets the core to the given core
     */
    void setCore(BlockPos core);

    /**
     * Called when this expansion is added to a network, called after core has become aware of us
     */
    void addedToNetwork();

    /**
     * Called when this block is removed from the network
     */
    void removeFromNetwork(boolean deleteSelf);

    /**
     * Adds dependants to this node, all children will be alerted when this changes so we need to track them
     * @param pos Child position
     */
    void addChild(BlockPos pos);

    /**
     * Get list of children
     * @return Dependants to this block
     */
    List<BlockPos> getChildren();

    /**
     * Tests if this block can join another multiblock based on core type,
     * @param otherCore Core to check against
     * @return True if able to join that network
     */
    boolean canJoinMultiBlock(IMultiBlockCore otherCore);

    /*******************************************************************************************************************
     * Default Methods                                                                                                 *
     *******************************************************************************************************************/

    /**
     * Write data about network
     * @param tag Tile tag to attach to
     */
    default void writeChildren(CompoundNBT tag) {
        if(!getChildren().isEmpty()) {
            tag.putInt("ChildSize", getChildren().size());
            for(int i = 0; i < getChildren().size(); i++)
                tag.putLong("Child" + i, getChildren().get(i).toLong());
        }

        if(getCore() != null)
            tag.putLong("CoreLocation", getCore().toLong());
    }

    default void readChildren(CompoundNBT tag) {
        if(tag.contains("ChildSize")) {
            for(int i = 0; i < tag.getInt("ChildSize"); i++)
                getChildren().add(BlockPos.fromLong(tag.getLong("Child" + i)));
        }

        if(tag.contains("CoreLocation"))
            setCore(BlockPos.fromLong(tag.getLong("CoreLocation")));
        else
            setCore(null);
    }
}
