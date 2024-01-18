package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.nucleus.data.BaseLootTableGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * This file was created for Nucleus
 * <p>
 * Nucleus is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 6/7/2022
 */
public class LootTableGenerator extends BaseLootTableGenerator {

    @Override
    protected void generate() {
        // Furnace Core
        createSimpleTable(Registration.FURNACE_CORE_BLOCK.get());

        // Stone WOrk
        createSimpleTable(Registration.STONE_WORK_CORE_BLOCK.get());

        // Providers
        // Solids
        createSimpleTable(Registration.CUBOID_BANK_SOLIDS_BLOCK.get());

        // IO
        createSimpleTable(Registration.CUBOID_IO_BLOCK.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream()
                .filter((e) -> e.get() != Registration.CUBOID_PROXY_BLOCK.get())
                .map(DeferredHolder::get)
                .collect(Collectors.toList());
    }
}