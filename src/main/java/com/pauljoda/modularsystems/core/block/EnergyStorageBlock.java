package com.pauljoda.modularsystems.core.block;

import com.pauljoda.modularsystems.core.multiblock.BaseMultiBlockBlock;
import com.pauljoda.modularsystems.core.tile.EnergyStorageTile;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created for Modular-Systems
 * <p>
 * Modular-Systems is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis - pauljoda
 * @since 8/30/20
 */
public class EnergyStorageBlock extends BaseMultiBlockBlock implements IAdvancedToolTipProvider {

    /**
     * Base Constructor
     */
    public EnergyStorageBlock() {
        super(Properties.create(new Material(MaterialColor.STONE, false, false,
                true, false,
                false, false,
                PushReaction.BLOCK)).harvestTool(ToolType.PICKAXE).hardnessAndResistance(1.0F),
                "energy_storage", EnergyStorageTile.class);
    }

    /*******************************************************************************************************************
     * IAdvancedToolTipProvider                                                                                        *
     *******************************************************************************************************************/

    /**
     * Get the tool tip to present when shift is pressed
     *
     * @param stack The itemstack
     *
     * @return The list to display
     */
    @Override
    public List<String> getAdvancedToolTip(ItemStack stack) {
        List<String> tips = new ArrayList<>();

        tips.add(ClientUtils.translate("modularsystems.compatible_systems"));
        tips.add("  - " + ClientUtils.translate("modularsystems.generator"));

        return tips;
    }
}
