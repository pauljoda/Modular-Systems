package com.pauljoda.modularsystems.core.multiblock;
import java.util.Comparator;

public interface FuelProvider {
    /**
     * Whether the provider can provide any fuel.
     * @return Can this provide
     */
    boolean canProvide();

    /**
     * How much fuel will be provided when consume is called. Should be mainly used for display purposes.
     * @return How much would be consumed
     */
    double fuelProvided();

    /**
     * Consumes fuel and returns how much fuel is provided.
     * @return How much was consumed
     */
    double consume();

    /**
     * Used to set the priority for this provider
     * @return 0 First, higher last
     */
    int getPriority();

    /**
     * Provides what type of fuel is used for sorting purposes.
     * @return Type of provider
     */
    FuelProviderType type();


    /**
     * Enum to represent the type of fuel provider.
     */
    enum FuelProviderType {
        LIQUID,
        POWER,
        ITEM,
        ENERGY,
        OTHER
    }

    /**
     * The FuelSorter class implements the Comparator interface to provide a way to compare FuelProvider objects based on their priority.
     * It sorts the FuelProvider objects in descending order of their priority.
     */
    final class FuelSorter implements Comparator<FuelProvider> {
        /**
         * Compares two FuelProvider objects based on their priority.
         * It sorts the FuelProvider objects in descending order of their priority.
         *
         * @param o1 the first FuelProvider object to be compared
         * @param o2 the second FuelProvider object to be compared
         * @return a negative integer if o1 has a higher priority than o2, a positive integer if o1 has a lower priority than o2,
         *         or zero if both have the same priority
         */
        @Override
        public int compare(FuelProvider o1, FuelProvider o2) {
            return -Integer.compare(o1.getPriority(), o2.getPriority());
        }

        /**
         * Compares this object with the specified object for equality.
         * Returns true if and only if the specified object is a reference to this object.
         *
         * @param obj the object to be compared for equality with this object
         * @return true if the specified object is a reference to this object, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            return this == obj;
        }
    }
}