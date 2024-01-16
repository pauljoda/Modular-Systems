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
                "Create a cuboid with the core as part of one of the faces");

        // Providers
        // Solids
        addWithDescription(Registration.CUBOID_BANK_SOLIDS_BLOCK_ITEM,
                "Solid Fuel Provider",
                "Provides solid fuel to a cuboid multiblock");

        // Menus -------------------------------------------------------------------------------------------------------
        // Furnace Core
        add("modular_systems.information.menu", "Information");
        add("modular_systems.speed.menu", "Speed: ");
        add("modular_systems.efficiency.menu", "Efficiency: ");
        add("modular_systems.multiplicity.menu", "Multiplicity: ");

        // Fuel Providers
        add("modular_systems.priority.menu", "Priority");
    }

    private void addWithDescription(DeferredHolder<Item, ? extends Item> item, String name, String desc) {
        add(item.get(), name);
        add(item.get().getDescriptionId() + ".desc", desc);
    }
}