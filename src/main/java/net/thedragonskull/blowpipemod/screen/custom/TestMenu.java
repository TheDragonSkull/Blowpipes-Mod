package net.thedragonskull.blowpipemod.screen.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.thedragonskull.blowpipemod.item.custom.DartItem;
import net.thedragonskull.blowpipemod.item.custom.InvTestItem;
import net.thedragonskull.blowpipemod.screen.ModMenuTypes;
import org.jetbrains.annotations.NotNull;

public class TestMenu extends AbstractContainerMenu {
    private final ItemStackHandler handler;

    public TestMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(6));
    }

    public TestMenu(int id, Inventory playerInventory, ItemStackHandler handler) {
        super(ModMenuTypes.DART_POUCH_MENU.get(), id);
        this.handler = handler;

        createDartPouchInventory();
        createPlayerInventory(playerInventory);
        createPlayerHotbar(playerInventory);

    }

    //Only can store darts
    private void createDartPouchInventory() {
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 3; column++) {
                this.addSlot(new SlotItemHandler(handler, row * 3 + column, 62 + (column * 18), 33 + (row * 18)) {
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
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        ItemStack stack = player.getMainHandItem();
        IItemHandler inventory = stack.getCapability(Capabilities.ItemHandler.ITEM);

        CompoundTag tag = new CompoundTag();

    }
}
