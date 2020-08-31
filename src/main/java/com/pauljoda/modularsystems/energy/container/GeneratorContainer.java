package com.pauljoda.modularsystems.energy.container;

import com.pauljoda.modularsystems.core.manager.ContainerManager;
import com.pauljoda.modularsystems.energy.tile.GeneratorTile;
import com.pauljoda.nucleus.common.container.ContainerGeneric;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;

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
public class GeneratorContainer extends ContainerGeneric {
    public GeneratorTile tile;

    /**
     * Creates the container object
     *
     * @param id
     * @param playerInventory The players inventory
     * @param inventory       The tile/object inventory
     */
    public GeneratorContainer(int id, IInventory playerInventory, GeneratorTile inventory) {
        super(ContainerManager.generator, id);
        this.tile = (GeneratorTile) inventory;
    }

    public GeneratorContainer(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
        this(windowId, playerInv,
                ((GeneratorTile) Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos()))); // Only used on client
    }
}