package com.pauljoda.modularsystems.core.manager;

import com.pauljoda.modularsystems.core.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
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
public class ItemManager {

    // Create tabs
    // Main Tab
    public static ItemGroup itemGroupModularSystems;

    /*******************************************************************************************************************
     * Items                                                                                                           *
     *******************************************************************************************************************/


    /*******************************************************************************************************************
     * BlockItems                                                                                                      *
     *******************************************************************************************************************/

    @ObjectHolder("generator")
    public static Item generator;

    /*******************************************************************************************************************
     * Register                                                                                                        *
     *******************************************************************************************************************/

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        // Setup ItemGroup
        itemGroupModularSystems = new ItemGroup(Reference.MOD_ID) {
            @Override
            public ItemStack createIcon() {
                return new ItemStack(BlockManager.generator);
            }
        };

        // Register Items

        // Register BlockItems
        registerBlockItemForBlock(event.getRegistry(), BlockManager.generator);
    }

    @SuppressWarnings("ConstantConditions")
    public static void registerBlockItemForBlock(IForgeRegistry<Item> registry, Block block) {
        Item itemBlock = new BlockItem(block, new Item.Properties().group(itemGroupModularSystems));
        itemBlock.setRegistryName(block.getRegistryName());
        registry.register(itemBlock);
    }
}