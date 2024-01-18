package com.pauljoda.modularsystems.stonework.screen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.multiblock.cuboid.screen.AbstractCuboidCoreScreen;
import com.pauljoda.modularsystems.stonework.container.StoneWorkContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StoneWorkCoreMenu extends AbstractCuboidCoreScreen<StoneWorkContainer> {

    /**
     * Main constructor for Guis
     *
     * @param inventory       The container
     * @param playerInventory
     * @param title
     */
    public StoneWorkCoreMenu(StoneWorkContainer inventory, Inventory playerInventory, Component title) {
        super(inventory, playerInventory, title, new ResourceLocation(Reference.MOD_ID, "textures/gui/stone_work_core.png"));
    }
}