package com.pauljoda.modularsystems.core.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.pauljoda.modularsystems.core.multiblock.block.entity.CuboidProxyBlockHolderBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
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
            blockRenderer.renderSingleBlock(state, pPoseStack, pBuffer, 15728640, OverlayTexture.NO_OVERLAY,
                    ModelData.EMPTY, RenderType.cutout());
        }

        pPoseStack.popPose();
    }
}
