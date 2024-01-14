package com.pauljoda.modularsystems.core.registry;

import com.google.gson.reflect.TypeToken;
import com.pauljoda.modularsystems.ModularSystems;
import com.pauljoda.modularsystems.core.collections.BlockValues;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.math.collections.Calculation;
import com.pauljoda.nucleus.util.JsonUtils;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class BlockValueRegistry {

    // Public Instance
    public static BlockValueRegistry INSTANCE = new BlockValueRegistry();

    /*******************************************************************************************************************
     * Save Locations                                                                                                  *
     *******************************************************************************************************************/
    // Blocks
    private String getBlockSaveLocation() {
        return String.format("%sRegistries%sblockValues.json", Reference.CONFIG_LOCATION, File.separator);
    }

    // Tags
    private String getTagSaveLocation() {
        return String.format("%sRegistries%stagValues.json", Reference.CONFIG_LOCATION, File.separator);
    }

    /*******************************************************************************************************************
     * Maps                                                                                                            *
     *******************************************************************************************************************/
    // Blocks
    private HashMap<String, BlockValues> blockValues;
    // Tags
    private HashMap<String, BlockValues> tagValues;

    /**
     * The BlockValueRegistry class is responsible for initializing and managing
     * the block and tag values for the module.
     */

    public BlockValueRegistry() {
        blockValues = new LinkedHashMap<>();
        tagValues = new LinkedHashMap<>();
    }

    /*******************************************************************************************************************
     * Registry Functions                                                                                              *
     *******************************************************************************************************************/

    /**
     * Checks if the given block is registered.
     *
     * @param block The block state to check.
     * @return True if the block is registered, false otherwise.
     */

    public boolean isBlockRegistered(BlockState block) {
        return blockValues.containsKey(block.getBlock().getDescriptionId());
    }

    /**
     * Checks if the given block has a registered block tag.
     *
     * @param block The block state to check.
     * @return True if the block has a registered block tag, false otherwise.
     */
    public boolean hasBlockTagRegistered(BlockState block) {
        for(TagKey<Block> tag : block.getTags().toList()) {
            // Found the tag
            if(tagValues.containsKey(tag.toString()))
                return true;
        }

        // Didn't find anything
        return false;
    }

    /**
     * Retrieves the tag that is registered for the given block.
     *
     * This will return the first that matches, any block that matches multiple tags should either
     * add the block as a value, or register earlier
     *
     * @param block The block state to check.
     * @return The tag that is registered for the block, or null if no tag is registered.
     */
    public @Nullable  String getTagRegistered(BlockState block) {
        for(TagKey<Block> tag : block.getTags().toList()) {
            // Found the tag
            if(tagValues.containsKey(tag.toString()))
                return tag.toString();
        }
        // There is nothing registered
        return null;
    }

    /*******************************************************************************************************************
     * Loading                                                                                                         *
     *******************************************************************************************************************/

    /**
     * Initializes the block value registry.
     *
     * This method loads the block and tag values from JSON files. If the files do not exist or there is an error reading the files,
     * default values are generated. The block values are stored in the private field blockValues and the tag values are stored in
     * the private field tagValues.
     */

    public void init() {
        if (!loadFromFile())
            generateDefaults();
        else
            ModularSystems.LOGGER.info("Block values loaded");
    }

    /**
     * Loads the block and tag values from JSON files.
     *
     * This method reads the block values and tag values from separate JSON files using the JsonUtils class. The block values
     * are stored in the private field blockValues and the tag values are stored in the private field tagValues. If the files
     * do not exist or there is an error reading the files, the blockValues and tagValues fields will remain null.
     *
     * @return true if the block and tag values were loaded successfully, false otherwise
     */
    private boolean loadFromFile() {
        ModularSystems.LOGGER.info("Loading blocks values from file...");
        blockValues = JsonUtils.<LinkedHashMap<String, BlockValues>>readFromJson(new TypeToken<LinkedHashMap<String, BlockValues>>(){},
                getBlockSaveLocation());

        tagValues = JsonUtils.<LinkedHashMap<String, BlockValues>>readFromJson(new TypeToken<LinkedHashMap<String, BlockValues>>(){},
                getTagSaveLocation());

        return blockValues != null && tagValues != null;
    }

    /**
     * Saves the block and tag values to separate JSON files.
     * If the save locations do not exist, the method creates the parent directories of the paths.
     */
    private void saveToFiles() {
        // Check for Directory
        checkAndCreatePath(getBlockSaveLocation());

        if (!blockValues.isEmpty())
            JsonUtils.writeToJson(blockValues, getBlockSaveLocation());

        if(!tagValues.isEmpty())
            JsonUtils.writeToJson(tagValues, getTagSaveLocation());
    }

    /**
     * Generates the default block and tag values.
     *
     * This method initializes the 'blockValues' and 'tagValues' maps with default values for various blocks and tags.
     * It sets the block values for specific block types using the block's description ID. The tag values are set using the
     * tag's location.
     *
     * The default values for each block and tag are defined through 'Calculation' objects and are added to the respective
     * maps with the block or tag key.
     *
     * This method is private and is called by the 'init' method during the initialization of the block value registry.
     */
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

    /**
     * Checks if the given path exists. If it does not exist, creates the parent directories of the path.
     *
     * @param pathString The string representation of the path
     */
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
