package com.pauljoda.modularsystems.core.data;

import net.minecraft.nbt.CompoundTag;

/**
 * The Savable interface represents an object that is capable of saving and loading data using CompoundTag.
 */
public interface Savable {

    /**
     * Loads the data from the given CompoundTag.
     *
     * @param tag The CompoundTag containing the data to be loaded.
     */
    void load(CompoundTag tag);

    /**
     * Saves the data of the object into the specified CompoundTag.
     *
     * @param tag The CompoundTag to store the data into.
     * @return The updated CompoundTag with the saved data.
     */
    CompoundTag save(CompoundTag tag);
}
