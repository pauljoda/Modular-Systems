package com.pauljoda.modularsystems.data.models;

import java.util.Arrays;
import com.pauljoda.modularsystems.math.Calculation;

/**
 * This file was created for Modular-Systems
 *
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since August 05, 2015
 */
public class BlockValues {
    protected Calculation speedFunction;
    protected Calculation efficiencyFunction;
    protected Calculation multiplicityFunction;

    /**
     * Creates block values using the given calculations
     * @param speed The speed calculation
     * @param efficiency The efficiency Calculation
     * @param multiplicity The multiplicity calculation
     */
    public BlockValues(Calculation speed, Calculation efficiency, Calculation multiplicity) {
        speedFunction = new Calculation(speed);
        efficiencyFunction = new Calculation(efficiency);
        multiplicityFunction = new Calculation(multiplicity);
    }

    /*******************************************************************************************************************
     ****************************************** Getters and Setters ****************************************************
     *******************************************************************************************************************/

    /**
     * Get the speed function
     */
    public Calculation getSpeedFunction() {
        return speedFunction;
    }

    /**
     * Set the speed function
     *
     * This will create a new object using the given objects values
     */
    public void setSpeedFunction(Calculation speedFunction) {
        this.speedFunction = new Calculation(speedFunction);
    }

    /**
     * Get the efficiency function
     */
    public Calculation getEfficiencyFunction() {
        return efficiencyFunction;
    }

    /**
     * Set the efficiency function
     *
     * Creates a new object given the passing objects values
     */
    public void setEfficiencyFunction(Calculation efficiencyFunction) {
        this.efficiencyFunction = new Calculation(efficiencyFunction);
    }

    /**
     * Get the multiplicity function
     */
    public Calculation getMultiplicityFunction() {
        return multiplicityFunction;
    }

    /**
     * Sets the function to a new object given the passing values
     */
    public void setMultiplicityFunction(Calculation multiplicityFunction) {
        this.multiplicityFunction = new Calculation(multiplicityFunction);
    }

    /*******************************************************************************************************************
     ************************************************* Java Stuff ******************************************************
     *******************************************************************************************************************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlockValues that = (BlockValues) o;

        return !(getSpeedFunction() != null ? !getSpeedFunction().equals(that.getSpeedFunction()) : that.getSpeedFunction() != null) &&
                !(getEfficiencyFunction() != null ? !getEfficiencyFunction().equals(that.getEfficiencyFunction()) : that.getEfficiencyFunction() != null) &&
                !(getMultiplicityFunction() != null ? !getMultiplicityFunction().equals(that.getMultiplicityFunction()) : that.getMultiplicityFunction() != null);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {speedFunction, efficiencyFunction, multiplicityFunction});
    }
}
