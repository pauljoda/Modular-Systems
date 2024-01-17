package com.pauljoda.modularsystems.furnace.screen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.multiblock.cuboid.screen.AbstractCuboidCoreScreen;
import com.pauljoda.modularsystems.furnace.container.FurnaceCoreContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FurnaceCoreMenu extends AbstractCuboidCoreScreen<FurnaceCoreContainer> {

    /**
     * Main constructor for Guis
     *
     * @param inventory       The container
     * @param playerInventory
     * @param title
     */
    public FurnaceCoreMenu(FurnaceCoreContainer inventory, Inventory playerInventory, Component title) {
        super(inventory, playerInventory, title, new ResourceLocation(Reference.MOD_ID, "textures/gui/furnace_core.png"));
    }
}
