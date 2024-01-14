package com.pauljoda.modularsystems.core.multiblock;

import com.pauljoda.modularsystems.core.data.Savable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Tuple;

import javax.annotation.Nullable;

public class StandardCuboidValues implements Savable {
    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/
    // Work Times
    // Current remaining fuel time
    private int fuelTime = 0;
    private static final String FUEL_TIME = "FuelTime";

    // Current fuel provider provided fuel time (used to show progress of use)
    private int currentFuelProvidedTime = 0;
    private static final String CURRENT_FUEL_PROVIDER_TIME = "CurrentFuelProviderTime";

    // Current work progress
    private int workTime = 0;
    private static final String WORK_TIME = "WorkTime";

    // Modifiers
    private double speed = 0.0D;
    private static final String SPEED = "Speed";
    private double efficiency = 0.0D;
    private static final String EFFICIENCY = "Efficiency";
    private double multiplicity = 0.0D;
    private static final String MULTIPLICITY = "Multiplicity";

    // If currently working
    private boolean isWorking = false;
    private static final String IS_WORKING = "IsWorking";

    // If needs update
    private boolean isDirty = false;
    private static final String IS_DIRTY = "IsDirty";

    // Formed Status
    private boolean wellFormed = false;
    private static final String WELL_FORMED = "WellFormed";

    // Corners
    private @Nullable Tuple<BlockPos, BlockPos> corners = null;
    private static final String CORNER_ONE = "CornerOne";
    private static final String CORNER_TWO = "CornerTwo";

    /*******************************************************************************************************************
     * Methods                                                                                                         *
     *******************************************************************************************************************/

    /**
     * Resets the values of speed, efficiency, multiplicity, and fuel time to their default values.
     * After calling this method, the speed, efficiency, multiplicity, and fuel time will be set to 0.
     */

    public void resetStructureValues() {
        speed = 0.0D;
        efficiency = 0.0D;
        multiplicity = 0.0D;
        fuelTime = 0;
    }

    /*******************************************************************************************************************
     * Savable                                                                                                         *
     *******************************************************************************************************************/

    /**
     * Loads the data from the given CompoundTag.
     *
     * @param tag The CompoundTag containing the data to be loaded.
     */
    @Override
    public void load(CompoundTag tag) {
        // Work Time
        fuelTime = tag.getInt(FUEL_TIME);
        currentFuelProvidedTime = tag.getInt(CURRENT_FUEL_PROVIDER_TIME);
        workTime = tag.getInt(WORK_TIME);

        // Modifiers
        speed = tag.getDouble(SPEED);
        efficiency = tag.getDouble(EFFICIENCY);
        multiplicity = tag.getDouble(MULTIPLICITY);

        // Boolean States
        isWorking = tag.getBoolean(IS_WORKING);
        isDirty = tag.getBoolean(IS_DIRTY);
        wellFormed = tag.getBoolean(WELL_FORMED);

        // Corners
        if (tag.contains(CORNER_ONE) && tag.contains(CORNER_TWO)) {
            BlockPos cornerOne = NbtUtils.readBlockPos(tag.getCompound(CORNER_ONE));
            BlockPos cornerTwo = NbtUtils.readBlockPos(tag.getCompound(CORNER_TWO));
            corners = new Tuple<>(cornerOne, cornerTwo);
        }
    }

    /**
     * Saves the data of the object into the specified CompoundTag.
     *
     * @param tag The CompoundTag to store the data into.
     * @return The updated CompoundTag with the saved data.
     */
    @Override
    public CompoundTag save(CompoundTag tag) {
        // Work Time
        tag.putInt(FUEL_TIME, fuelTime);
        tag.putInt(CURRENT_FUEL_PROVIDER_TIME, currentFuelProvidedTime);
        tag.putInt(WORK_TIME, workTime);

        // Modifiers
        tag.putDouble(SPEED, speed);
        tag.putDouble(EFFICIENCY, efficiency);
        tag.putDouble(MULTIPLICITY, multiplicity);

        // Boolean States
        tag.putBoolean(IS_WORKING, isWorking);
        tag.putBoolean(IS_DIRTY, isDirty);
        tag.putBoolean(WELL_FORMED, wellFormed);

        // Corners
        if (corners != null) {
            tag.put(CORNER_ONE, NbtUtils.writeBlockPos(corners.getA()));
            tag.put(CORNER_TWO, NbtUtils.writeBlockPos(corners.getB()));
        }

        return tag;
    }

    /*******************************************************************************************************************
     * Getter/Setters                                                                                                  *
     *******************************************************************************************************************/

    /**
     * Retrieves the current remaining fuel time.
     *
     * @return The current remaining fuel time.
     */

    public int getFuelTime() {
        return fuelTime;
    }

