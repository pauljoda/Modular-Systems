package com.pauljoda.modularsystems.core.math.function;

import com.pauljoda.modularsystems.core.registry.BlockValueRegistry;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Set;

public class BlockCountFunction {
    private HashMap<ResourceLocation, Integer> blockCount = new HashMap<>();
    private HashMap<ResourceLocation, Integer> tagCount = new HashMap<>();

    /**
     * Adds a block to the count.
     * If the block is registered, it increments the count for that block.
     * If the block is not registered, it checks against tags and increments the count for the matching tag.
     *
     * @param block The block to be added.
     */
    public void addBlock(BlockState block, Level level) {
        // First, check if there is a block registered
        if(BlockValueRegistry.INSTANCE.isBlockRegistered(block, level)) {
            // Check if already here, if not set to zero so when we add one its initialized
            var currentCount = blockCount.getOrDefault(BuiltInRegistries.BLOCK.getKey(block.getBlock()), 0);

            // Increment
            currentCount += 1;

            // Add to map
            blockCount.put(BuiltInRegistries.BLOCK.getKey(block.getBlock()), currentCount);
        }
    }

    /**
     * Returns the count of a specific block.
     *
     * @param block The block to get the count for.
     * @return The count of the specified block. If the block is not found in the blockCount map,
     *         it returns 0.
     */
    public int getBlockCount(BlockState block) {
        return blockCount.getOrDefault(block.getBlock().getDescriptionId(), 0);
    }

    /**
     * Returns the count of a specific block.
     *
     * @param block The block name to get the count for.
     * @return The count of the specified block. If the block is not found in the blockCount map, it returns 0.
     */
    public int getBlockCount(ResourceLocation block) {
        return blockCount.getOrDefault(block, 0);
    }

    /**
     * Returns the count of a specific tag from the tagCount map.
     *
     * @param tag The tag to get the count for.
     * @return The count of the specified tag. If the tag is not found in the tagCount map,
     *         it returns 0.
     */
    public int getTagCount(ResourceLocation tag) {
        return tagCount.getOrDefault(tag, 0);
    }

    /**
     * Retrieves the set of unique block names from the blockCount map.
     *
     * @return A set of strings representing the unique block names. If the blockCount map is empty,
     *         returns an empty set.
     */
    public Set<ResourceLocation> getBlockSet() {
        return blockCount.keySet();
    }

    /**
     * Retrieves the set of unique tags from the tagCount map.
     *
     * @return A set of strings representing the unique tags. If the tagCount map is empty,
     *         returns an empty set.
     */
    public Set<ResourceLocation> getTagSet() {
        return tagCount.keySet();
    }
}
