package com.pauljoda.modularsystems.core.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankBaseBE;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class CuboidBankBER implements BlockEntityRenderer<CuboidBankBaseBE> {

    private final BlockRenderDispatcher blockRenderer;

    private final ModelManager modelManager;

    private static final Material TEXTURE_INSIDE = new Material(InventoryMenu.BLOCK_ATLAS,
            new ResourceLocation("minecraft", "block/iron_block"));

    public CuboidBankBER(BlockEntityRendererProvider.Context context) {
        this.modelManager = context.getBlockRenderDispatcher().getBlockModelShaper().getModelManager();
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(CuboidBankBaseBE pBlockEntity,
                       float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();

        float level = (float) Math.max(3 / 16F, (pBlockEntity.getPowerLevelScaled(8) + 4) / 16F);

        final TextureAtlasSprite tas = TEXTURE_INSIDE.sprite();

        VertexConsumer buffer = pBuffer.getBuffer(RenderType.solid());


        addQuad(buffer, pPoseStack, tas,
                7 / 16F, 4 / 16F, 14.5F / 16F,
                9 / 16F, level, 14.5F / 16F,
                7 / 16F, 4 / 16F,
                9 / 16F, level, pPackedOverlay, Integer.MAX_VALUE, Direction.NORTH);
        addQuad(buffer, pPoseStack, tas,
                9 / 16F, 4 / 16F, 1.5F / 16F,
                7 / 16F, level, 1.5F / 16F,
                7 / 16F, 4 / 16F,
                9 / 16F, level, pPackedOverlay, Integer.MAX_VALUE, Direction.SOUTH);
        addQuad(buffer, pPoseStack, tas,
                1.5F / 16F, 4 / 16F, 7F / 16F,
                1.5F / 16F, level, 9F / 16F,
                7 / 16F, 4 / 16F,
                9 / 16F, level, pPackedOverlay, Integer.MAX_VALUE, Direction.EAST);
        addQuad(buffer, pPoseStack, tas,
                14.5F / 16F, 4 / 16F, 9F / 16F,
                14.5F / 16F, level, 7F / 16F,
                7 / 16F, 4 / 16F,
                9 / 16F, level, pPackedOverlay, Integer.MAX_VALUE, Direction.WEST);

        pPoseStack.popPose();
    }

    private static void addVertex(VertexConsumer vb, PoseStack ms, TextureAtlasSprite sprite, float x, float y,
                                  float z, float texU, float texV, int overlayUV, int lightmapUV, Direction front) {
        vb.vertex(ms.last().pose(), x, y, z);
        vb.color(1.0f, 0.0f, 0.0f, 1.0f);
        vb.uv(sprite.getU(texU), sprite.getV(texV));
        vb.overlayCoords(overlayUV);
        vb.uv2(lightmapUV);
        vb.normal(ms.last().normal(), front.getStepX(), front.getStepY(), front.getStepZ());
        vb.endVertex();
    }

    private static void addQuad(VertexConsumer buffer, PoseStack pPoseStack, TextureAtlasSprite tas,
                                float xStart, float yStart, float zStart,
                                float xEnd, float yEnd, float zEnd,
                                float texUStart, float texVStart, float texUEnd, float texVEnd,
                                int pPackedOverlay, int pPackedLight, Direction front) {
        addVertex(buffer, pPoseStack, tas,
                xStart, yStart, zStart,
                texUStart, texVStart,
                pPackedOverlay, pPackedLight,
                front);
        addVertex(buffer, pPoseStack, tas,
                xEnd, yStart, zEnd,
                texUEnd, texVStart,
                pPackedOverlay, pPackedLight,
                front);
        addVertex(buffer, pPoseStack, tas,
                xEnd, yEnd, zEnd,
                texUEnd, texVEnd,
                pPackedOverlay, pPackedLight,
                front);
        addVertex(buffer, pPoseStack, tas,
                xStart, yEnd, zStart,
                texUStart, texVEnd,
                pPackedOverlay, pPackedLight,
                front);
    }
}
