package com.pauljoda.modularsystems.core.datagen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.AbstractCuboidCoreBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
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

        // Furnace core
        addCuboidCore(Registration.FURNACE_CORE_BLOCK.get(),
                mcLoc("block/furnace_top"),
                mcLoc("block/furnace_top"),
                mcLoc("block/furnace_front"),
                mcLoc("block/furnace_front_on"),
                mcLoc("block/furnace_side"),
                mcLoc("block/hopper_top"));

        // Solid Fuel Bank
        addCuboidBank(Registration.CUBOID_BANK_SOLIDS_BLOCK.get(), mcLoc("block/coal_block"));

        // IO
        var model = getModelWith3DRim(Registration.CUBOID_IO_BLOCK.get());
        model = getModelWithCore(model, mcLoc("block/dispenser_front_vertical"));
        model.renderType("cutout");
        getVariantBuilder(Registration.CUBOID_IO_BLOCK.get()).partialState().setModels(new ConfiguredModel(model));
    }

    /**
     * Adds a cuboid core block model to the block state provider.
     *
     * @param block            The block to add the cuboid core model to.
     * @param up               The resource location of the top texture.
     * @param down             The resource location of the bottom texture.
     * @param frontOff         The resource location of the front texture when the cuboid is off.
     * @param frontOn          The resource location of the front texture when the cuboid is on.
     * @param side             The resource location of the side texture.
     * @param border           The resource location of the border texture.
     */
    protected void addCuboidCore(Block block, ResourceLocation up, ResourceLocation down,
                                 ResourceLocation frontOff, ResourceLocation frontOn,
                                 ResourceLocation side,
                                 ResourceLocation border) {
        addCuboidCore(block, BuiltInRegistries.BLOCK.getKey(block).getPath(), BuiltInRegistries.BLOCK.getKey(block).getPath() + "_on",
                up, down, frontOff, frontOn, side, side, side, border);
    }

    /**
     * Adds a cuboid core block model to the block state provider.
     *
     * @param block            The block to add the cuboid core model to.
     * @param modelLocationOff The location of the cuboid core off model.
     * @param modelLocationOn  The location of the cuboid core on model.
     * @param up               The resource location of the top texture.
     * @param down             The resource location of the bottom texture.
     * @param frontOff         The resource location of the front texture when the cuboid is off.
     * @param frontOn          The resource location of the front texture when the cuboid is on.
     * @param south            The resource location of the south face texture.
     * @param east             The resource location of the east face texture.
     * @param west             The resource location of the west face texture.
     * @param border           The resource location of the border texture.
     */
    protected void addCuboidCore(Block block, String modelLocationOff, String modelLocationOn, ResourceLocation up, ResourceLocation down,
                                 ResourceLocation frontOff, ResourceLocation frontOn,
                                 ResourceLocation south, ResourceLocation east, ResourceLocation west,
                                 ResourceLocation border) {
        float offset = 0.015F;

        // Off model
        var cuboidCoreModelOff = models().getBuilder(modelLocationOff)
                .parent(models().getExistingFile(mcLoc("block/cube")))

                .element().from(0, 0, 0).to(16, 16, 16)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#up").cullface(Direction.UP);
                        case DOWN -> faceBuilder.texture("#down").cullface(Direction.DOWN);
                        case NORTH -> faceBuilder.texture("#north").cullface(Direction.NORTH);
                        case SOUTH -> faceBuilder.texture("#south").cullface(Direction.SOUTH);
                        case EAST -> faceBuilder.texture("#east").cullface(Direction.EAST);
                        case WEST -> faceBuilder.texture("#west").cullface(Direction.WEST);
                    }
                })
                .end()

                .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                .allFaces(((direction, faceBuilder) -> faceBuilder.texture("#border").cullface(direction)))
                .end()

                .texture("up", up)
                .texture("down", down)
                .texture("north", frontOff)
                .texture("east", east)
                .texture("south", south)
                .texture("west", west)
                .texture("border", border)
                .texture("particle", frontOff)
                .renderType("cutout");

        // On Model
        var cuboidCoreModelOn = models().getBuilder(modelLocationOn)
                .parent(models().getExistingFile(mcLoc("block/cube")))

                .element().from(0, 0, 0).to(16, 16, 16)
                .allFaces((direction, faceBuilder) -> {
                    switch (direction) {
                        case UP -> faceBuilder.texture("#up").cullface(Direction.UP);
                        case DOWN -> faceBuilder.texture("#down").cullface(Direction.DOWN);
                        case NORTH -> faceBuilder.texture("#north").cullface(Direction.NORTH);
                        case SOUTH -> faceBuilder.texture("#south").cullface(Direction.SOUTH);
                        case EAST -> faceBuilder.texture("#east").cullface(Direction.EAST);
                        case WEST -> faceBuilder.texture("#west").cullface(Direction.WEST);
                    }
                })
                .end()

                .element().from(-offset, -offset, -offset).to(16 + offset, 16 + offset, 16 + offset)
                .allFaces(((direction, faceBuilder) -> faceBuilder.texture("#border").cullface(direction)))
                .end()

                .texture("up", up)
                .texture("down", down)
                .texture("north", frontOn)
                .texture("east", east)
                .texture("south", south)
                .texture("west", west)
                .texture("border", border)
                .texture("particle", frontOn)
                .renderType("cutout");

        getVariantBuilder(block)
                .forAllStates(state ->
                        ConfiguredModel.builder()
                                .modelFile(state.getValue(AbstractCuboidCoreBlock.LIT) ? cuboidCoreModelOn : cuboidCoreModelOff)
                                .rotationY((int) state.getValue(AbstractCuboidCoreBlock.FACING).getOpposite().toYRot())
                                .build());
    }

    /**
     * Adds a cuboid bank block model to the block state provider.
     *
     * @param block       The block to add the cuboid bank model to.
     * @param coreTexture The resource location of the core texture.
     */
    protected void addCuboidBank(Block block, ResourceLocation coreTexture) {
        var model = getModelWith3DRim(block);
        model = getModelWithIndicators(model);
        model = getModelWithCore(model, coreTexture);
        model.renderType("cutout");
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(model));
    }

    /**
     * Constructs a model with 3D rims for the given block.
     *
     * @param block the block to create the model for
     * @return a ModelBuilder object representing the constructed model
     */
    protected ModelBuilder<?> getModelWith3DRim(Block block) {
        return models().getBuilder(BuiltInRegistries.BLOCK.getKey(block).getPath())
                .parent(models().getExistingFile(mcLoc("block/cube")))
                .texture("particle", mcLoc("block/hopper_top"))
                .texture("hopper", mcLoc("block/hopper_top"))

                // Top Rim 1
                .element().from(0, 14, 0).to(16, 16, 2)
                .allFaces(((direction, faceBuilder) -> {
                    switch (direction) {
                        case NORTH -> faceBuilder.texture("#hopper").uvs(0, 0, 16, 2);
                        case EAST -> faceBuilder.texture("#hopper").uvs(14, 0, 16, 2);
                        case SOUTH -> faceBuilder.texture("#hopper").uvs(0, 0, 16, 2);
                        case WEST -> faceBuilder.texture("#hopper").uvs(0, 0, 2, 2);
                        case UP -> faceBuilder.texture("#hopper").uvs(0, 16, 16, 14);
                        case DOWN -> faceBuilder.texture("#hopper").uvs(0, 14, 16, 16);
                    }
                }))
                .end()

                // Top Rim 2
                .element().from(0, 14, 2).to(2, 16, 14)
                .face(Direction.EAST).texture("#hopper").uvs(2, 0, 14, 0).end()
                .face(Direction.WEST).texture("#hopper").uvs(2, 0, 14, 2).end()
                .face(Direction.UP).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.DOWN).texture("#hopper").uvs(0, 2, 2, 14).end()
                .end()

                // Top Rim 3
                .element().from(14, 14, 2).to(16, 16, 14)
                .face(Direction.EAST).texture("#hopper").uvs(2, 0, 14, 2).end()
                .face(Direction.WEST).texture("#hopper").uvs(2, 0, 14, 2).end()
                .face(Direction.UP).texture("#hopper").uvs(14, 2, 16, 14).end()
                .face(Direction.DOWN).texture("#hopper").uvs(0, 2, 2, 14).end()
                .end()

                // Top Rim 4
                .element().from(0, 14, 14).to(16, 16, 16)
                .allFaces(((direction, faceBuilder) -> {
                    switch (direction) {
                        case NORTH -> faceBuilder.texture("#hopper").uvs(0, 0, 16, 2);
                        case EAST -> faceBuilder.texture("#hopper").uvs(0, 0, 2, 2);
                        case SOUTH -> faceBuilder.texture("#hopper").uvs(0, 0, 16, 2);
                        case WEST -> faceBuilder.texture("#hopper").uvs(14, 0, 16, 2);
                        case UP -> faceBuilder.texture("#hopper").uvs(0, 2, 16, 0);
                        case DOWN -> faceBuilder.texture("#hopper").uvs(0, 14, 16, 16);
                    }
                }))
                .end()

                // LowerRim1 instantiation
                .element().from(0, 0, 0).to(16, 2, 2)
                .face(Direction.NORTH).texture("#hopper").uvs(0, 14, 16, 16).end()
                .face(Direction.EAST).texture("#hopper").uvs(14, 14, 16, 16).end()
                .face(Direction.SOUTH).texture("#hopper").uvs(0, 14, 16, 16).end()
                .face(Direction.WEST).texture("#hopper").uvs(0, 14, 2, 16).end()
                .face(Direction.UP).texture("#hopper").uvs(0, 2, 16, 0).end()
                .face(Direction.DOWN).texture("#hopper").uvs(0, 14, 16, 16).end()
                .end()

                // LowerRim2 instantiation
                .element().from(0, 0, 14).to(16, 2, 16)
                .face(Direction.NORTH).texture("#hopper").uvs(0, 14, 16, 16).end()
                .face(Direction.EAST).texture("#hopper").uvs(0, 14, 2, 16).end()
                .face(Direction.SOUTH).texture("#hopper").uvs(0, 14, 16, 16).end()
                .face(Direction.WEST).texture("#hopper").uvs(14, 14, 16, 16).end()
                .face(Direction.UP).texture("#hopper").uvs(0, 2, 16, 0).end()
                .face(Direction.DOWN).texture("#hopper").uvs(0, 0, 16, 2).end()
                .end()

                // LowerRim3 instantiation
                .element().from(0, 0, 2).to(2, 2, 14)
                .face(Direction.EAST).texture("#hopper").uvs(2, 14, 14, 16).end()
                .face(Direction.WEST).texture("#hopper").uvs(2, 14, 14, 16).end()
                .face(Direction.UP).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.DOWN).texture("#hopper").uvs(0, 2, 2, 14).end()
                .end()

                // LowerRim4 instantiation
                .element().from(14, 0, 2).to(16, 2, 14)
                .face(Direction.EAST).texture("#hopper").uvs(2, 14, 14, 16).end()
                .face(Direction.WEST).texture("#hopper").uvs(2, 14, 14, 16).end()
                .face(Direction.UP).texture("#hopper").uvs(14, 2, 16, 14).end()
                .face(Direction.DOWN).texture("#hopper").uvs(14, 2, 16, 14).end()
                .end()

                // Edge1
                .element().from(0, 2, 0).to(2, 14, 2)
                .face(Direction.NORTH).texture("#hopper").uvs(14, 2, 16, 14).end()
                .face(Direction.EAST).texture("#hopper").uvs(14, 2, 16, 14).end()
                .face(Direction.SOUTH).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.WEST).texture("#hopper").uvs(0, 2, 2, 14).end()
                .end()

                // Edge2
                .element().from(14, 2, 0).to(16, 14, 2)
                .face(Direction.NORTH).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.EAST).texture("#hopper").uvs(14, 2, 16, 14).end()
                .face(Direction.SOUTH).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.WEST).texture("#hopper").uvs(0, 2, 2, 14).end()
                .end()

                // Edge3
                .element().from(14, 2, 14).to(16, 14, 16)
                .face(Direction.NORTH).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.EAST).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.SOUTH).texture("#hopper").uvs(14, 2, 16, 14).end()
                .face(Direction.WEST).texture("#hopper").uvs(14, 2, 16, 14).end()
                .end()

                // Edge4
                .element().from(0, 2, 14).to(2, 14, 16)
                .face(Direction.NORTH).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.EAST).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.SOUTH).texture("#hopper").uvs(0, 2, 2, 14).end()
                .face(Direction.WEST).texture("#hopper").uvs(14, 2, 16, 14).end()
                .end();
    }

    /**
     * Returns a ModelBuilder object with indicators added.
     *
     * @param builder the ModelBuilder object to add indicators to
     * @return a ModelBuilder object with indicators added
     */
    protected ModelBuilder<?> getModelWithIndicators(ModelBuilder<?> builder) {
        return builder
                .texture("stone", mcLoc("block/stone"))
                .texture("clay", mcLoc("block/black_concrete"))

                // IndicatorNorthTop instantiation
                .element().from(6, 12, 1).to(10, 13, 2)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 4, 1).end()
                .end()

                // IndicatorNorthBottom instantiation
                .element().from(6, 3, 1).to(10, 4, 2)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 4, 1).end()
                .end()

                // IndicatorNorthL instantiation
                .element().from(6, 4, 1).to(7, 12, 2)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorNorthR instantiation
                .element().from(9, 4, 1).to(10, 12, 2)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorNorthBack instantiation
                .element().from(7, 4, 1.8F).to(9, 12, 1.8F)
                .face(Direction.NORTH).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.EAST).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.SOUTH).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.WEST).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.UP).texture("#clay").uvs(0, 0, 2, 0).end()
                .face(Direction.DOWN).texture("#clay").uvs(0, 0, 2, 0).end()
                .end()

                // IndicatorWestTop instantiation
                .element().from(1, 12, 6).to(2, 13, 10)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 4).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 4).end()
                .end()

                // IndicatorWestBottom instantiation
                .element().from(1, 3, 6).to(2, 4, 10)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 4).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 4).end()
                .end()

                // IndicatorWestL instantiation
                .element().from(1, 4, 6).to(2, 12, 7)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorWestR instantiation
                .element().from(1, 4, 9).to(2, 12, 10)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorWestBack instantiation
                .element().from(1.8F, 4, 7).to(1.8F, 12, 9)
                .face(Direction.NORTH).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.EAST).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.SOUTH).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.WEST).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.UP).texture("#clay").uvs(0, 0, 0, 2).end()
                .face(Direction.DOWN).texture("#clay").uvs(0, 0, 0, 2).end()
                .end()

                // IndicatorSouthTop instantiation
                .element().from(6, 12, 14).to(10, 13, 15)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 4, 1).end()
                .end()

                // IndicatorSouthBottom instantiation
                .element().from(6, 3, 14).to(10, 4, 15)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 4, 1).end()
                .end()

                // IndicatorSouthL instantiation
                .element().from(6, 4, 14).to(7, 12, 15)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorSouthR instantiation
                .element().from(9, 4, 14).to(10, 12, 15)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorSouthBack instantiation
                .element().from(7, 4, 14.2F).to(9, 12, 14.2F)
                .face(Direction.NORTH).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.EAST).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.SOUTH).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.WEST).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.UP).texture("#clay").uvs(0, 0, 2, 0).end()
                .face(Direction.DOWN).texture("#clay").uvs(0, 0, 2, 0).end()
                .end()

                // IndicatorEastTop instantiation
                .element().from(14, 12, 6).to(15, 13, 10)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 4).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 4).end()
                .end()

                // IndicatorEastBottom instantiation
                .element().from(14, 3, 6).to(15, 4, 10)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 4, 1).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 4).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 4).end()
                .end()

                // IndicatorEastL instantiation
                .element().from(14, 4, 9).to(15, 12, 10)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorWestR instantiation
                .element().from(14, 4, 6).to(15, 12, 7)
                .face(Direction.NORTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.EAST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.SOUTH).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.WEST).texture("#stone").uvs(0, 0, 1, 8).end()
                .face(Direction.UP).texture("#stone").uvs(0, 0, 1, 1).end()
                .face(Direction.DOWN).texture("#stone").uvs(0, 0, 1, 1).end()
                .end()

                // IndicatorEastBack instantiation
                .element().from(14.2F, 4, 7).to(14.2F, 12, 9)
                .face(Direction.NORTH).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.EAST).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.SOUTH).texture("#clay").uvs(0, 0, 0, 8).end()
                .face(Direction.WEST).texture("#clay").uvs(0, 0, 2, 8).end()
                .face(Direction.UP).texture("#clay").uvs(0, 0, 0, 2).end()
                .face(Direction.DOWN).texture("#clay").uvs(0, 0, 0, 2).end()
                .end();
    }

    /**
     * Returns a ModelBuilder object with a core added.
     *
     * @param builder The ModelBuilder object to add the core to.
     * @param core    The resource location of the core texture.
     * @return A ModelBuilder object with the core added.
     */
    protected ModelBuilder<?> getModelWithCore(ModelBuilder<?> builder, ResourceLocation core) {
        return builder
                .texture("core", core)
                .// Core instantiation
                        element().from(2, 2, 2).to(14, 14, 14)
                .face(Direction.NORTH).texture("#core").uvs(2, 2, 14, 14).end()
                .face(Direction.EAST).texture("#core").uvs(2, 2, 14, 14).end()
                .face(Direction.SOUTH).texture("#core").uvs(2, 2, 14, 14).end()
                .face(Direction.WEST).texture("#core").uvs(2, 2, 14, 14).end()
                .face(Direction.UP).texture("#core").uvs(2, 2, 14, 14).end()
                .face(Direction.DOWN).texture("#core").uvs(2, 2, 14, 14).end()
                .end();
    }
}
