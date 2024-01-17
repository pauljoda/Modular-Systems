package com.pauljoda.modularsystems.core.multiblock.cuboid.screen;

import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidIOBE;
import com.pauljoda.modularsystems.core.multiblock.cuboid.container.CuboidIOContainer;
import com.pauljoda.modularsystems.core.network.bidirectional.SyncableFieldPacket;
import com.pauljoda.nucleus.client.gui.MenuBase;
import com.pauljoda.nucleus.client.gui.widget.control.MenuWidgetCheckBox;
import com.pauljoda.nucleus.network.PacketManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CuboidIOMenu extends MenuBase<CuboidIOContainer> {

    protected final CuboidIOContainer container;

    /**
     * Main constructor for Guis
     *
     * @param inventory       The container
     * @param playerInventory
     * @param title           The title of the gui
     */
    public CuboidIOMenu(CuboidIOContainer inventory, Inventory playerInventory, Component title) {
        super(inventory, playerInventory, title, 130, 75,
                new ResourceLocation(Reference.MOD_ID, "textures/gui/cuboid_io.png"));

        this.container = inventory;
        addComponents();
    }

    /**
     * This will be called after the GUI has been initialized and should be where you add all components.
     */
    @Override
    protected void addComponents() {
        if (this.container != null) {
            components.add(new MenuWidgetCheckBox(this, 10, 25, 130, 0,
                    container.getCuboidIOBE().isPull(),
                    "modular_systems.pull.menu") {
                @Override
                protected void setValue(boolean value) {
                    PacketManager.INSTANCE
                            .sendToServer(new SyncableFieldPacket(false, CuboidIOBE.PULL_TOGGLE, 1.0D,
                                    container.getCuboidIOBE().getBlockPos()));
                }
            });
            components.add(new MenuWidgetCheckBox(this, 10, 45, 130, 0,
                    container.getCuboidIOBE().isPush(),
                    "modular_systems.push.menu") {
                @Override
                protected void setValue(boolean value) {
                    PacketManager.INSTANCE
                            .sendToServer(new SyncableFieldPacket(false, CuboidIOBE.PUSH_TOGGLE, 1.0D,
                                    container.getCuboidIOBE().getBlockPos()));
                }
            });
        }
    }
}
