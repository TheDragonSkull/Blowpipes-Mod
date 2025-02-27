package net.thedragonskull.blowpipemod.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DartPouchCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final ItemStackHandler inventory = new ItemStackHandler(6); // 6 slots
    private final LazyOptional<IItemHandler> instance = LazyOptional.of(() -> inventory);

    public DartPouchCapabilityProvider(ItemStack stack) {
        if (stack.hasTag()) {
            deserializeNBT(stack.getOrCreateTag().getCompound("dart_pouch_inventory"));
        }
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return instance.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.put("dart_pouch_inventory", inventory.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("dart_pouch_inventory")) {
            inventory.deserializeNBT(nbt.getCompound("dart_pouch_inventory"));
        }
    }
}
