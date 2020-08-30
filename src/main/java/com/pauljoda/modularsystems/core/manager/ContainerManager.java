package com.pauljoda.modularsystems.core.manager;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.generator.container.GeneratorContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/20
 */
@ObjectHolder(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerManager {

    /*******************************************************************************************************************
     * Block Instances                                                                                                 *
     *******************************************************************************************************************/

    @ObjectHolder("generator")
    public static ContainerType<GeneratorContainer> generator;

    /*******************************************************************************************************************
     * Block Instances                                                                                                 *
     *******************************************************************************************************************/

    @SubscribeEvent
    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(IForgeContainerType.create(GeneratorContainer::new).setRegistryName("generator"));
    }
}