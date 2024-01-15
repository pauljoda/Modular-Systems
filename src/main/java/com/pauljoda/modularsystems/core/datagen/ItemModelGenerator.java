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
        withExistingParent(BuiltInRegistries.BLOCK.getKey(Registration.FURNACE_CORE_BLOCK.get()).getPath(),
                modLoc(String.format("block/%s", "furnace_core_off")));

        // Providers
        // Solid
        fromBlock(Registration.CUBOID_BANK_SOLIDS_BLOCK.get());
    }

    public void fromBlock(Block block) {
        withExistingParent(BuiltInRegistries.BLOCK.getKey(block).getPath(),
                modLoc(String.format("block/%s", BuiltInRegistries.BLOCK.getKey(block).getPath())));
    }
}