package com.pauljoda.modularsystems.registry;

import com.google.gson.reflect.TypeToken;
import com.pauljoda.modularsystems.ModularSystems;
import com.pauljoda.modularsystems.data.models.BlockValues;
import com.pauljoda.modularsystems.lib.Reference;
import com.pauljoda.modularsystems.math.Calculation;
import com.pauljoda.nucleus.util.JsonUtils;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BlockValueRegistry {
    public static BlockValueRegistry INSTANCE = new BlockValueRegistry();

    private String getBlockSaveLocation() {
        return String.format("%sRegistries%sblockValues.json", Reference.CONFIG_LOCATION, File.separator);
    }

    private String getTagSaveLocation() {
        return String.format("%sRegistries%stagValues.json", Reference.CONFIG_LOCATION, File.separator);
    }

    private HashMap<String, BlockValues> blockValues;
    private HashMap<String, BlockValues> tagValues;

    public BlockValueRegistry() {
        blockValues = new LinkedHashMap<>();
        tagValues = new LinkedHashMap<>();
    }

    public void init() {
        if (!loadFromFile())
            generateDefaults();
        else
            ModularSystems.LOGGER.info("Block values loaded");
    }

    private boolean loadFromFile() {
        ModularSystems.LOGGER.info("Loading blocks values from file...");
        blockValues = JsonUtils.<LinkedHashMap<String, BlockValues>>readFromJson(new TypeToken<LinkedHashMap<String, BlockValues>>(){},
                getBlockSaveLocation());

        tagValues = JsonUtils.<LinkedHashMap<String, BlockValues>>readFromJson(new TypeToken<LinkedHashMap<String, BlockValues>>(){},
                getTagSaveLocation());

        return blockValues != null && tagValues != null;
    }

    private void saveToFiles() {
        // Check for Directory
        checkAndCreatePath(getBlockSaveLocation());

        if (!blockValues.isEmpty())
            JsonUtils.writeToJson(blockValues, getBlockSaveLocation());

        if(!tagValues.isEmpty())
            JsonUtils.writeToJson(tagValues, getTagSaveLocation());
    }

    private void generateDefaults() {
        blockValues = new LinkedHashMap<>();
        tagValues = new LinkedHashMap<>();

        /**
         * Blocks
         */

        // Redstone
        blockValues.put(Blocks.REDSTONE_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(-1, 50, 0, 3, 0, -50, 1),
                        new Calculation(-1, 10, 0, 3, 0, -500, 1),
                        Calculation.FLAT));

        // Quartz
        blockValues.put(Blocks.QUARTZ_BLOCK.getDescriptionId(),
                new BlockValues(
                        Calculation.FLAT,
                        new Calculation(-1, 5, 7, 3, 0, -500, 0),
                        new Calculation(1, 1, 0, 1, 0, 0, 3)));

        // Gold Block
        blockValues.put(Blocks.GOLD_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(2, 1, 0, 1, 0, 0, 50),
                        new Calculation(2, 1, 0, 2, 0, 0, 500),
                        new Calculation(1, 4, 0, 1, 0, 0, 3)));

        // Diamond Block
        blockValues.put(Blocks.DIAMOND_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(-8, 1, 0, 1, 0, -100, 0),
                        new Calculation(15, 1, 0, 1, 0, 0, 300),
                        new Calculation(1, 1, 0, 1, 0, 0, 3)));

        // Lapis Block
        blockValues.put(Blocks.LAPIS_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(-1, 2, 0, 1, 0, -25, 0),
                        new Calculation(2, 1, 0, 1, 0, 0, 100),
                        new Calculation(1, 14, 0, 1, 0, 0, 1)));

        // Brick Block
        blockValues.put(Blocks.BRICKS.getDescriptionId(),
                new BlockValues(
                        new Calculation(10, 1, 0, 1, 0, 0, 100),
                        new Calculation(-10, 1, 0, 2, 0, -500, 0),
                        new Calculation(1, 2, 0, 1, 0, 0, 5)));

        // Iron Block
        blockValues.put(Blocks.IRON_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(1, 75, 0, 3, 0, 0, 100),
                        new Calculation(1, 15, 0, 3, 0, 0, 500),
                        Calculation.FLAT));

        // Emerald Block
        blockValues.put(Blocks.EMERALD_BLOCK.getDescriptionId(),
                new BlockValues(
                        Calculation.FLAT,
                        Calculation.FLAT,
                        new Calculation(1, 5, 0, 1, 0, 0, 10)));

        // Stone Brick
        blockValues.put(Blocks.STONE_BRICKS.getDescriptionId(),
                new BlockValues(
                        new Calculation(-1, 10, 0, 1, 0, -20, 0),
                        new Calculation(1, 5, 0, 1, 0, 0, 50),
                        Calculation.FLAT));

        // Coal Block
        blockValues.put(Blocks.COAL_BLOCK.getDescriptionId(),
                new BlockValues(
                        Calculation.FLAT,
                        new Calculation(8, 1, 0, 1, 0, 0, 300),
                        Calculation.FLAT));

        // Nether Bricks
        blockValues.put(Blocks.NETHER_BRICKS.getDescriptionId(),
                new BlockValues(
                        new Calculation(-1, 1, 0, 1, 0, -30, 0),
                        new Calculation(-1, 1, 0, 2, 0, -50, 0),
                        Calculation.FLAT));

        /**
         * Tags
         */

        // Cobble Stone
        tagValues.put(Tags.Blocks.COBBLESTONE.location().toString(),
                new BlockValues(
                        new Calculation(-1, 50, 0, 3, 0, -50, 1),
                        new Calculation(-1, 10, 0, 3, 0, -500, 1),
                        Calculation.FLAT));

        // Stone
        tagValues.put(Tags.Blocks.STONE.location().toString(),
                new BlockValues(
                        new Calculation(-1, 100, 0, 2, 0, -40, 0),
                        Calculation.FLAT,
                        Calculation.FLAT));

        // Sandstone
        tagValues.put(Tags.Blocks.SANDSTONE.location().toString(),
                new BlockValues(
                        new Calculation(-2, 50, 0, 2, 25, -100, 40),
                        new Calculation(1, 1, 0, 1, 0, 0, 200),
                        Calculation.FLAT));

        // Netherrack
        tagValues.put(Tags.Blocks.NETHERRACK.location().toString(),
                new BlockValues(
                        new Calculation(-2, 2, 0, 2, 0, -100, 0),
                        new Calculation(-1, 1, 0, 3, 0, -700, 0),
                        Calculation.FLAT));

        // Obsidian
        tagValues.put(Tags.Blocks.OBSIDIAN.location().toString(),
                new BlockValues(
                        new Calculation(1, 50, 0, 2, 0, 0, 50),
                        new Calculation(1, 50, 0, 3, 0, 0, 250),
                        Calculation.FLAT));

        // End Stone
        // Obsidian
        tagValues.put(Tags.Blocks.END_STONES.location().toString(),
                new BlockValues(
                        new Calculation(-1, 40, 0, 2, 0, -75, 0),
                        new Calculation(-1, 16, -55, 2, 50, -250, 200),
                        new Calculation(1, 19, 0, 1, 0, 0, 5)));

        saveToFiles();
    }

    private void checkAndCreatePath(String pathString) {
        Path path = Paths.get(pathString);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
