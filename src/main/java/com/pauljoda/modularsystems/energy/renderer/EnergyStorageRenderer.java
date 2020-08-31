package com.pauljoda.modularsystems.energy.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.pauljoda.modularsystems.core.tile.EnergyStorageTile;
import com.pauljoda.nucleus.util.ClientUtils;
import com.pauljoda.nucleus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/31/20
 */
public class EnergyStorageRenderer extends TileEntityRenderer<EnergyStorageTile> {

    public EnergyStorageRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(EnergyStorageTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(matrixStackIn.getLast().getMatrix());

        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.disableLighting();

        RenderUtils.bindTexture(new ResourceLocation("modularsystems", "textures/gui/generator.png"));

        BufferBuilder tess = Tessellator.getInstance().getBuffer();

        float level = (float) Math.max(3 / 16F, (tileEntityIn.getPowerLevelScaled(8) + 4) / 16F);

        RenderUtils.setColor(new Color(200, 0, 0));
        drawLevel(7 / 16F, 4 / 16F, 1.5F / 16F, 9 / 16F, level, 1.5F / 16F, tess);
        drawLevel(1.5F / 16F, 4 / 16F, 7 / 16F, 1.5F / 16F, level, 9 / 16F, tess);
        drawLevel(14.5F / 16F, 4 / 16F, 7 / 16F, 14.5F / 16F, level, 9 / 16F, tess);
        drawLevel(7 / 16F, 4 / 16F, 14.5F / 16F, 9 / 16F, level, 14.5F / 16F, tess);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.enableCull();
        RenderUtils.restoreRenderState();
        RenderUtils.restoreColor();
        RenderSystem.enableLighting();

        RenderSystem.popMatrix();

    }

    protected void drawLevel(float x1, float y1, float z1, float x2, float y2, float z2, BufferBuilder tess) {
        tess.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        tess.pos(x1, y1, z1).tex(2 / 255F, 2 / 255F).normal(0, -1, 0).endVertex();
        tess.pos(x1, y2, z1).tex(2 / 255F, 4 / 255F).normal(0, -1, 0).endVertex();
        tess.pos(x2, y2, z2).tex(3 / 255F, 4 / 255F).normal(0, -1, 0).endVertex();
        tess.pos(x2, y1, z2).tex(3 / 255F, 2 / 255F).normal(0, -1, 0).endVertex();
        Tessellator.getInstance().draw();
    }
}
