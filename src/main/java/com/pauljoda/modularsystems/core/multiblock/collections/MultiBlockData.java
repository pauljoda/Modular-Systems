package com.pauljoda.modularsystems.core.multiblock.collections;

import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockExpansion;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
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
public class MultiBlockData {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/

    // Holds reference to all blocks that depend on this one
    protected List<BlockPos> nodes = new ArrayList<>();

    /*******************************************************************************************************************
     * MultiBlock                                                                                                      *
     *******************************************************************************************************************/

    /**
     * Used to add a new node into the network
     * @param node The node to add
     */
    public void addNode(BlockPos node) {
        nodes.add(node);
    }

    /**
     * Get the instance of the nodes
     * @return List of nodes
     */
    public List<BlockPos> getNodes() {
        return nodes;
    }

    /**
     * Used to get the node associated with a location
     * @param loc The location of the node
     * @return The node that is at that location
     */
    public BlockPos getNode(BlockPos loc) {
        for(BlockPos node : nodes) {
            if(node.equals(loc))
                return node;
        }
        return null;
    }

    /**
     * Deletes the node in the network
     * @param node The node to add
     * @return True if found and deleted
     */
    public boolean deleteNode(IMultiBlockExpansion node) {
        for(Iterator<BlockPos> iterator = nodes.iterator(); iterator.hasNext(); ) {
            BlockPos child = iterator.next();
            if(child.equals(node.getPos())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }


    /**
     * Used to let the children know to remove from the network
     * @param world The world
     */
    public void destroyNetwork(World world) {
        for(BlockPos loc : nodes) {
            if(!world.isRemote && world.getTileEntity(loc) != null && world.getTileEntity(loc) instanceof IMultiBlockExpansion)
                ((IMultiBlockExpansion)world.getTileEntity(loc)).removeFromNetwork(false);
        }
    }

    /*******************************************************************************************************************
     * Data                                                                                                            *
     *******************************************************************************************************************/

    /**
     * Write the data to given NBT
     * @param tag Tag to write
     */
    public void write(CompoundNBT tag) {
        if(nodes != null && !nodes.isEmpty()) {
            tag.putInt("ChildSize", nodes.size());
            for(int i = 0; i < nodes.size(); i++)
                tag.putLong( "child" + i, nodes.get(i).toLong());
        }
    }

    /**
     * Read data from NBT
     * @param tag Tag to read from, usually tile tag
     */
    public void read(CompoundNBT tag) {
        if(tag.contains("ChildSize")) {
            nodes = new ArrayList<>();
            for(int i = 0; i < tag.getInt("ChildSize"); i++) {
                nodes.add(BlockPos.fromLong(tag.getLong("child" + i)));
            }
        }
    }
}
