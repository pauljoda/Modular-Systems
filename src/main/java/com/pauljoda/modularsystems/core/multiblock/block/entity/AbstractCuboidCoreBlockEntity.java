package com.pauljoda.modularsystems.core.multiblock.block.entity;

import com.pauljoda.modularsystems.core.lib.Registration;
import com.pauljoda.modularsystems.core.math.function.BlockCountFunction;
import com.pauljoda.modularsystems.core.multiblock.FuelProvider;
import com.pauljoda.modularsystems.core.multiblock.StandardCuboidValues;
import com.pauljoda.modularsystems.core.multiblock.block.AbstractCuboidCoreBlock;
import com.pauljoda.modularsystems.core.multiblock.providers.block.entity.CuboidBankBaseBlockEntity;
import com.pauljoda.nucleus.capabilities.InventoryHolder;
import com.pauljoda.nucleus.common.blocks.entity.item.InventoryHandler;
import com.pauljoda.nucleus.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.pauljoda.nucleus.common.blocks.BlockFourWayRotating.FOUR_WAY;

public abstract class AbstractCuboidCoreBlockEntity extends InventoryHandler implements MenuProvider {

    /*******************************************************************************************************************
     * Variables                                                                                                       *
     *******************************************************************************************************************/
    // Base work time (10 seconds)
    protected final int BASE_WORK_TIME = 200;
    // Base "Burn Time", based off coal in a furnace
    protected final int BASE_FUEL_TIME = 1600;

    protected static final int INPUT_SLOT = 0;
    protected static final int OUTPUT_SLOT = 1;

    // Max Cuboid Size
    protected final int MAX_EDGE_SIZE = 16;
    protected final int MAX_SIZE = ((MAX_EDGE_SIZE * MAX_EDGE_SIZE) * 2) + // Top and bottom
            ((MAX_EDGE_SIZE - 2) * (MAX_EDGE_SIZE - 2) * 4) + // Faces
            ((MAX_EDGE_SIZE - 2) * 4); // Pillars

    // Values
    public StandardCuboidValues values;

    public final ContainerData coreData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> values.getFuelTime();
                case 1 -> values.getCurrentFuelProvidedTime();
                case 2 -> values.getWorkTime();
                case 3 -> getAdjustedProcessTime();
                default -> throw new IllegalArgumentException("Invalid Index: " + pIndex);
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            throw new IllegalStateException("Cannot set values through IIntArray");
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    /*******************************************************************************************************************
     * Constructor                                                                                                       *
     *******************************************************************************************************************/

