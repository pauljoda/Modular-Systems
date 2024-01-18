package com.pauljoda.modularsystems.core.multiblock.cuboid.screen;

import com.pauljoda.modularsystems.core.multiblock.cuboid.container.AbstractCuboidCoreContainer;
import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.BaseWidget;
import com.pauljoda.nucleus.client.gui.widget.display.MenuTabCollection;
import com.pauljoda.nucleus.client.gui.widget.display.MenuWidgetText;
import com.pauljoda.nucleus.client.gui.widget.display.MenuWidgetTextureAnimated;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;
import java.util.ArrayList;

public class AbstractCuboidCoreScreen<T extends AbstractCuboidCoreContainer> extends MenuBase<T> {

    protected AbstractCuboidCoreContainer container;

    /**
     * Main constructor for Guis
     *
     * @param inventory       The container
     * @param playerInventory
     */
    public AbstractCuboidCoreScreen(T inventory,
                           Inventory playerInventory, Component title, ResourceLocation backGround) {
        super(inventory, playerInventory,
                title,
                175,
                165,
                backGround);

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
            furnaceInfo.add(new MenuWidgetText(this, 5, 23, "modular_systems.process_time.menu", Color.WHITE));
            double speed = core.getAdjustedProcessTime();
            var speedColor = speed < 200 ?
                    Color.GREEN :
                    speed == 200 ?
                            Color.WHITE :
                            Color.RED;
            var formatSpeed = new MenuWidgetText(this, 15, 33, "", speedColor);
            formatSpeed.setLabel(String.format("%.2f", speed) + " " + ClientUtils.translate("modular_systems.ticks"));
            furnaceInfo.add(formatSpeed);

            // Efficiency
            furnaceInfo.add(new MenuWidgetText(this, 5, 48, "modular_systems.efficiency.menu", Color.WHITE));
            var efficiency = core.values.getEfficiency();
            var efficiencyColor = efficiency >= 0 ?
                    Color.GREEN :
                    efficiency == 0 ?
                            Color.WHITE :
                            Color.RED;
            var efficiencyPrefix = efficiency > 0 ? "+" : "";
            var efficiencyFormat = new MenuWidgetText(this, 15, 58, "", efficiencyColor);
            efficiencyFormat.setLabel(efficiencyPrefix + String.format("%.2f", efficiency) + " " + ClientUtils.translate("modular_systems.ticks"));
            furnaceInfo.add(efficiencyFormat);

            // Multiplicity
            furnaceInfo.add(new MenuWidgetText(this, 5, 73, "modular_systems.multiplicity.menu", Color.WHITE));
            furnaceInfo.add(new MenuWidgetText(this, 15, 83, (core.values.getMultiplicity() + 1) + "X", Color.WHITE));

            tabs.addTab(furnaceInfo, 95, 100, 176, 31, new ItemStack(Items.BOOK));
        }
    }
}