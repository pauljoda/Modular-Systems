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
     * Checks if the given block is registered.
     *
     * @param block The block state to check.
     * @return True if the block is registered, false otherwise.
     */

    public boolean isBlockRegistered(String block) {
        return blockValues.containsKey(block);
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
            if(tagValues.containsKey(tag.location().toString()))
                return true;
        }

        // Didn't find anything
        return false;
    }

    /**
     * Checks if the given block has a registered block tag.
     *
     * @param tag The block state to check.
     * @return True if the block has a registered block tag, false otherwise.
     */
    public boolean hasBlockTagRegistered(String tag) {
        return tagValues.containsKey(tag);
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

    /**
     * Retrieves the speed value for a given block and count.
     *
     * @param block The block to get the value for.
     * @param count The count of blocks.
     * @return The speed value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getBlockSpeedValue(String block, int count) {
        if(isBlockRegistered(block)) {
            var values = blockValues.get(block);
            return values.getSpeedFunction().F(count);
        }
        return 0.0;
    }

    /**
     * Retrieves the efficiency value for a given block and count.
     *
     * The efficiency value is calculated based on the registered block values.
     *
     * @param block The block to get the value for.
     * @param count The count of blocks.
     * @return The efficiency value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getBlockEfficiencyValue(String block, int count) {
        if(isBlockRegistered(block)) {
            var values = blockValues.get(block);
            return values.getEfficiencyFunction().F(count);
        }
        return 0.0;
    }

    /**
     * Retrieves the multiplicity value for a given block and count.
     *
     * @param block The block to get the value for.
     * @param count The count of blocks.
     * @return The multiplicity value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getBlockMultiplicityValue(String block, int count) {
        if(isBlockRegistered(block)) {
            var values = blockValues.get(block);
            return values.getMultiplicityFunction().F(count);
        }
        return 0.0;
    }

    /**
     * Retrieves the speed value for a given tag and count.
     *
     * @param tag The tag to retrieve the speed value for.
     * @param count The count of blocks.
     * @return The speed value for the tag and count. Returns 0.0 if the tag is not registered.
     */
    public double getTagSpeedValue(String tag, int count) {
        if(hasBlockTagRegistered(tag)) {
            var values = tagValues.get(tag);
            return values.getSpeedFunction().F(count);
        }
        return 0.0D;
    }

    /**
     * Calculates the efficiency value for a given tag and count.
     *
     * @param tag   The tag to get the value for.
     * @param count The count of blocks.
     * @return The efficiency value for the tag and count. Returns 0.0 if the tag is not registered.
     */
    public double getTagEfficiencyValue(String tag, int count) {
        if(hasBlockTagRegistered(tag)) {
            var values = tagValues.get(tag);
            return values.getEfficiencyFunction().F(count);
        }
        return 0.0D;
    }
    /**
     * Retrieves the multiplicity value for a given tag and count.
     *
     * @param tag The tag to get the value for.
     * @param count The count of blocks.
     * @return The multiplicity value for the tag and count. Returns 0.0 if the tag is not registered.
     */
    public double getTagMultiplicityValue(String tag, int count) {
        if(hasBlockTagRegistered(tag)) {
            var values = tagValues.get(tag);
            return values.getMultiplicityFunction().F(count);
        }
        return 0.0D;
    }

    /**
     * Retrieves the speed value for the given block state and count.
     *
     * This method checks if the block state is registered. If it is, it retrieves
     * the speed function for the block and calculates the speed value based on the count.
     * If the block state is not registered, it checks if there is a registered tag for
     * the block. If there is, it retrieves the speed function for the tag and calculates
     * the speed value based on the count.
     *
     * @param state The block state to get the value for.
     * @param count The count of blocks.
     * @return The speed value for the block state and count. Returns 0.0 if the block state
     *         is not registered or if there is no registered tag for the block.
     */
    public double getSpeedValue(BlockState state, int count) {
        // By Block
        if(isBlockRegistered(state)) {
            return getBlockSpeedValue(state.getBlock().getDescriptionId(), count);
        } else {
            var tagRegistered = getTagRegistered(state);
            if(tagRegistered != null) {
                return getTagSpeedValue(tagRegistered, count);
            }
        }
        return 0.0D;
    }

    /**
     * Retrieves the efficiency value for a given block state and count.
     *
     * The efficiency value is calculated based on the registered block and tag values.
     *
     * @param state The block state to get the value for.
     * @param count The count of blocks.
     * @return The efficiency value for the block state and count. Returns 0.0 if the block state is not registered.
     */
    public double getEfficiencyValue(BlockState state, int count) {
        // By Block
        if(isBlockRegistered(state)) {
            return getBlockEfficiencyValue(state.getBlock().getDescriptionId(), count);
        } else {
            var tagRegistered = getTagRegistered(state);
            if(tagRegistered != null) {
                return getTagEfficiencyValue(tagRegistered, count);
            }
        }
        return 0.0D;
    }

    /**
     * Retrieves the multiplicity value for a given block and count.
     *
     * @param state The block state to get the value for.
     * @param count The count of blocks.
     * @return The multiplicity value for the block and count. Returns 0.0 if the block is not registered.
     */
    public double getMultiplicityValue(BlockState state, int count) {
        // By Block
        if(isBlockRegistered(state)) {
            return getBlockMultiplicityValue(state.getBlock().getDescriptionId(), count);
        } else {
            var tagRegistered = getTagRegistered(state);
            if(tagRegistered != null) {
                return getTagMultiplicityValue(tagRegistered, count);
            }
        }
        return 0.0D;
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
                        new Calculation(-2, -50, 1),
                        new Calculation(-50, -500, 1),
                        Calculation.FLAT));

        // Quartz
        blockValues.put(Blocks.QUARTZ_BLOCK.getDescriptionId(),
                new BlockValues(
                        Calculation.FLAT,
                        new Calculation(-15,-500, 0),
                        new Calculation(0.10, 0, 3)));

        // Gold Block
        blockValues.put(Blocks.GOLD_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(2, 0, 50),
                        new Calculation(20, 0, 500),
                        new Calculation(0.25, 0, 3)));

        // Diamond Block
        blockValues.put(Blocks.DIAMOND_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(-8, -100, 0),
                        new Calculation(15, 0, 300),
                        new Calculation(0.50,0, 3)));

        // Netherite
        blockValues.put(Blocks.NETHERITE_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(-10, 100, 0),
                        new Calculation(20, 0, 200),
                        new Calculation(1, 0, 5)));

        // Lapis Block
        blockValues.put(Blocks.LAPIS_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(-1, -25, 0),
                        new Calculation(2, 0, 100),
                        new Calculation(0.1,0, 2)));

        // Brick Block
        blockValues.put(Blocks.BRICKS.getDescriptionId(),
                new BlockValues(
                        new Calculation(5, 0, 100),
                        new Calculation(5, 0, 500),
                        Calculation.FLAT));

        // Iron Block
        blockValues.put(Blocks.IRON_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(10, 0, 100),
                        new Calculation(10, 0, 500),
                        Calculation.FLAT));

        blockValues.put(Blocks.COPPER_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(10, 0, 200),
                        new Calculation(5, 0, 1000),
                        Calculation.FLAT));

        // Emerald Block
        blockValues.put(Blocks.EMERALD_BLOCK.getDescriptionId(),
                new BlockValues(
                        Calculation.FLAT,
                        Calculation.FLAT,
                        new Calculation(1, 0, 5)));

        // Stone Brick
        blockValues.put(Blocks.STONE_BRICKS.getDescriptionId(),
                new BlockValues(
                        new Calculation(1, 0, 100),
                        new Calculation(10, 0, 500),
                        Calculation.FLAT));

        // Coal Block
        blockValues.put(Blocks.COAL_BLOCK.getDescriptionId(),
                new BlockValues(
                        new Calculation(1, 0, 100),
                        new Calculation(5, 0, 300),
                        Calculation.FLAT));

        // Nether Bricks
        blockValues.put(Blocks.NETHER_BRICKS.getDescriptionId(),
                new BlockValues(
                        new Calculation(-1, -30, 0),
                        new Calculation(-10, -200, 0),
                        Calculation.FLAT));

        /**
         * Tags
         */

        // Cobble Stone
        tagValues.put(Tags.Blocks.COBBLESTONE.location().toString(),
                new BlockValues(
                        new Calculation(-0.10, -50, 1),
                        new Calculation(-1, -500, 1),
                        Calculation.FLAT));

        // Stone
        tagValues.put(Tags.Blocks.STONE.location().toString(),
                new BlockValues(
                        new Calculation(-0.30, -40, 0),
                        Calculation.FLAT,
                        Calculation.FLAT));

        // Sandstone
        tagValues.put(Tags.Blocks.SANDSTONE.location().toString(),
                new BlockValues(
                        new Calculation(-0.10, -50, 1),
                        new Calculation(-1, -500, 1),
                        Calculation.FLAT));

        // Netherrack
        tagValues.put(Tags.Blocks.NETHERRACK.location().toString(),
                new BlockValues(
                        new Calculation(-2, -100, 0),
                        new Calculation(-20, -800, 0),
                        Calculation.FLAT));

        // Obsidian
        tagValues.put(Tags.Blocks.OBSIDIAN.location().toString(),
                new BlockValues(
                        new Calculation(10,  0, 50),
                        new Calculation(20, 0, 1600),
                        Calculation.FLAT));

        // End Stone
        // Obsidian
        tagValues.put(Tags.Blocks.END_STONES.location().toString(),
                new BlockValues(
                        new Calculation(-0.5, -75, 0),
                        new Calculation(-10, -250, -50),
                        new Calculation(0.10, 0, 5)));


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