    /**
     * Initializes a new instance of the AbstractCuboidCore class.
     *
     * @param tileEntityTypeIn The type of the tile entity.
     * @param pos              The position of the block.
     * @param state            The state of the block.
     */
    public AbstractCuboidCoreBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);

        values = new StandardCuboidValues();
    }

    /*******************************************************************************************************************
     * Abstract Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * Applies a recipe to the given stack.
     *
     * @param stack The item stack to apply the recipe to.
     * @return The resulting item stack after applying the recipe.
     */
    public abstract ItemStack recipe(ItemStack stack);

    /**
     * Checks if a given block state is banned.
     *
     * @param blockState The block state to check.
     * @return true if the block is banned, false otherwise.
     */
    public abstract boolean isBlockBanned(BlockState blockState);

    /**
     * Generates values using the provided BlockCountFunction.
     *
     * @param function The BlockCountFunction used to generate values.
     */
    public abstract void generateValues(BlockCountFunction function);

    /**
     * Retrieves the redstone output of the cuboid core.
     * <p>
     * Value 0 - 16
     *
     * @return The redstone output value.
     */
    public abstract int getRedstoneOutput();

    /*******************************************************************************************************************
     * Inventory Methods                                                                                                *
     *******************************************************************************************************************/

    /**
     * The initial size of the inventory
     *
     * @return How big to make the inventory on creation
     */
    @Override
    protected int getInventorySize() {
        return 2;
    }

    @Override
    protected InventoryHolder initializeInventory() {
        return new InventoryHolder() {
            @Override
            protected int getInventorySize() {
                return AbstractCuboidCoreBlockEntity.this.getInventorySize();
            }

            @Override
            protected boolean isItemValidForSlot(int index, ItemStack stack) {
                return AbstractCuboidCoreBlockEntity.this.isItemValidForSlot(index, stack);
            }

            @Override
            public boolean isInputSlot(int slot) {
                return slot == INPUT_SLOT;
            }

            @Override
            public boolean isOutputSlot(int slot) {
                return slot == OUTPUT_SLOT;
            }
        };
    }

    /*******************************************************************************************************************
     * Multiblock Methods                                                                                              *
     *******************************************************************************************************************/

    /**
     * Overrides the onServerTick() method from the superclass.
     * This method is called every tick on the server-side.
     * It performs additional logic specific to the implementation of AbstractCuboidCoreBlockEntity.
     */
    @Override
    public void onServerTick() {
        if(updateMultiblock())
            doWork();
    }

    /**
     * Updates the multiblock based on the current state of the values.
     * If the values are dirty, checks if the multiblock is well-formed.
     * If it is well-formed, builds the multiblock.
     * If it is not well-formed, deconstructs the multiblock.
     * Sets the dirty flag to false after updating.
     *
     * @return true if the multiblock is well-formed after updating, false otherwise.
     */
    protected boolean updateMultiblock() {
        if (values.isDirty()) {
            if (isWellFormed()) {
                buildMultiblock();
            } else {
                deconstructMultiblock();
            }
            values.setDirty(false);
        }
        return values.isWellFormed();
    }

    /**
     * Checks if the current multiblock structure is well-formed.
     *
     * @return {@code true} if the multiblock is well-formed, {@code false} otherwise.
     */
    protected boolean isWellFormed() {
        var wellFormed = false;

        // Catch if somehow checking before block is available
        if(getLevel().isEmptyBlock(getBlockPos()))
            return false;

        var corners = getCorners();
        if(corners != null) {
            // Cache corners
            values.setCorners(corners);
            var boundedLists = LevelUtils.getAllBetweenTogether(corners.getA(), corners.getB());
            var inside = boundedLists.getA();
            var outside = boundedLists.getB();

            // Must have an interior
            if(inside.size() <= 0)
                return false;

            // Must be hollow
            for (var location : inside)
                if(!getLevel().isEmptyBlock(location))
                    return false;

            // Check structure
            for (var location : outside) {
                // Don't check ourselves
                if(!location.equals(getBlockPos()) &&
                        !(getLevel().getBlockEntity(location) instanceof FuelProvider)) {
                    if (getLevel().isEmptyBlock(location) ||
                            isBlockBanned(getLevel().getBlockState(location)))
                        return false;
                }
            }

            // No issues found
            values.setWellFormed(true);
            return true;
        }

        return wellFormed;
    }

    public void buildMultiblock() {
        // Safety Check
        if(values.getCorners() == null)
            return;

        var outside = LevelUtils.getAllBetween(values.getCorners().getA(), values.getCorners().getB(), false, true);
        var blockCountFunction = new BlockCountFunction();
        for (var loc : outside) {
            // If not ourselves
            if(!loc.equals(getBlockPos())) {
                if(getLevel().getBlockEntity(loc) instanceof CuboidBankBaseBlockEntity bank) {
                    bank.setCoreLocation(loc);
                    bank.markForUpdate(Block.UPDATE_ALL);
                } else {
                    var blockState = getLevel().getBlockState(loc);
                    blockCountFunction.addBlock(blockState);

                    getLevel().setBlock(loc, Registration.CUBOID_PROXY_BLOCK.get().defaultBlockState(), Block.UPDATE_ALL);
                    var be = getLevel().getBlockEntity(loc);
                    // Make sure set
                    if(be instanceof CuboidProxyBlockEntity proxy) {
                        proxy.setCoreLocation(getBlockPos());
                        proxy.setStoredBlockState(blockState);
                        proxy.markForUpdate(Block.UPDATE_ALL);
                    }
                }
            }
        }

        generateValues(blockCountFunction);
        values.setWellFormed(true);
        markForUpdate(Block.UPDATE_ALL);
    }

    public void deconstructMultiblock() {
        // Safety Check
        if(values.getCorners() == null)
            return;

        values.resetStructureValues();
        var outside = LevelUtils.getAllBetween(values.getCorners().getA(), values.getCorners().getB(), false, true);
        for (var loc : outside) {
            // Not us
            if(!loc.equals(getBlockPos()) && !getLevel().isEmptyBlock(loc)) {
                if(getLevel().getBlockEntity(loc) instanceof CuboidProxyBlockEntity proxy) {
                    var blockState = proxy.getStoredBlockState();
                    getLevel().setBlock(loc, blockState, Block.UPDATE_ALL);
                } else if(getLevel().getBlockEntity(loc) instanceof CuboidBankBaseBlockEntity bank) {
                    bank.setCoreLocation(null);
                    bank.markForUpdate(Block.UPDATE_ALL);
                }
            }
        }

        values.setWellFormed(false);
        markForUpdate(Block.UPDATE_ALL);
    }

    /**
     * Retrieves the corners of the cuboid shape.
     *
     * @return A tuple containing the first and second corner positions, or null if the corners could not be found.
     */
    protected @Nullable Tuple<BlockPos, BlockPos> getCorners() {
        var local = getBlockPos();
        var firstCorner = new BlockPos(local);
        var secondCorner = new BlockPos(local);

        var dir = getLevel().getBlockState(local).getValue(FOUR_WAY);

        // Move Inside
        firstCorner = firstCorner.offset(dir.getOpposite().getNormal());
        secondCorner = secondCorner.offset(dir.getOpposite().getNormal());

        var right = LevelUtils.rotateRight(dir);
        var left = LevelUtils.rotateLeft(dir);

        // First Corner
        while(getLevel().isEmptyBlock(firstCorner)) {
            firstCorner = firstCorner.offset(right.getNormal());
            // If too far away
            if(getBlockPos().distSqr(firstCorner) > MAX_EDGE_SIZE) return null;
        }

        // Pop back inside
        firstCorner = firstCorner.offset(right.getOpposite().getNormal());

        // Find Floor
        while(getLevel().isEmptyBlock(firstCorner)) {
            firstCorner = firstCorner.offset(Direction.DOWN.getNormal());
            // Too far away
            if(getBlockPos().distSqr(firstCorner) > MAX_EDGE_SIZE) return null;
        }

        // Found, move to outside corner
        firstCorner = firstCorner.offset(right.getNormal()).offset(dir.getNormal());

        // Find Side
        while(getLevel().isEmptyBlock(secondCorner)) {
            secondCorner = secondCorner.offset(left.getNormal());
            // Too far
            if(getBlockPos().distSqr(secondCorner) > MAX_EDGE_SIZE) return null;
        }

        // Pop back inside
        secondCorner = secondCorner.offset(left.getOpposite().getNormal());

        // Move to back
        while(getLevel().isEmptyBlock(secondCorner)) {
            secondCorner = secondCorner.offset(dir.getOpposite().getNormal());
            // Too far
            if(getBlockPos().distSqr(secondCorner) > MAX_EDGE_SIZE) return null;
        }

        // Pop back inside
        secondCorner = secondCorner.offset(dir.getNormal());

        // Move Up
        while(getLevel().isEmptyBlock(secondCorner)) {
            secondCorner = secondCorner.offset(Direction.UP.getNormal());
            // Too far
            if(getBlockPos().distSqr(secondCorner) > MAX_EDGE_SIZE) return null;
        }

        // Found, move back
        secondCorner = secondCorner.offset(left.getNormal()).offset(dir.getOpposite().getNormal());

        return new Tuple<>(firstCorner, secondCorner);
    }

    /*******************************************************************************************************************
     * Work Methods                                                                                                    *
     *******************************************************************************************************************/

    boolean wasWorking = false;
    protected void doWork() {
        var didwork = false;
        wasWorking = values.getFuelTime() > 0;

        // Must be on server
        if(!getLevel().isClientSide) {
            // Use Fuel
            if(values.getFuelTime() > 0) {
                values.addFuelTime(-1);
                markForUpdate(Block.UPDATE_ALL);
            }

            if(canProcess(getItemCapability().getStackInSlot(INPUT_SLOT),
                    recipe(getItemCapability().getStackInSlot(INPUT_SLOT)),
                    getItemCapability().getStackInSlot(OUTPUT_SLOT))
                    && !values.isWorking()) {
                // Check Corners
                if (values.getCorners() == null) {
                    values.setCorners(getCorners());
                }
                if(values.getCorners() == null) {
                    markForUpdate(Block.UPDATE_ALL);
                    return;
                }

                var providers = getFuelProviders();
                if (values.getFuelTime() <= 0 && !providers.isEmpty()) {
                    var scaledFuelTime = getAdjustedFuelTime(providers.get(0).consume());
                    values.setFuelTime(scaledFuelTime);
                    values.setCurrentFuelProvidedTime(scaledFuelTime);
                    process();

                    // Started Work
                    var state = getLevel().getBlockState(getBlockPos()).setValue(AbstractCuboidCoreBlock.LIT, values.getFuelTime() > 0);
                    getLevel().setBlock(getBlockPos(), state, Block.UPDATE_ALL);
                    //markForUpdate(Block.UPDATE_ALL);
                }
                else if (values.getFuelTime() > 0) {
                    didwork = process();
                }
                else {
                    values.setWorkTime(0);
                    values.setFuelTime(0);
                    // Back to not working
                    var state = getLevel().getBlockState(getBlockPos()).setValue(AbstractCuboidCoreBlock.LIT, values.getFuelTime() > 0);
                    getLevel().setBlock(getBlockPos(), state, Block.UPDATE_ALL);
                    //markForUpdate(Block.UPDATE_ALL);
                }
            }
            else if (values.getFuelTime() <= 0 && wasWorking) {
                values.setWorkTime(0);
                var state = getLevel().getBlockState(getBlockPos()).setValue(AbstractCuboidCoreBlock.LIT, values.getFuelTime() > 0);
                getLevel().setBlock(getBlockPos(), state, Block.UPDATE_ALL);
                //markForUpdate(Block.UPDATE_ALL);
            }
            if(didwork) {
                var state = getLevel().getBlockState(getBlockPos()).setValue(AbstractCuboidCoreBlock.LIT, values.getFuelTime() > 0);
                getLevel().setBlock(getBlockPos(), state, Block.UPDATE_ALL);
                //markForUpdate(Block.UPDATE_ALL);
            }
        }
    }

    /**
     * Checks if the given input, result, and output ItemStacks can be processed.
     *
     * @param input  The input ItemStack.
     * @param result The result ItemStack.
     * @param output The output ItemStack.
     * @return true if the ItemStacks can be processed, false otherwise.
     */
    protected boolean canProcess(ItemStack input, ItemStack result, ItemStack output) {
        if(input.isEmpty() || result.isEmpty())
            return false;
        else if (output.isEmpty())
            return true;
        else if(!output.is(result.getItem()))
            return false;
        else {
            //The size below would be if the smeltingMultiplier = 1
            //If the smelting multiplier is > 1,
            //there is no guarantee that all potential operations will be completed.
            var minStackSize = output.getCount() + result.getCount();
            return minStackSize <= result.getMaxStackSize();
        }
    }

    /**
     * Retrieves a list of fuel providers within the cuboid shape.
     *
     * @return A list of FuelProvider objects within the cuboid shape, sorted in descending order of their priority.
     */
    protected List<FuelProvider> getFuelProviders() {
        var providers = new ArrayList<FuelProvider>();
        if(values.getCorners() == null)
            return providers;

        for (var coord :
                LevelUtils.getAllBetween(values.getCorners().getA(), values.getCorners().getB(),
                        false, true))
            if(getLevel().getBlockEntity(coord) instanceof FuelProvider fuelProvider &&
                fuelProvider.canProvide())
                providers.add(fuelProvider);

        providers.sort(new FuelProvider.FuelSorter());
        return providers;
    }

    /**
     * Retrieves the adjusted fuel time based on the given fuel time.
     *
     * @param fuelTime The fuel time to adjust.
     * @return The adjusted fuel time.
     */
    protected int getAdjustedFuelTime(double fuelTime) {
        var scaledTicks = ((BASE_FUEL_TIME + values.getEfficiency()) / BASE_FUEL_TIME) * fuelTime;
        scaledTicks = scaledTicks / (values.getMultiplicity() + 1);
        return (int) Math.max(Math.round(scaledTicks), 5);
    }

    /**
     * Retrieves the adjusted process time based on the base work time and the speed value.
     *
     * @return The adjusted process time.
     */
    protected int getAdjustedProcessTime() {
        return (int) Math.max(BASE_WORK_TIME + values.getSpeed(), 1);
    }

    /**
     * Processes the work by incrementing the work time. If the work time exceeds the adjusted process time,
     * it resets the work time, processes the item, and returns true. Otherwise, it returns false.
     *
     * @return true if the item was processed, false otherwise.
     */
    protected boolean process() {
        values.addWorkTime(1);
        if (values.getWorkTime() >= getAdjustedProcessTime()) {
            values.setWorkTime(0);
            processItem();
            return true;
        }
        return false;
    }

    /**
     * Process the item in the input slot and update the output slot.
     */
    protected void processItem() {
        var processCount = processCountAndSize();
        if(processCount != null && processCount.getB() > 0) {
            var inventory = getItemCapability();
            var recipeResult = recipe(inventory.getStackInSlot(INPUT_SLOT));

            // Decrease Input
            var inputStack = inventory.getStackInSlot(INPUT_SLOT);
            if(inputStack.getCount() <= processCount.getB()) {
                inventory.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
            } else {
                inventory.extractItem(INPUT_SLOT, processCount.getB(), false);
            }

            // Increase Output
            if(inventory.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
                recipeResult = recipeResult.copy();
                recipeResult.setCount(processCount.getB());
                inventory.setStackInSlot(OUTPUT_SLOT, recipeResult);
            } else
                inventory.getStackInSlot(OUTPUT_SLOT).setCount(inventory.getStackInSlot(OUTPUT_SLOT).getCount() + processCount.getB());
            markForUpdate(Block.UPDATE_ALL);
        }
    }

    /**
     * Calculates the available and count values for processing an item.
     *
     * @return A Tuple object containing two integers, representing the available and count values.
     *         Returns null if the input stack is empty, or if the output stack does not meet the conditions for processing.
     */
    protected Tuple<Integer, Integer> processCountAndSize() {
        var inventory = getItemCapability();
        var input = inventory.getStackInSlot(INPUT_SLOT);

        if(input.isEmpty())
            return null;

        var output = inventory.getStackInSlot(OUTPUT_SLOT);
        var recipeResult = recipe(input);

        if(recipeResult.isEmpty() && !output.isEmpty() && !output.is(recipeResult.getItem()))
            return null;
        else if(output.isEmpty()) {
            output = recipeResult.copy();
            output.setCount(0);
        }
        input = input.copy();
        int recipeStackSize = recipeResult.getCount() > 0 ? recipeResult.getCount() : 1;
        int outputAvailable = output.getMaxStackSize() - output.getCount();
        int available = values.getMultiplicity() + 1 < input.getCount() ? (int) (values.getMultiplicity() + 1) : input.getCount();
        int count = recipeStackSize * available;
        if(count > outputAvailable) {
            available = outputAvailable / recipeStackSize;
            count = available * recipeStackSize;
        }
        return new Tuple<>(available, count);
    }

    /*******************************************************************************************************************
     * Block Entity Methods                                                                                            *
     *******************************************************************************************************************/

    /**
     * Saves the additional data of the {@link AbstractCuboidCoreBlockEntity} into the specified {@link CompoundTag}.
     *
     * @param compound The {@link CompoundTag} to store the data into.
     */
    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        values.save(compound);
    }

    /**
     * Loads the data from the given CompoundTag.
     *
     * @param compound The CompoundTag containing the data to be loaded.
     */
    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        values.load(compound);
    }
}
