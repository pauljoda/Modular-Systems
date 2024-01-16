package com.pauljoda.modularsystems.core.client.event;

import com.pauljoda.modularsystems.core.registry.BlockValueRegistry;
import com.pauljoda.nucleus.client.gui.GuiColor;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;


public class RenderBlockValuesEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onToolTipEvent(ItemTooltipEvent event) {
        var advancedTooltips = Minecraft.getInstance().options.advancedItemTooltips;
        if(advancedTooltips) {
            // Must have values
            if(event.getItemStack().getItem() instanceof BlockItem blockItem &&
                    (BlockValueRegistry.INSTANCE.isBlockRegistered(blockItem.getBlock().defaultBlockState()) ||
                            BlockValueRegistry.INSTANCE.hasBlockTagRegistered(blockItem.getBlock().defaultBlockState()))) {

                // Stub to show advanced
                if (!ClientUtils.isShiftPressed()) {
                    event.getToolTip().add(Component.literal(GuiColor.ORANGE + ClientUtils.translate("modular_systems.blockvalues.shift")));
                } else {
                    BlockState blockState = blockItem.getBlock().defaultBlockState();

                    double speedValue = BlockValueRegistry.INSTANCE.getSpeedValue(blockState, 1);
                    double efficiencyValue = BlockValueRegistry.INSTANCE.getEfficiencyValue(blockState, 1);
                    double multiplicityValue = BlockValueRegistry.INSTANCE.getMultiplicityValue(blockState, 1);

                    event.getToolTip().add(Component.literal(ClientUtils.translate("modular_systems.blockvalues.details")));
                    event.getToolTip().add(Component.literal(GuiColor.RED + ClientUtils.translate("modular_systems.speed.menu") + speedValue + " " + ClientUtils.translate("modular_systems.ticks")));
                    event.getToolTip().add(Component.literal(GuiColor.CYAN + ClientUtils.translate("modular_systems.efficiency.menu") + efficiencyValue + " " + ClientUtils.translate("modular_systems.ticks")));
                    event.getToolTip().add(Component.literal(GuiColor.GREEN + ClientUtils.translate("modular_systems.multiplicity.menu") + multiplicityValue + "X"));
                }
            }
        }
    }
}