    /**
     * Sets the fuel time for the StandardValues class.
     *
     * @param fuelTime The new value for the fuel time.
     */
    public void setFuelTime(int fuelTime) {
        this.fuelTime = fuelTime;
    }

    /**
     * Adds the provided fuel time to the current fuel time.
     *
     * @param addFuelTime The fuel time to be added.
     */
    public void addFuelTime(int addFuelTime) {
        this.fuelTime += addFuelTime;
    }

    /**
     * Retrieves the current fuel provided time.
     *
     * @return The current fuel provided time.
     */
    public int getCurrentFuelProvidedTime() {
        return currentFuelProvidedTime;
    }

    /**
     * Sets the current fuel provided time.
     *
     * @param currentFuelProvidedTime The new value for the current fuel provided time.
     */
    public void setCurrentFuelProvidedTime(int currentFuelProvidedTime) {
        this.currentFuelProvidedTime = currentFuelProvidedTime;
    }

    /**
     * Retrieves the current work time.
     *
     * @return The current work time.
     */
    public int getWorkTime() {
        return workTime;
    }

    /**
     * Sets the work time for the StandardValues class.
     *
     * @param workTime The new value for the work time.
     */
    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    /**
     * Adds work time to the object.
     *
     * @param workTimeToAdd The amount of work time to add.
     */
    public void addWorkTime(int workTimeToAdd) {
        this.workTime += workTimeToAdd;
    }

    /**
     * Retrieves the current speed value.
     *
     * @return The current speed value.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Sets the speed for the StandardValues class.
     *
     * @param speed The new value for the speed.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Adds the provided speed to the current speed value.
     *
     * @param addSpeed The speed to be added.
     */
    public void addSpeed(double addSpeed) {
        this.speed += addSpeed;
    }

    /**
     * Retrieves the efficiency value.
     *
     * @return The efficiency value.
     */
    public double getEfficiency() {
        return efficiency;
    }

    /**
     * Sets the efficiency value for the StandardValues class.
     *
     * @param efficiency The new value for the efficiency.
     */
    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    /**
     * Adds the provided efficiency value to the current efficiency.
     *
     * @param addEfficiency The efficiency value to be added.
     */
    public void addEfficiency(double addEfficiency) {
        this.efficiency += addEfficiency;
    }

    /**
     * Retrieves the current multiplicity value.
     *
     * @return The current multiplicity value.
     */
    public double getMultiplicity() {
        return multiplicity;
    }

    /**
     * Sets the multiplicity for the StandardValues class.
     *
     * @param multiplicity The new value for the multiplicity.
     */
    public void setMultiplicity(double multiplicity) {
        this.multiplicity = multiplicity;
    }

    /**
     * Adds the provided multiplicity to the current multiplicity value.
     *
     * @param addMultiplicity The multiplicity to be added.
     */
    public void addMultiplicity(double addMultiplicity) {
        this.multiplicity += addMultiplicity;
    }

    /**
     * Retrieves the status of whether the object is currently working or not.
     *
     * @return {@code true} if the object is working, {@code false} otherwise.
     */
    public boolean isWorking() {
        return isWorking;
    }

    /**
     * Sets the working status of the object.
     *
     * @param working The new working status. {@code true} if the object is working, {@code false} otherwise.
     */
    public void setWorking(boolean working) {
        isWorking = working;
    }

    /**
     * Checks if the object has been modified since the last save or load operation.
     *
     * @return {@code true} if the object has been modified, {@code false} otherwise.
     */
    public boolean isDirty() {
        return isDirty;
    }

    /**
     * Sets the dirty status of the object. The dirty status indicates whether the object's data has been modified and needs to be saved.
     *
     * @param dirty {@code true} if the object is dirty, {@code false} otherwise.
     */
    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    /**
     * Determines whether the object is well-formed or not.
     *
     * @return {@code true} if the object is well-formed, {@code false} otherwise.
     */
    public boolean isWellFormed() {
        return wellFormed;
    }

    /**
     * Sets the well-formed status of the object.
     *
     * @param formed {@code true} if the object is well-formed, {@code false} otherwise.
     */
    public void setWellFormed(boolean formed) {
        wellFormed = formed;
    }

    /**
     * Sets the corners of the cuboid shape.
     *
     * @param corners The tuple representing the two corner positions of the cuboid. Can be null.
     */
    public void setCorners(@Nullable Tuple<BlockPos, BlockPos> corners) {
        this.corners = corners;
    }

    /**
     * Retrieves the corners of the object.
     *
     * @return A Tuple containing the two BlockPositions representing the corners of the object, or null if the corners are not set.
     */
    public @Nullable Tuple<BlockPos, BlockPos> getCorners() {
        return corners;
    }
}
