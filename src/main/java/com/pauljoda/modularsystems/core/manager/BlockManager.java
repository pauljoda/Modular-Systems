package com.pauljoda.modularsystems.core.manager;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.block.EnergyStorageBlock;
import com.pauljoda.modularsystems.energy.block.GeneratorBlock;
import net.minecraft.block.Block;
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
public class BlockManager {

    /*******************************************************************************************************************
     * Block Instances                                                                                                 *
     *******************************************************************************************************************/

    @ObjectHolder("generator")
    public static Block generator;

    @ObjectHolder("energy_storage")
    public static Block energy_storage;

    /*******************************************************************************************************************
     * Registration                                                                                                    *
     *******************************************************************************************************************/

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new GeneratorBlock());
        event.getRegistry().register(new EnergyStorageBlock());
    }
}