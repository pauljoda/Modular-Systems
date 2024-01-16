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
        add("cuboid.multiblock", "Cuboid Multiblock");

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
        add("information.menu", "Information");
        add("speed.menu", "Speed: ");
        add("efficiency.menu", "Efficiency: ");
        add("multiplicity.menu", "Multiplicity: ");

        // Fuel Providers
        add("priority.menu", "Priority");
    }

    private void addWithDescription(DeferredHolder<Item, ? extends Item> item, String name, String desc) {
        add(item.get(), name);
        add(item.get().getDescriptionId() + ".desc", desc);
    }
}