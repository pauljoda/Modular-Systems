package com.pauljoda.modularsystems.generator.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.generator.container.GeneratorContainer;
import com.pauljoda.modularsystems.generator.tile.GeneratorTile;
import com.pauljoda.nucleus.client.gui.GuiBase;
import com.pauljoda.nucleus.client.gui.component.display.GuiComponentColoredZone;
import com.pauljoda.nucleus.client.gui.component.display.GuiComponentText;
import com.pauljoda.nucleus.client.gui.component.display.GuiComponentTextureAnimated;
import com.pauljoda.nucleus.util.ClientUtils;
import com.pauljoda.nucleus.util.EnergyUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.energy.CapabilityEnergy;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/29/20
 */
public class GeneratorScreen extends GuiBase<GeneratorContainer> {
    protected GeneratorTile generator;

    public GeneratorScreen(GeneratorContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title,
                175, 165,
                new ResourceLocation(Reference.MOD_ID, "textures/gui/generator.png"));
        this.generator = container.tile;
        addComponents();
    }

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    @Override
    protected void addComponents() {
        if(generator != null) {
            // Energy Bar
            // Power Bar
            components.add(new GuiComponentTextureAnimated(this, 16, 12, 176, 14,
                    16, 62, GuiComponentTextureAnimated.ANIMATION_DIRECTION.UP) {
                @Override
                protected int getCurrentProgress(int scale) {
                    return (generator.getEnergyStored() * scale) / generator.getMaxEnergyStored();
                }
//
//                /**
//                 * Used to determine if a dynamic tooltip is needed at runtime
//                 *
//                 * @param mouseX Mouse X Pos
//                 * @param mouseY Mouse Y Pos
//                 * @return A list of string to display
//                 */
//                @Override
//                public List<ITextComponent> getDynamicToolTip(int mouseX, int mouseY) {
//                    List<ITextComponent> toolTip = new ArrayList<ITextComponent>();
//                    EnergyUtils.addToolTipInfo(machine.getCapability(CapabilityEnergy.ENERGY, null),
//                            toolTip, machine.energyStorage.getMaxInsert(), machine.energyStorage.getMaxExtract());
//                    return toolTip;
//                }
            });
        }
    }
}