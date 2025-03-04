package net.thedragonskull.blowpipemod.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.thedragonskull.blowpipemod.client.gui.SelectedDartOverlay;
import net.thedragonskull.blowpipemod.item.ModItems;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.List;

import static net.thedragonskull.blowpipemod.client.gui.SelectedDartOverlay.currentDartIndex;

public class DartPouchUtil {

    //Change the selected slot based on the key pressed
    public static void updateDartIndex(boolean isNext) {
        if (isNext) {
            currentDartIndex = (currentDartIndex + 1) % 6; //Next slot (circular)
        } else {
            currentDartIndex = (currentDartIndex - 1 + 6) % 6; //Previous slot (circular)
        }
    }

    //Checks for a pouch in the Curio slot
    public static ItemStack findDartPouch(Player player) {
        return CuriosApi.getCuriosInventory(player).map(handler -> {
            ICurioStacksHandler slotInventory = handler.getCurios().get("dart_pouch");
            if (slotInventory != null && slotInventory.getSlots() > 0) {
                return slotInventory.getStacks().getStackInSlot(0);
            }
            return ItemStack.EMPTY;
        }).orElse(ItemStack.EMPTY);
    }

    //Checks for a dart giving priority to the offhand, then inside the pouch
    public static ItemStack findAvailableDart(Player player) {
        ItemStack offHand = player.getOffhandItem();
        if (isDart(offHand)) {
            return offHand;
        }

        ItemStack pouchStack = findDartPouch(player);
        if (!pouchStack.isEmpty()) {
            return pouchStack.getCapability(ForgeCapabilities.ITEM_HANDLER).map(cap -> {
                if (cap instanceof ItemStackHandler handler) {
                    int index = SelectedDartOverlay.currentDartIndex;
                    if (index >= 0 && index < handler.getSlots()) {
                        ItemStack pouchDart = handler.getStackInSlot(index);
                        if (isDart(pouchDart)) {
                            return pouchDart;
                        }
                    }
                }
                return ItemStack.EMPTY;
            }).orElse(ItemStack.EMPTY);
        }

        return ItemStack.EMPTY;
    }

    //Get all the darts in the pouch
    public static List<ItemStack> getDartsFromPouch(ItemStack pouchStack) {
        List<ItemStack> darts = new ArrayList<>();

        pouchStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
            if (cap instanceof ItemStackHandler handler) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack storedItem = handler.getStackInSlot(i);
                    darts.add(storedItem);

                }
            }
        });

        return darts;
    }

    //Adds a dart to the pouch
    public static boolean addDartToPouch(ItemStack pouchStack, ItemStack dart) {
        if (pouchStack.isEmpty() || dart.isEmpty()) return false;

        return pouchStack.getCapability(ForgeCapabilities.ITEM_HANDLER).map(cap -> {
            if (cap instanceof ItemStackHandler handler) {
                for (int i = 0; i < handler.getSlots(); i++) {
                    ItemStack storedDart = handler.getStackInSlot(i);

                    if (ItemStack.isSameItem(storedDart, dart) && storedDart.getCount() < storedDart.getMaxStackSize()) {
                        storedDart.grow(1);
                        return true;
                    }
                }
            }
            return false;
        }).orElse(false);
    }


    //List of accepted darts
    public static boolean isDart(ItemStack stack) {
        return stack.is(ModItems.DART_BASE.get()) ||
                stack.is(ModItems.POISON_DART.get()) ||
                stack.is(ModItems.POWDER_DART.get()) ||
                stack.is(ModItems.LURE_DART.get());
    }

}
