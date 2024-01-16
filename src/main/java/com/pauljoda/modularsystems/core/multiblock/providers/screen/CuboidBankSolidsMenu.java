package com.pauljoda.modularsystems.core.multiblock.providers.screen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankBaseBlockEntity;
import com.pauljoda.modularsystems.core.multiblock.providers.container.CuboidBankSolidsContainer;
import com.pauljoda.modularsystems.core.network.bidirectional.SyncableFieldPacket;
import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.BaseWidget;
import com.pauljoda.nucleus.client.gui.widget.control.MenuWidgetSetNumber;
import com.pauljoda.nucleus.client.gui.widget.display.MenuTabCollection;
import com.pauljoda.nucleus.client.gui.widget.display.MenuWidgetText;
import com.pauljoda.nucleus.network.PacketManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;
import java.util.ArrayList;

public class CuboidBankSolidsMenu extends MenuBase<CuboidBankSolidsContainer> {

    protected CuboidBankSolidsContainer container;

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

        this.container = inventory;

        addRightTabs(rightTabs);
        addComponents();
    }

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    @Override
    protected void addComponents() {}

    @Override
    protected void addRightTabs(MenuTabCollection tabs) {
        if(container != null && container.getBank() != null) {
            var bank = container.getBank();
            var priorityTab = new ArrayList<BaseWidget>();

            // Priority Title
            priorityTab.add(new MenuWidgetText(this, 26, 6, "priority.menu", Color.ORANGE));

            // Set Number
            priorityTab.add(new MenuWidgetSetNumber(this, 26, 25,
                    206, 0, 40,
                    bank.getPriority(), -100, 100) {
                @Override
                protected void setValue(int newValue) {
                    PacketManager.INSTANCE
                            .sendToServer(
                                    new SyncableFieldPacket(false,
                                            CuboidBankBaseBlockEntity.UPDATE_PRIORITY,
                                            newValue,
                                            bank.getBlockPos()));
                }
            });

            // Build the Tab
            tabs.addTab(priorityTab, 100, 50,
                    176, 0, new ItemStack(Blocks.ANVIL));
        }
    }
}