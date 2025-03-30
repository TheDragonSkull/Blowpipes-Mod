package net.thedragonskull.blowpipemod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.component.ModDataComponents;

public class ReloadBlowpipeUtil {
    public static void unloadBlowpipe(ServerPlayer player, ItemStack mainHand) {
        if (mainHand.has(ModDataComponents.DART)) {
            ItemStack dart = mainHand.get(ModDataComponents.DART);

            if (dart == null || dart.isEmpty()) {
                mainHand.remove(ModDataComponents.DART);
                return;
            }

            dart.setCount(1);

            ItemStack pouchStack = DartPouchUtil.findDartPouch(player);

            if (pouchStack.isEmpty() || !DartPouchUtil.addDartToPouch(pouchStack, dart)) {
                if (!player.isCreative() && !player.getInventory().add(dart)) {
                    player.drop(dart, false);
                }
            }
        }

        mainHand.set(ModDataComponents.LOADED.get(), false);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 1.0F, 1.0F);
        sendMessage(player, "¡Blowpipe unloaded! ❌", ChatFormatting.RED);
        mainHand.remove(ModDataComponents.DART.get());
        mainHand.remove(ModDataComponents.DART_TYPE.get());
    }

    public static void loadBlowpipe(ServerPlayer player, ItemStack mainHand) {
        ItemStack selectedDart = DartPouchUtil.findAvailableDart(player);

        if (!selectedDart.isEmpty()) {
            BlowpipeUtil.loadBlowpipe(mainHand, selectedDart, player);
            mainHand.set(ModDataComponents.LOADED.get(), true);
            sendMessage(player, "Blowpipe loaded! ✅", ChatFormatting.GREEN);
        } else {
            System.out.println("[DEBUG] No se encontró ningún dardo disponible para recargar.");
            sendMessage(player, "¡No dart selected! ❌", ChatFormatting.DARK_RED);
        }
    }

    private static void sendMessage(ServerPlayer player, String message, ChatFormatting color) {
        player.sendSystemMessage(Component.literal(message).withStyle(color));
    }
}
