package com.pauljoda.modularsystems.core;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.manager.ContainerManager;
import com.pauljoda.modularsystems.core.manager.TileManager;
import com.pauljoda.modularsystems.energy.renderer.EnergyStorageRenderer;
import com.pauljoda.modularsystems.energy.screen.GeneratorScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MOD_ID)
public class ModularSystems {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Public INSTANCE of this mod
     */
    public static ModularSystems INSTANCE;


    /**
     * The location of the config folder
     */
    public static String configFolderLocation;

    // Main load point
    @SuppressWarnings("deprecation")
    public ModularSystems() {
        // Register setup methods
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        });
    }

    /**
     * Common setup, called after all registering
     * @param event Setup event
     */
    private void setup(final FMLCommonSetupEvent event) {

    }

    /**
     * Common setup, called after all registering, client only
     * @param event Setup event
     */
    @OnlyIn(Dist.CLIENT)
    private void setupClient(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerManager.generator, GeneratorScreen::new);

        ClientRegistry.bindTileEntityRenderer(TileManager.energy_storage, EnergyStorageRenderer::new);
    }
}
