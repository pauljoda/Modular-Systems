package com.pauljoda.modularsystems.core.recipe.stonework;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

public class StoneWorkRecipeSerializer implements RecipeSerializer<StoneWorkRecipe> {

    /**
     * The Codec for serializing and deserializing StoneWorkRecipe objects.
     */
    public static final Codec<StoneWorkRecipe> CODEC =
            RecordCodecBuilder.create(stoneWorkRecipeInstance ->
                stoneWorkRecipeInstance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(StoneWorkRecipe::input),
                        ItemStack.RESULT_CODEC.fieldOf("output").forGetter(StoneWorkRecipe::output)
                ).apply(stoneWorkRecipeInstance, StoneWorkRecipe::new)
            );

    /**
     * Returns the codec for the StoneWorkRecipe class.
     *
     * @return The codec for StoneWorkRecipe.
     */
    @Override
    public Codec<StoneWorkRecipe> codec() {
        return CODEC;
    }

    /**
     * Converts the data received from the network into a StoneWorkRecipe object.
     *
     * @param pBuffer The buffer containing the data received from the network.
     * @return The StoneWorkRecipe object created from the data.
     */
    @Override
    public StoneWorkRecipe fromNetwork(FriendlyByteBuf pBuffer) {
        var input = Ingredient.fromNetwork(pBuffer);
        var output = pBuffer.readItem();
        return new StoneWorkRecipe(input, output);
    }

    /**
     * Writes the StoneWorkRecipe to a network buffer.
     *
     * @param pBuffer The network buffer to write to.
     * @param pRecipe The StoneWorkRecipe to write.
     */
    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, StoneWorkRecipe pRecipe) {
        pRecipe.input().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.output());
    }
}
