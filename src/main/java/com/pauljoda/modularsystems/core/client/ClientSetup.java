package com.pauljoda.modularsystems.core.client;

import com.pauljoda.modularsystems.core.client.event.RenderBlockValuesEvent;
import com.pauljoda.modularsystems.core.client.render.CuboidBankBER;
import com.pauljoda.modularsystems.core.client.render.CuboidProxyBER;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.furnace.screen.FurnaceCoreMenu;
import com.pauljoda.modularsystems.core.multiblock.providers.screen.CuboidBankSolidsMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(new RenderBlockValuesEvent());
        event.enqueueWork(() -> {
            // Register Screens

            // Furnace Core
            MenuScreens.register(Registration.FURNACE_CORE_CONTAINER.get(), FurnaceCoreMenu::new);

            // Providers
            // Solids
            MenuScreens.register(Registration.CUBOID_BANK_SOLIDS_CONTAINER.get(), CuboidBankSolidsMenu::new);
        });
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BlockEntityRenderers.register(Registration.CUBOID_BANK_SOLIDS_BLOCK_ENTITY.get(), CuboidBankBER::new);
        BlockEntityRenderers.register(Registration.CUBOID_PROXY_BLOCK_ENTITY.get(), CuboidProxyBER::new);
    }
}
