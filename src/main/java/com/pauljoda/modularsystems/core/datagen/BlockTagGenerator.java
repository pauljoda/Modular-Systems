package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends BlockTagsProvider {

    public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.CUBOID_PROXY_BLOCK.get())
                .add(Registration.FURNACE_CORE_BLOCK.get())
                .add(Registration.STONE_WORK_CORE_BLOCK.get())
                .add(Registration.CUBOID_BANK_SOLIDS_BLOCK.get())
                .add(Registration.CUBOID_IO_BLOCK.get());

        tag(BlockTags.create(new ResourceLocation("forge:relocation_not_supported")))
                .add(Registration.CUBOID_PROXY_BLOCK.get())
                .add(Registration.FURNACE_CORE_BLOCK.get())
                .add(Registration.STONE_WORK_CORE_BLOCK.get())
                .add(Registration.CUBOID_BANK_SOLIDS_BLOCK.get())
                .add(Registration.CUBOID_IO_BLOCK.get());
    }

    @Override
    public @NotNull String getName() {
        return "Modular Systems Block Tags";
    }
}