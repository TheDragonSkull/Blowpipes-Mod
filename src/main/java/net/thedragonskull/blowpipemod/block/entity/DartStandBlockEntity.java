package net.thedragonskull.blowpipemod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.item.custom.DartItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DartStandBlockEntity extends BlockEntity {
    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public DartStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DART_STAND_BE.get(), pPos, pBlockState);
    }

    public void interact(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        int selectedSlot = player.getInventory().selected;

        if (level == null)
            return;

            // Put dart
        if (inventory.getStackInSlot(0).isEmpty() && isDart(heldItem)) {
            ItemStack stack = heldItem.copy();
            stack.setCount(1);
            inventory.setStackInSlot(0, stack);
            heldItem.shrink(1);
            level.playSound(null, this.getBlockPos(),
                    SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1.0f, 2.0f);

            // Retrieve dart
        } else if (!inventory.getStackInSlot(0).isEmpty() && heldItem.isEmpty()) {
            player.getInventory().setItem(selectedSlot, inventory.getStackInSlot(0));
            inventory.setStackInSlot(0, ItemStack.EMPTY);
            level.playSound(null, this.getBlockPos(),
                    SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundSource.BLOCKS, 1.0f, 2.0f);

            // Replace dart
        } else if (!inventory.getStackInSlot(0).isEmpty() && heldItem.getItem() instanceof DartItem) {
            player.getInventory().setItem(selectedSlot, inventory.getStackInSlot(0));
            ItemStack stack = heldItem.copy();
            stack.setCount(1);
            inventory.setStackInSlot(0, stack);
            heldItem.shrink(1);
            level.playSound(null, this.getBlockPos(),
                    SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.BLOCKS, 1.0f, 2.0f);
        }
    }


    private boolean isDart(ItemStack stack) {
        return stack.getItem() instanceof DartItem;
    }

    public ItemStack getStoredDart() {
        return inventory.getStackInSlot(0);
    }

    public void tick() {
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("StoredDart", inventory.serializeNBT(pRegistries));
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inventory.deserializeNBT(pRegistries, pTag.getCompound("StoredDart"));
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
