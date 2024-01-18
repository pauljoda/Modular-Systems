package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class ItemModelGenerator extends ItemModelProvider {

    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Reference.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerModels() {
        // Items -------------------------------------------------------------------------------------------------------


        // Blocks ------------------------------------------------------------------------------------------------------

        // Furnace Core
        fromBlock(Registration.FURNACE_CORE_BLOCK.get());

        // Stone Work
        fromBlock(Registration.STONE_WORK_CORE_BLOCK.get());

        // Providers
        // Solid
        fromBlock(Registration.CUBOID_BANK_SOLIDS_BLOCK.get());

        // IO
        fromBlock(Registration.CUBOID_IO_BLOCK.get());
    }

    public void fromBlock(Block block) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                modLoc(String.format("block/%s", BuiltInRegistries.BLOCK.getKey(block).getPath())));
    }
}