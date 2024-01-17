package com.pauljoda.modularsystems.core.lib;

import com.pauljoda.modularsystems.core.multiblock.cuboid.block.CuboidIOBlock;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.CuboidProxyBlock;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidIOBE;
import com.pauljoda.modularsystems.core.multiblock.cuboid.block.entity.CuboidProxyBlockHolderBE;
import com.pauljoda.modularsystems.core.multiblock.cuboid.container.CuboidIOContainer;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankSolidsBE;
import com.pauljoda.modularsystems.furnace.block.FurnaceCoreBlock;
import com.pauljoda.modularsystems.furnace.block.entity.FurnaceCoreBE;
import com.pauljoda.modularsystems.furnace.container.FurnaceCoreContainer;
import com.pauljoda.modularsystems.core.multiblock.providers.block.CuboidBankSolidsBlock;
import com.pauljoda.modularsystems.core.multiblock.providers.container.CuboidBankSolidsContainer;
import com.pauljoda.nucleus.common.blocks.entity.item.InventoryHandler;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {
    /*******************************************************************************************************************
     * Registries                                                                                                      *
     *******************************************************************************************************************/

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.createBlocks(Reference.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Reference.MOD_ID);
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.createItems(Reference.MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS
            = DeferredRegister.create(Registries.MENU, Reference.MOD_ID);

    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Reference.MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MOD_ID);


    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        ITEMS.register(bus);
        CONTAINERS.register(bus);
        ENTITIES.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    /*******************************************************************************************************************
     * Items                                                                                                           *
     *******************************************************************************************************************/

    /*******************************************************************************************************************
     * Blocks                                                                                                          *
     *******************************************************************************************************************/

    // Cuboid
    public static final DeferredHolder<Block, CuboidProxyBlock> CUBOID_PROXY_BLOCK =
            BLOCKS.register("cuboid_proxy", () -> new CuboidProxyBlock());
    public static final DeferredHolder<Item, BlockItem> CUBOID_PROXY_ITEM_BLOCK =
            ITEMS.register("cuboid_proxy", () -> new BlockItem(CUBOID_PROXY_BLOCK.get(), new Item.Properties()));

    // Furnace Core
    public static final DeferredHolder<Block, FurnaceCoreBlock> FURNACE_CORE_BLOCK =
            BLOCKS.register("furnace_core", () -> new FurnaceCoreBlock());
    public static final DeferredHolder<Item, BlockItem> FURNACE_CORE_BLOCK_ITEM =
            ITEMS.register("furnace_core", () -> new BlockItem(FURNACE_CORE_BLOCK.get(), new Item.Properties()));
    // Providers
    // Solids
    public static final DeferredHolder<Block, CuboidBankSolidsBlock> CUBOID_BANK_SOLIDS_BLOCK =
            BLOCKS.register("cuboid_bank_solids", () -> new CuboidBankSolidsBlock());
    public static final DeferredHolder<Item, BlockItem> CUBOID_BANK_SOLIDS_BLOCK_ITEM =
            ITEMS.register("cuboid_bank_solids", () -> new BlockItem(CUBOID_BANK_SOLIDS_BLOCK.get(), new Item.Properties()));

    // IO
    public static final DeferredHolder<Block, CuboidIOBlock> CUBOID_IO_BLOCK =
            BLOCKS.register("cuboid_io", () -> new CuboidIOBlock());
    public static final DeferredHolder<Item, BlockItem> CUBOID_IO_BLOCK_ITEM =
            ITEMS.register("cuboid_io", () -> new BlockItem(CUBOID_IO_BLOCK.get(), new Item.Properties()));

    /*******************************************************************************************************************
     * Block Entity                                                                                                    *
     *******************************************************************************************************************/

    // Cuboid Proxy
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CuboidProxyBlockHolderBE>> CUBOID_PROXY_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("cuboid_proxy",
                    () -> BlockEntityType.Builder.of(CuboidProxyBlockHolderBE::new, CUBOID_PROXY_BLOCK.get()).build(null));

    // Furnace Core
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FurnaceCoreBE>> FURNACE_CORE_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("furnace_core",
                    () -> BlockEntityType.Builder.of(FurnaceCoreBE::new, FURNACE_CORE_BLOCK.get()).build(null));

    // Providers
    // Solids
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CuboidBankSolidsBE>> CUBOID_BANK_SOLIDS_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("cuboid_bank_solids",
                    () -> BlockEntityType.Builder.of(CuboidBankSolidsBE::new, CUBOID_BANK_SOLIDS_BLOCK.get()).build(null));

    // IO
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CuboidIOBE>> CUBOID_IO_BLOCK_ENTITY =
            BLOCK_ENTITY_TYPES.register("cuboid_io",
                    () -> BlockEntityType.Builder.of(CuboidIOBE::new, CUBOID_IO_BLOCK.get()).build(null));

    /*******************************************************************************************************************
     * Container                                                                                                       *
     *******************************************************************************************************************/

    // Furnace Core
    public static final DeferredHolder<MenuType<?>, MenuType<FurnaceCoreContainer>> FURNACE_CORE_CONTAINER =
            CONTAINERS.register("furnace_core",
                    () -> IMenuTypeExtension.create(FurnaceCoreContainer::new));

    // Providers
    // Solids
    public static final DeferredHolder<MenuType<?>, MenuType<CuboidBankSolidsContainer>> CUBOID_BANK_SOLIDS_CONTAINER =
            CONTAINERS.register("cuboid_bank_solids",
                    () -> IMenuTypeExtension.create(CuboidBankSolidsContainer::new));

    // IO
    public static final DeferredHolder<MenuType<?>, MenuType<CuboidIOContainer>> CUBOID_IO_CONTAINER =
            CONTAINERS.register("cuboid_io",
                    () -> IMenuTypeExtension.create(CuboidIOContainer::new));

    /*******************************************************************************************************************
     * Entity                                                                                                          *
     *******************************************************************************************************************/

    /*******************************************************************************************************************
     * Creative Tabs                                                                                                   *
     *******************************************************************************************************************/

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB
            = CREATIVE_MODE_TABS.register(Reference.MOD_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Reference.MOD_ID))
            .icon(() -> FURNACE_CORE_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                // Furnace Core
                output.accept(FURNACE_CORE_BLOCK_ITEM.get());

                // Providers
                // Solids
                output.accept(CUBOID_BANK_SOLIDS_BLOCK_ITEM.get());

                // IO
                output.accept(CUBOID_IO_BLOCK_ITEM.get());
            }).build());

    /*******************************************************************************************************************
     * Register Capabilities                                                                                           *
     *******************************************************************************************************************/

    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Proxy
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                CUBOID_PROXY_BLOCK_ENTITY.get(),
                (provider, dir) -> provider.getCore() != null ? provider.getCore().getItemCapabilitySided(dir) : null
        );

        // Furnace Core
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                FURNACE_CORE_BLOCK_ENTITY.get(),
                InventoryHandler::getItemCapabilitySided
        );

        // Providers
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                CUBOID_BANK_SOLIDS_BLOCK_ENTITY.get(),
                (CuboidBankSolidsBE provider, @Nullable Direction dir) -> provider.getItemCapability()
        );

        // IO
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                CUBOID_IO_BLOCK_ENTITY.get(),
                (provider, dir) -> provider.getCore() != null ? provider.getCore().getItemCapabilitySided(dir) : null
        );
    }
}
