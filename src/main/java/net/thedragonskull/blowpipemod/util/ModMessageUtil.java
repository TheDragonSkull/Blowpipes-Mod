package net.thedragonskull.blowpipemod.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.thedragonskull.blowpipemod.config.BlowPipeModConfigs;

public class ModMessageUtil {
    public static void sendMessage(Player player, String message, ChatFormatting color) {
        if (BlowPipeModConfigs.CONFIG.toggle_display_messages.get()) {
            player.displayClientMessage(Component.literal(message).withStyle(color), true);
        }
    }
}
