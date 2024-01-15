package com.pauljoda.modularsystems.power.providers.screen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.power.providers.container.CuboidBankSolidsContainer;
import com.pauljoda.nucleus.client.gui.MenuBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CuboidBankSolidsMenu extends MenuBase<CuboidBankSolidsContainer> {

    /**
     * Main constructor for Guis
     *
     * @param inventory       The container
     * @param playerInventory
     */
    public CuboidBankSolidsMenu(CuboidBankSolidsContainer inventory,
                                Inventory playerInventory, Component title) {
        super(inventory, playerInventory,
                Component.translatable("block.modular_systems.cuboid_bank_solids"),
                175,
                165,
                new ResourceLocation(Reference.MOD_ID, "textures/gui/cuboid_bank_solids.png"));
    }

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    @Override
    protected void addComponents() {

    }
}