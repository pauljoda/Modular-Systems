package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pauljoda.modularsystems.core.math.collections.Calculation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BlockValuesRecipeSerializer implements RecipeSerializer<BlockValueRecipe> {

    /**
     * The Codec for the BlockValueRecipe class.
     */
    public static final Codec<BlockValueRecipe> CODEC =
            RecordCodecBuilder.create(blockValueRecipeInstance ->
                    blockValueRecipeInstance.group(
                            Ingredient.CODEC.fieldOf("inputBlock").forGetter(BlockValueRecipe::inputBlock),
                            Calculation.CODEC.fieldOf("speedCalculation").forGetter(BlockValueRecipe::speedCalculation),
                            Calculation.CODEC.fieldOf("efficiencyCalculation").forGetter(BlockValueRecipe::efficiencyCalculation),
                            Calculation.CODEC.fieldOf("multiplicityCalculation").forGetter(BlockValueRecipe::multiplicityCalculation)
                    ).apply(blockValueRecipeInstance, BlockValueRecipe::new)
            );

    /**
     * Retrieves the codec for the BlockValueRecipe class.
     *
     * @return The codec for BlockValueRecipe.
     */
    @Override
    public Codec<BlockValueRecipe> codec() {
        return CODEC;
    }

    /**
     * Constructs a Calculation object by reading data from a FriendlyByteBuf.
     *
     * @param pBuffer The buffer containing the data to read.
     * @return A new Calculation object initialized with the read data.
     */
    @Override
    public BlockValueRecipe fromNetwork(FriendlyByteBuf pBuffer) {
        var block = Ingredient.fromNetwork(pBuffer);
        var speed = Calculation.fromNetwork(pBuffer);
        var efficiency = Calculation.fromNetwork(pBuffer);
        var multiplicity = Calculation.fromNetwork(pBuffer);
        return new BlockValueRecipe(block, speed, efficiency, multiplicity);
    }

    /**
     * Writes the given BlockValueRecipe object to a FriendlyByteBuf for network transmission.
     *
     * @param pBuffer The FriendlyByteBuf to write the data to.
     * @param pRecipe The BlockValueRecipe object to write.
     */
    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, BlockValueRecipe pRecipe) {
        pRecipe.inputBlock().toNetwork(pBuffer);
        Calculation.toNetwork(pBuffer, pRecipe.speedCalculation());
        Calculation.toNetwork(pBuffer, pRecipe.efficiencyCalculation());
        Calculation.toNetwork(pBuffer, pRecipe.multiplicityCalculation());
    }
}
