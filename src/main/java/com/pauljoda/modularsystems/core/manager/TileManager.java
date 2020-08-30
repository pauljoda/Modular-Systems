package com.pauljoda.modularsystems.core.manager;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.generator.tile.GeneratorTile;
import net.minecraft.tileentity.TileEntityType;
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
public class TileManager {

    /*******************************************************************************************************************
     * Tile Instances                                                                                                  *
     *******************************************************************************************************************/

    @ObjectHolder("generator")
    public static TileEntityType<GeneratorTile> generator;

    /*******************************************************************************************************************
     * Registration                                                                                                    *
     *******************************************************************************************************************/

    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry()
                .register(TileEntityType.Builder.create(GeneratorTile::new, BlockManager.generator)
                        .build(null).setRegistryName("generator"));
    }
}
