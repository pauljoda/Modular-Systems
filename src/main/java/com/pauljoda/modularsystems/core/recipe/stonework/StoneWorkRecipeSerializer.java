package com.pauljoda.modularsystems.core.recipe.stonework;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

public class StoneWorkRecipeSerializer implements RecipeSerializer<StoneWorkRecipe> {

    public static final Codec<StoneWorkRecipe> CODEC =
            RecordCodecBuilder.create(stoneWorkRecipeInstance ->
                stoneWorkRecipeInstance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(StoneWorkRecipe::input),
                        ItemStack.RESULT_CODEC.fieldOf("output").forGetter(StoneWorkRecipe::output)
                ).apply(stoneWorkRecipeInstance, StoneWorkRecipe::new)
            );

    @Override
    public Codec<StoneWorkRecipe> codec() {
        return CODEC;
    }

    @Override
    public StoneWorkRecipe fromNetwork(FriendlyByteBuf pBuffer) {
        var input = Ingredient.fromNetwork(pBuffer);
        var output = pBuffer.readItem();
        return new StoneWorkRecipe(input, output);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, StoneWorkRecipe pRecipe) {
        pRecipe.input().toNetwork(pBuffer);
        pBuffer.writeItem(pRecipe.output());
    }
}
