package net.thedragonskull.blowpipemod.menu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.thedragonskull.blowpipemod.capabilities.DartPouchCapabilityProvider;
import net.thedragonskull.blowpipemod.component.ModDataComponents;
import net.thedragonskull.blowpipemod.item.custom.DartItem;
import org.jetbrains.annotations.NotNull;

import static net.thedragonskull.blowpipemod.capabilities.DartPouchCapabilityProvider.DART_POUCH_INVENTORY;
import static net.thedragonskull.blowpipemod.util.DartPouchUtil.findDartPouch;

public class DartPouchMenu extends AbstractContainerMenu {
    private final ItemStackHandler pouchContainer;
    private final Player player;

    public DartPouchMenu(int containerId, Inventory inv) {
        this(containerId, inv, new ItemStackHandler(6));
    }

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

        ItemStack pouch = findDartPouch(player);
        var cap = pouch.getCapability(DART_POUCH_INVENTORY, null);

        if (cap != null) {
            CompoundTag tag = new CompoundTag();
            tag.put("dart_pouch_inventory", ((DartPouchCapabilityProvider) cap).serializeNBT(null));
            pouch.set(ModDataComponents.INVENTORY.get(), tag);
        }

        playPouchClose(player);
    }



    private void playPouchClose(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

}
