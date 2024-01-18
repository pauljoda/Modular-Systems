package com.pauljoda.modularsystems.core.datagen;
import com.pauljoda.modularsystems.core.lib.Reference;
import com.pauljoda.modularsystems.core.lib.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class TranslationGenerator extends LanguageProvider {

    public TranslationGenerator(PackOutput gen, String locale) {
        super(gen, Reference.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // Creative Tab
        add("itemGroup." + Reference.MOD_ID, "Modular Systems");

        // Info
        add("modular_systems.cuboid.multiblock", "Cuboid Multiblock");
        add("modular_systems.blockvalues.shift", "Press <SHIFT> to view Modular Systems Values");
        add("modular_systems.blockvalues.details", "Modular Systems Block Values:");
        add("modular_systems.ticks", "Ticks");

        // Items -------------------------------------------------------------------------------------------------------


        // Blocks ------------------------------------------------------------------------------------------------------
        // Furnace Core
        addWithDescription(Registration.FURNACE_CORE_BLOCK_ITEM,
                "Modular Furnace Core",
                "Cuboid Multiblock - Smelts items with same recipe as the furnace");

        // Stone Work
        addWithDescription(Registration.STONE_WORK_CORE_BLOCK_ITEM,
                "Modular Stone Works Core",
                "Cuboid Multiblock - Processes stone blocks");

        // Providers
        // Solids
        addWithDescription(Registration.CUBOID_BANK_SOLIDS_BLOCK_ITEM,
                "Solid Fuel Provider",
                "Provides solid fuel to a cuboid multiblock");

        // IO
        addWithDescription(Registration.CUBOID_IO_BLOCK_ITEM,
                "IO Expansion",
                "Add to a cuboid multiblock to import and export from the multiblock, pushes and pulls");

        // Menus -------------------------------------------------------------------------------------------------------
        // Furnace Core
        add("modular_systems.information.menu", "Information");
        add("modular_systems.speed.menu", "Speed: ");
        add("modular_systems.process_time.menu", "Process Time: ");
        add("modular_systems.efficiency.menu", "Efficiency: ");
        add("modular_systems.multiplicity.menu", "Multiplicity: ");

        // Fuel Providers
        add("modular_systems.priority.menu", "Priority");

        // IO
        add("modular_systems.push.menu", "Push");
        add("modular_systems.pull.menu", "Pull");
    }

    private void addWithDescription(DeferredHolder<Item, ? extends Item> item, String name, String desc) {
        add(item.get(), name);
        add(item.get().getDescriptionId() + ".desc", desc);
    }
}