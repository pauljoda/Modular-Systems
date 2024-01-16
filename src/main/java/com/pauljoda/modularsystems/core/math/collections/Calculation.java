package com.pauljoda.modularsystems.core.math.collections;

import java.util.Arrays;

public class Calculation {

    public static Calculation FLAT = new Calculation(0, 0, 0);

    protected double scaleFactor = 1;
    protected double floor = 0;
    protected double ceiling = 1;

    /**
     * Create a calculation object using the given parameters
     * <p>
     * A function will be created that will allow user to define behaviors
     * <p>
     * The function relates to:
     * y = (m / m1)(x + t)^p + b
     * <p>
     * The floor and ceiling are used to define the range this should not break from
     */
    public Calculation(double scaleFactorNum, double min, double max) {
        scaleFactor = scaleFactorNum;
        floor = min;
        ceiling = max;
    }

    /**
     * Used to create a new instance, copying the values of the other
     * @param calculation The other
     */
    public Calculation(Calculation calculation) {
        this(calculation.scaleFactor, calculation.floor, calculation.ceiling);
    }

    /**
     * Calculate the result using the defined function
     * @param x The blocks count or 'x' in the function
     * @return F(x)
     */
    public double F(int x) {
        return Math.max(floor, Math.min(ceiling, x * scaleFactor));
    }

    /**
     * Calculate the result using the defined function
     * @param x The blocks count or 'x' in the function
     * @return F(x)
     */
    public double F_NoClamp(int x) {
        return x * scaleFactor;
    }

    /*******************************************************************************************************************
     **************************************** Accessors and Mutators ***************************************************
     *******************************************************************************************************************/

    /**
     * Accessor for the Scale Factor Numerator
     * @return m1
     */
    public double getScaleFactor() {
        return scaleFactor;
    }

    /**
     * Mutator for the Scale Factor Numerator
     * @param scaleFactor The new value for m1
     */
    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }

    /**
     * The lower limit of the function
     * @return The lowest possible value
     */
    public double getFloor() {
        return floor;
    }

    /**
     * Set the lower limit
     * @param floor The new lowest value
     */
    public void setFloor(double floor) {
        this.floor = floor;
    }

    /**
     * The upper limit of the function
     * @return The highest value
     */
    public double getCeiling() {
        return ceiling;
    }

    /**
     * Set the new highest value
     * @param ceiling The new highest value
     */
    public void setCeiling(double ceiling) {
        this.ceiling = ceiling;
    }

    /*******************************************************************************************************************
     **************************************** Basic Java Things ********************************************************
     *******************************************************************************************************************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Calculation that = (Calculation) o;

        return  Double.compare(that.scaleFactor, scaleFactor) == 0 &&
                Double.compare(that.floor, floor) == 0 &&
                Double.compare(that.ceiling, ceiling) == 0;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {scaleFactor, floor, ceiling});
    }

    @Override
    public String toString() {
        return String.format("Scale Factor: %s, Floor: %s, Ceiling: %s", scaleFactor, floor, ceiling);
    }
}