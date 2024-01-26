package com.pauljoda.modularsystems.core.recipe.fluidfuel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidFuelRecipeSerializer implements RecipeSerializer<FluidFuelRecipe> {

    /**
     * Represents a codec for serializing and deserializing FluidFuelRecipe objects.
     */
    public static final Codec<FluidFuelRecipe> CODEC =
            RecordCodecBuilder.create(fluidFuelRecipe ->
                    fluidFuelRecipe.group(
                            FluidStack.CODEC.fieldOf("input").forGetter(FluidFuelRecipe::input),
                            Codec.INT.fieldOf("burntime").forGetter(FluidFuelRecipe::burntime)
                    ).apply(fluidFuelRecipe, FluidFuelRecipe::new)
            );

    /**
     * Returns the codec for the FluidFuelRecipe class.
     *
     * @return The codec for the FluidFuelRecipe class.
     */
    @Override
    public Codec<FluidFuelRecipe> codec() {
        return CODEC;
    }

    /**
     * Creates a Fluid Fuel Recipe from the network.
     *
     * @param friendlyByteBuf The FriendlyByteBuf containing the recipe data.
     * @return The FluidFuelRecipe object created from the network data.
     */
    @Override
    public FluidFuelRecipe fromNetwork(FriendlyByteBuf friendlyByteBuf) {
        var input = FluidStack.readFromPacket(friendlyByteBuf);
        var burnTime = friendlyByteBuf.readInt();
        return new FluidFuelRecipe(input, burnTime);
    }

    /**
     * Writes the FluidFuelRecipe object to the network.
     *
     * @param friendlyByteBuf The byte buffer to write the data to.
     * @param fluidFuelRecipe The FluidFuelRecipe object to write.
     */
    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, FluidFuelRecipe fluidFuelRecipe) {
        friendlyByteBuf.writeFluidStack(fluidFuelRecipe.input());
        friendlyByteBuf.writeInt(fluidFuelRecipe.burntime());
    }
}
