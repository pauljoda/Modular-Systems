package com.pauljoda.modularsystems.core.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidProxyBlockHolderBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.client.model.data.ModelData;

public class CuboidProxyBER implements BlockEntityRenderer<CuboidProxyBlockHolderBE> {

    private final BlockRenderDispatcher blockRenderer;

    private final ModelManager modelManager;

    public CuboidProxyBER(BlockEntityRendererProvider.Context context) {
        this.modelManager = context.getBlockRenderDispatcher().getBlockModelShaper().getModelManager();
        this.blockRenderer = context.getBlockRenderDispatcher();
    }
    @Override
    public void render(CuboidProxyBlockHolderBE pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();

        if(pBlockEntity.getStoredBlockState() != null) {
            var state = pBlockEntity.getStoredBlockState();
            blockRenderer.getModelRenderer().tesselateWithAO(
                    pBlockEntity.getLevel(),
                    blockRenderer.getBlockModel(state),
                    state,
                    pBlockEntity.getBlockPos(),
                    pPoseStack,
                    pBuffer.getBuffer(RenderType.cutout()),
                    false,
                    RandomSource.create(),
                    state.getSeed(pBlockEntity.getBlockPos()),
                    pPackedOverlay);
        }

        pPoseStack.popPose();
    }
}
