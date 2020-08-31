package com.pauljoda.modularsystems.core.block;

import com.pauljoda.modularsystems.core.multiblock.BaseMultiBlockBlock;
import com.pauljoda.modularsystems.core.multiblock.interfaces.IMultiBlockExpansion;
import com.pauljoda.modularsystems.core.tile.EnergyStorageTile;
import com.pauljoda.nucleus.common.IAdvancedToolTipProvider;
import com.pauljoda.nucleus.util.ClientUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

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
