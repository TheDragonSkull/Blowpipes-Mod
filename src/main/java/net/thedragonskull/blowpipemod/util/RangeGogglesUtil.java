package net.thedragonskull.blowpipemod.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.item.custom.RangeGoggles;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class RangeGogglesUtil {

    // Checks for goggles in the Curio slot
    public static boolean hasGogglesInCurioSlot(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .map(handler -> {
                    ICurioStacksHandler slotInventory = handler.getCurios().get("01_range_goggles");
                    if (slotInventory != null && slotInventory.getSlots() > 0) {
                        ItemStack stack = slotInventory.getStacks().getStackInSlot(0);
                        return !stack.isEmpty() && stack.getItem() instanceof RangeGoggles;
                    }
                    return false;
                }).orElse(false);
    }

    // Checks for goggles in the players head slot
    public static boolean hasGogglesInHeadSlot(Player player) {
        ItemStack helmet = player.getInventory().getArmor(3);
        return !helmet.isEmpty() && helmet.getItem() instanceof RangeGoggles;
    }

    // Checks if the goggles are already equipped in any slot
    public static boolean hasGogglesEquipped(Player player) {
        return hasGogglesInCurioSlot(player) || hasGogglesInHeadSlot(player);
    }


}
