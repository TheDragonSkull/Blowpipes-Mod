package net.thedragonskull.blowpipemod.menu;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.thedragonskull.blowpipemod.item.custom.DartItem;
import org.jetbrains.annotations.NotNull;
import static net.thedragonskull.blowpipemod.util.DartPouchUtil.findDartPouch;

public class DartPouchMenu extends AbstractContainerMenu {
    private final ItemStackHandler pouchContainer;
    private final Player player;

    public DartPouchMenu(int id, Inventory playerInventory, ItemStackHandler pouchInventory) {
        super(ModMenuTypes.DART_POUCH_MENU.get(), id);
        this.pouchContainer = pouchInventory;
        this.player = playerInventory.player;

        createDartPouchInventory(pouchInventory);
        createPlayerInventory(playerInventory);
        createPlayerHotbar(playerInventory);
    }


    //Only can store darts
    private void createDartPouchInventory(ItemStackHandler pouchInventory) {
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 3; column++) {
                this.addSlot(new SlotItemHandler(pouchInventory, row * 3 + column, 62 + (column * 18), 33 + (row * 18)) {
                    @Override
                    public boolean mayPlace(@NotNull ItemStack stack) {
                        return stack.getItem() instanceof DartItem;
                    }
                });
            }
        }
    }

    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv,
                        9 + column + (row * 9),
                        8 + (column * 18),
                        84 + (row * 18)));
            }
        }
    }

    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv,
                    column,
                    8 + (column * 18),
                    142));
        }
    }


    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack returnStack = ItemStack.EMPTY;
        Slot fromSlot = this.slots.get(pIndex);

        if (fromSlot.hasItem()) {
            ItemStack slotStack = fromSlot.getItem();
            returnStack = slotStack.copy();

            if (pIndex < 6) { //In the pouch inventory
                if (!this.moveItemStackTo(slotStack, 33, 42, false)) { // Move to hotbar if possible
                    if (!this.moveItemStackTo(slotStack, 6, 32, false)) { // Move to player if possible
                        return ItemStack.EMPTY;
                    }
                }
            } else { //In the hotbar/player inv
                if (!this.moveItemStackTo(slotStack, 0, 6, false)) // Move to pouch
                    return ItemStack.EMPTY;

                if (slotStack.isEmpty()) {
                    fromSlot.set(ItemStack.EMPTY);
                } else {
                    fromSlot.setChanged();
                }
            }

        }
        return returnStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        System.out.println("[DEBUG] Menú cerrado por: " + player.getName().getString());

        ItemStack pouch = findDartPouch(player);
        pouch.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
            if (cap instanceof ItemStackHandler handler) {
                CompoundTag tag = pouch.getOrCreateTag();
                tag.put("dart_pouch_inventory", handler.serializeNBT());
                pouch.setTag(tag);
            }
        });

        playPouchClose(player);
    }

    private void playPouchClose(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

}
