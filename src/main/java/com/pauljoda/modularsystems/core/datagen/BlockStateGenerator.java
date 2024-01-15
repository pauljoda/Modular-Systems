package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {

    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Reference.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Proxy
        float offset = 0.005F;
        var proxy = models().getBuilder("block/cuboid_proxy")
                .parent(models().getExistingFile(mcLoc("block")))
                .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                .allFaces(((direction, faceBuilder) -> {
                    faceBuilder.texture("#hopper");
                }))
                .end()
                .texture("hopper", mcLoc("block/hopper_top"))
                .texture("all", mcLoc("block/hopper_top"))
                .texture("particle", mcLoc("block/hopper_top")).renderType("cutout");
        getVariantBuilder(Registration.CUBOID_PROXY_BLOCK.get()).partialState().setModels(new ConfiguredModel(proxy));
    }
}
