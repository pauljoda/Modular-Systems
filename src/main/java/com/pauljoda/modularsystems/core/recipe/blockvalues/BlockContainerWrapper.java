package com.pauljoda.modularsystems.core.recipe.blockvalues;

import com.pauljoda.nucleus.recipe.ValueContainerWrapper;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.nullness.qual.NonNull;

public record BlockContainerWrapper(@NonNull BlockState heldBlock) implements ValueContainerWrapper {
    @Override
    @NonNull
    public BlockState heldBlock() {
        return heldBlock;
    }
}
