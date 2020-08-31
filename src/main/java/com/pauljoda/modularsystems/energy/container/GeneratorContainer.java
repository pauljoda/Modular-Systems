package com.pauljoda.modularsystems.energy.container;

import com.pauljoda.modularsystems.core.manager.ContainerManager;
import com.pauljoda.modularsystems.energy.tile.GeneratorTile;
import com.pauljoda.nucleus.common.container.BaseContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

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
public class GeneratorContainer  extends BaseContainer {
    public GeneratorTile tile;

    /**
     * Creates the container object
     *
     * @param id
     * @param playerInventory The players inventory
     * @param inventory       The tile/object inventory
     */
    public GeneratorContainer(int id, IInventory playerInventory, IItemHandler inventory) {
        super(ContainerManager.generator, id, playerInventory, inventory);
        addSlot(new SlotItemHandler(inventory, 0, 80, 58) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return inventory.isItemValidForSlot(0, stack);
            }
        });
        this.tile = (GeneratorTile) inventory;
        addPlayerInventorySlots(8, 84);
    }

    public GeneratorContainer(int windowId, PlayerInventory playerInv, PacketBuffer extraData) {
        this(windowId, playerInv,
                ((GeneratorTile) Minecraft.getInstance().world.getTileEntity(extraData.readBlockPos()))); // Only used on client
    }
}