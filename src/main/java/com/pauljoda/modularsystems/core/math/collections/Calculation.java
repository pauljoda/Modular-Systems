package com.pauljoda.modularsystems.core.math.collections;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Arrays;

public record Calculation(double scaleFactor, double floor, double ceiling) {

    public static final Codec<Calculation> CODEC =
            RecordCodecBuilder.create(calculationInstance ->
                calculationInstance.group(
                        Codec.DOUBLE.fieldOf("scaleFactor").forGetter(Calculation::scaleFactor),
                        Codec.DOUBLE.fieldOf("floor").forGetter(Calculation::floor),
                        Codec.DOUBLE.fieldOf("ceiling").forGetter(Calculation::ceiling)
                ).apply(calculationInstance, Calculation::new));

    public static Calculation FLAT = new Calculation(0, 0, 0);

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

    /**
     * Writes the current Calculation object to a FriendlyByteBuf for network transmission.
     *
     * @param buffer The FriendlyByteBuf to write the data to
     */
    public static void toNetwork(FriendlyByteBuf buffer, Calculation calculation) {
        buffer.writeDouble(calculation.scaleFactor);
        buffer.writeDouble(calculation.ceiling);
        buffer.writeDouble(calculation.floor);
    }

    /**
     * Constructs a Calculation object by reading data from a FriendlyByteBuf.
     *
     * @param buffer The buffer containing the data to read.
     * @return A new Calculation object initialized with the read data.
     */
    public static Calculation fromNetwork(FriendlyByteBuf buffer) {
        var scaleFactor = buffer.readDouble();
        var ceiling = buffer.readDouble();
        var floor = buffer.readDouble();

        return new Calculation(scaleFactor, ceiling, floor);
    }

    /*******************************************************************************************************************
     **************************************** Accessors and Mutators ***************************************************
     *******************************************************************************************************************/

    /**
     * Accessor for the Scale Factor Numerator
     * @return m1
     */
    public double scaleFactor() {
        return scaleFactor;
    }


    /**
     * The lower limit of the function
     * @return The lowest possible value
     */
    public double floor() {
        return floor;
    }

    /**
     * The upper limit of the function
     * @return The highest value
     */
    public double ceiling() {
        return ceiling;
    }

    /*******************************************************************************************************************
     **************************************** Basic Java Things ********************************************************
     *******************************************************************************************************************/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Calculation that = (Calculation) o;

        return  Double.compare(that.scaleFactor(), scaleFactor) == 0 &&
                Double.compare(that.floor(), floor) == 0 &&
                Double.compare(that.ceiling(), ceiling) == 0;
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