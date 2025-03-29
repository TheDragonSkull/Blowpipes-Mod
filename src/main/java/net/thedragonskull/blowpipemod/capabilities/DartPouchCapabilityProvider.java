package net.thedragonskull.blowpipemod.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import org.jetbrains.annotations.Nullable;

public class DartPouchCapabilityProvider implements IItemHandler, INBTSerializable<CompoundTag> {
    private final ItemStackHandler inventory = new ItemStackHandler(6);

    public static final ItemCapability<IItemHandler,@Nullable Direction> DART_POUCH_INVENTORY =
            ItemCapability.create(
                ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "dart_pouch_inventory"),
                    IItemHandler.class,
                    Direction.class
            );

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.put("dart_pouch_inventory", inventory.serializeNBT(provider));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (nbt.contains("dart_pouch_inventory")) {
            inventory.deserializeNBT(provider, nbt.getCompound("dart_pouch_inventory"));
        }
    }

    @Override
    public int getSlots() {
        return inventory.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        return inventory.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return inventory.extractItem(slot, amount, simulate);
    }

    @Override
    public int getSlotLimit(int slot) {
        return inventory.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return inventory.isItemValid(slot, stack);
    }
}
