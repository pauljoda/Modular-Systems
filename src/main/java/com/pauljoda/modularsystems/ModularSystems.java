package com.pauljoda.modularsystems;

import com.mojang.logging.LogUtils;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.registry.BlockValueRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;

@Mod(Reference.MOD_ID)
public class ModularSystems {
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModularSystems(IEventBus modEventBus) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Registration
        Registration.init(modEventBus);

        Reference.CONFIG_LOCATION = String.format("%s%s%s%s",
                FMLPaths.CONFIGDIR.get().toString(),
                File.separator,
                Reference.MOD_ID,
                File.separator);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> BlockValueRegistry.INSTANCE.init());
    }
}
