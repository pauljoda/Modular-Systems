package com.pauljoda.modularsystems.core.multiblock;

import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockCore;
import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockExpansion;
import com.pauljoda.nucleus.common.tiles.UpdatingTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;

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
public abstract class AbstractMultiBlockExpansionTile extends UpdatingTile implements IMultiBlockExpansion, INamedContainerProvider, ICapabilityProvider {

    /*******************************************************************************************************************
     * Class Variables                                                                                                 *
     *******************************************************************************************************************/

    // List of children
    protected List<BlockPos> children = new ArrayList<>();

    // Reference to core
    protected BlockPos core = null;

    public AbstractMultiBlockExpansionTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Called when this tile is removed from the network, only modify the core values needed, should mirror
     * what was done when added to network
     */
    public abstract void removedFromNetwork();

    /*******************************************************************************************************************
     * IMultiBlockExpansion                                                                                            *
     *******************************************************************************************************************/

    /**
     * Get an instance of the core, null if not found
     *
     * @return Core position or null
     */
    @Override
    public BlockPos getCore() {
        return core == null ? null : core;
    }

    /**
     * Get instance of the core, not just the position
     * @return The core object
     */
    public IMultiBlockCore getCoreItself() {
        return getCore() != null ? (IMultiBlockCore) world.getTileEntity(getCore()) : null;
    }

    /**
     * Sets the core to the given core
     *
     * @param core
     */
    @Override
    public void setCore(BlockPos core) {
        this.core = core;
    }


    /**
     * Adds dependants to this node, all children will be alerted when this changes so we need to track them
     *
     * @param pos Child position
     */
    @Override
    public void addChild(BlockPos pos) {
        children.add(pos);
    }

    /**
     * Get list of children
     *
     * @return Dependants to this block
     */
    @Override
    public List<BlockPos> getChildren() {
        return children;
    }

    /**
     * Called when this block is removed from the network
     *
     * @param deleteSelf
     */
    @Override
    public void removeFromNetwork(boolean deleteSelf) {
        // Tell core we are gone
        if(getCore() != null && deleteSelf) {
            getCoreItself().removeExpansion(this);
            removedFromNetwork();
            world.notifyBlockUpdate(getCore(), getWorld().getBlockState(getCore()), getWorld().getBlockState(getCore()), 3);
            world.getTileEntity(core).markDirty();
            core = null;
        }

        // Alert children
        for(BlockPos child : children) {
            if (world.getTileEntity(child) instanceof IMultiBlockExpansion)
                ((IMultiBlockExpansion) world.getTileEntity(child)).removeFromNetwork(true);
        }

        children.clear();
        markForUpdate(3);
    }

    /*******************************************************************************************************************
     * Tile Entity                                                                                                     *
     *******************************************************************************************************************/

    @Override
    protected void onServerTick() {
        super.onServerTick();

        // Look for multiblocks to join
        if(core == null && getWorld().getGameTime() % 80 == 0)
            searchAndConnect();
        else if(getCore() == null && world.getGameTime() % 20 == 0)
            removeFromNetwork(true);
    }

    /**
     * Look for a way to join a multiblock
     */
    protected void searchAndConnect() {
        for(Direction dir : Direction.values()) {
            if(world.getTileEntity(pos.offset(dir)) != null) {
                TileEntity otherTile = world.getTileEntity(pos.offset(dir));

                // We found a core!
                if(otherTile instanceof IMultiBlockCore) {
                    IMultiBlockCore foundCore = (IMultiBlockCore) otherTile;
                    if(foundCore.isExpansionAllowed(this)) {
                        this.core = otherTile.getPos();
                        foundCore.addExpansion(this);
                        addedToNetwork();
                        world.notifyBlockUpdate(getCore(), getWorld().getBlockState(getCore()), getWorld().getBlockState(getCore()), 3);
                        world.getTileEntity(core).markDirty();
                        markForUpdate(3);
                    }
                } else if (getCore() == null && otherTile instanceof IMultiBlockExpansion) { // Other Expansion
                    IMultiBlockExpansion expansion = (IMultiBlockExpansion) otherTile;
                    if(expansion.getCore() != null &&
                            canJoinMultiBlock((IMultiBlockCore) world.getTileEntity(expansion.getCore())) &&
                            ((IMultiBlockCore)world.getTileEntity(expansion.getCore())).isExpansionAllowed(this)) {
                        core = expansion.getCore();
                        expansion.addChild(getPos());
                        addedToNetwork();
                        world.notifyBlockUpdate(getCore(), getWorld().getBlockState(getCore()), getWorld().getBlockState(getCore()), 3);
                        world.getTileEntity(core).markDirty();
                        markForUpdate(3);
                    }
                }
            }
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        readChildren(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        writeChildren(compound);
        return compound;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return getCore() != null ?
                world.getTileEntity(getCore()).getCapability(cap, side)
                : super.getCapability(cap, side);
    }

    /*******************************************************************************************************************
     * INamedContainerProvider                                                                                         *
     *******************************************************************************************************************/

    @Override
    public ITextComponent getDisplayName() {
        return getCore() != null ?
                ((INamedContainerProvider)world.getTileEntity(getCore())).getDisplayName()
                : new TranslationTextComponent("proxy");
    }

    @Override
    public Container createMenu(int windowID, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return getCore() != null ?
                ((INamedContainerProvider)world.getTileEntity(getCore())).createMenu(windowID, playerInventory, playerEntity)
                : null;
    }
}
