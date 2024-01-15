package com.pauljoda.modularsystems.core.client;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.furnace.screen.FurnaceCoreMenu;
import com.pauljoda.modularsystems.power.providers.screen.CuboidBankSolidsMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Register Screens

            // Furnace Core
            MenuScreens.register(Registration.FURNACE_CORE_CONTAINER.get(), FurnaceCoreMenu::new);

            // Providers
            // Solids
            MenuScreens.register(Registration.CUBOID_BANK_SOLIDS_CONTAINER.get(), CuboidBankSolidsMenu::new);
        });
    }
}
