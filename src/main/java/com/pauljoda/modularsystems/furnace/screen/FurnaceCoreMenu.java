package com.pauljoda.modularsystems.furnace.screen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.furnace.container.FurnaceCoreContainer;
import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.BaseWidget;
import com.pauljoda.nucleus.client.gui.widget.display.MenuTabCollection;
import com.pauljoda.nucleus.client.gui.widget.display.MenuWidgetText;
import com.pauljoda.nucleus.client.gui.widget.display.MenuWidgetTextureAnimated;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;
import java.util.ArrayList;

public class FurnaceCoreMenu extends MenuBase<FurnaceCoreContainer> {

    protected FurnaceCoreContainer container;

    /**
     * Main constructor for Guis
     *
     * @param inventory       The container
     * @param playerInventory
     */
    public FurnaceCoreMenu(FurnaceCoreContainer inventory,
                           Inventory playerInventory, Component title) {
        super(inventory, playerInventory,
                Component.translatable("block.modular_systems.furnace_core"),
                175,
                165,
                new ResourceLocation(Reference.MOD_ID, "textures/gui/furnace_core.png"));

        container = inventory;

        // Need to run again since we now have reference to container
        addRightTabs(rightTabs);
    }

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    @Override
    protected void addComponents() {
        // Burn Time
        components.add(new MenuWidgetTextureAnimated(this, 81, 53, 176, 0, 14, 14, MenuWidgetTextureAnimated.ANIMATION_DIRECTION.UP) {
            @Override
            protected int getCurrentProgress(int i) {
                return (int) ((container.getFuelTime() * i) / Math.max(container.getCurrentFuelProvidedTime(), 0.001));
            }
        });

        // Arrow
        components.add(new MenuWidgetTextureAnimated(this, 79, 34, 176, 14, 24, 17, MenuWidgetTextureAnimated.ANIMATION_DIRECTION.RIGHT) {
            @Override
            protected int getCurrentProgress(int scale) {
                return ((container.getWorkTime() * scale) / container.getScaledProcessTime());
            }
        });
    }

    @Override
    protected void addRightTabs(MenuTabCollection tabs) {
        if (container != null && container.getCore() != null) {
            var core = container.getCore();
            var furnaceInfo = new ArrayList<BaseWidget>();

            // Information
            furnaceInfo.add(new MenuWidgetText(this, 26, 6, "modular_systems.information.menu", Color.ORANGE));

            // Speed
            furnaceInfo.add(new MenuWidgetText(this, 5, 23, "modular_systems.speed.menu", Color.WHITE));
            double speed = (-1 * (((core.values.getSpeed() + 200) / 200) - 1)) != 0 ?
                    (-1 * (((core.values.getSpeed() + 200) / 200) - 1)) * 100 :
                    0.00;
            var speedColor = speed > 0 ?
                    Color.GREEN :
                    speed == 0 ?
                            Color.WHITE :
                            Color.RED;
            var formatSpeed = new MenuWidgetText(this, 15, 33, "", speedColor);
            formatSpeed.setLabel(String.format("%.2f", speed) + "%");
            furnaceInfo.add(formatSpeed);

            // Efficiency
            furnaceInfo.add(new MenuWidgetText(this, 5, 48, "modular_systems.efficiency.menu", Color.WHITE));
            var efficiency = (-1 * (100 - ((1600 + core.values.getEfficiency()) / 1600) * 100) != 0) ?
                    -1 * (100 - ((1600 + core.values.getEfficiency()) / 1600) * 100) :
                    0.00D;
            var efficiencyColor = efficiency > 0 ?
                    Color.GREEN :
                    efficiency == 0 ?
                            Color.WHITE :
                            Color.RED;
            var efficiencyFormat = new MenuWidgetText(this, 15, 58, "", efficiencyColor);
            efficiencyFormat.setLabel(String.format("%.2f", efficiency));
            furnaceInfo.add(efficiencyFormat);

            // Multiplicity
            furnaceInfo.add(new MenuWidgetText(this, 5, 73, "modular_systems.multiplicity.menu", Color.WHITE));
            furnaceInfo.add(new MenuWidgetText(this, 15, 83, (core.values.getMultiplicity() + 1) + "X", Color.WHITE));

            tabs.addTab(furnaceInfo, 95, 100, 176, 31, new ItemStack(Items.BOOK));
        }
    }
}
