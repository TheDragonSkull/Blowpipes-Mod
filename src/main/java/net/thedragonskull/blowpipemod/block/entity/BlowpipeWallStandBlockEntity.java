package net.thedragonskull.blowpipemod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlowpipeWallStandBlockEntity extends BlockEntity {
    private final ItemStackHandler blowpipeHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public BlowpipeWallStandBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.BLOWPIPE_WALL_STAND_BE.get(), pPos, pBlockState);
    }

    public void interact(Player player, InteractionHand hand, int slotIndex) {
        if (slotIndex < 0 || slotIndex > 2) return;
        if (level == null) return;

        ItemStack heldItem = player.getItemInHand(hand);
        int selectedSlot = player.getInventory().selected;
        ItemStack storedItem = blowpipeHandler.getStackInSlot(slotIndex);

        // Put blowpipe
        if (storedItem.isEmpty() && isBlowpipe(heldItem)) {
            ItemStack stack = heldItem.copy();
            stack.setCount(1);
            blowpipeHandler.setStackInSlot(slotIndex, stack);
            heldItem.shrink(1);
            level.playSound(null, this.getBlockPos(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.BLOCKS);

            // Retrieve blowpipe
        } else if (!storedItem.isEmpty() && heldItem.isEmpty()) {
            player.getInventory().setItem(selectedSlot, storedItem);
            blowpipeHandler.setStackInSlot(slotIndex, ItemStack.EMPTY);
            level.playSound(null, this.getBlockPos(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundSource.BLOCKS);

            // Replace blowpipe
        } else if (!storedItem.isEmpty() && isBlowpipe(heldItem)) {
            player.getInventory().setItem(selectedSlot, storedItem);
            ItemStack stack = heldItem.copy();
            stack.setCount(1);
            blowpipeHandler.setStackInSlot(slotIndex, stack);
            heldItem.shrink(1);
            level.playSound(null, this.getBlockPos(), SoundEvents.BAMBOO_WOOD_BUTTON_CLICK_ON, SoundSource.BLOCKS);
        }

    }


    private boolean isBlowpipe(ItemStack stack) {
        return stack.getItem() instanceof BlowPipe;
    }

    public ItemStack getStoredBlowpipes(int slotIndex) {
        return slotIndex >= 0 && slotIndex < 3 ? blowpipeHandler.getStackInSlot(slotIndex) : ItemStack.EMPTY;
    }

    public void tick() {
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        blowpipeHandler.deserializeNBT(pTag.getCompound("StoredBlowpipes"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("StoredBlowpipes", blowpipeHandler.serializeNBT());
        super.saveAdditional(pTag);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
}
