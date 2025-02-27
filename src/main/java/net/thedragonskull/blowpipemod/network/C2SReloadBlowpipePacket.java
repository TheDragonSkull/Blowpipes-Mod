package net.thedragonskull.blowpipemod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;
import net.thedragonskull.blowpipemod.client.gui.SelectedDartOverlay;
import net.thedragonskull.blowpipemod.client.handler.ClientForgeHandler;
import net.thedragonskull.blowpipemod.entity.custom.AbstractDart;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;
import net.thedragonskull.blowpipemod.item.custom.DartPouchItem;
import net.thedragonskull.blowpipemod.util.DartPouchUtil;

import static net.thedragonskull.blowpipemod.util.DartPouchUtil.findDartPouch;
import static net.thedragonskull.blowpipemod.util.DartPouchUtil.isDart;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class C2SReloadBlowpipePacket {
    public static ItemStack darts;

    public C2SReloadBlowpipePacket() {
    }

    public C2SReloadBlowpipePacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {

                ItemStack mainHand = player.getMainHandItem();
                ItemStack offHand = player.getOffhandItem();

                if (!(mainHand.getItem() instanceof BlowPipe blowpipe) || mainHand.getOrCreateTag().getBoolean("loaded")) {
                    return;
                }

                ItemStack selectedDart = DartPouchUtil.findAvailableDart(player);

                if (!selectedDart.isEmpty()) {

                    blowpipe.loadBlowpipe(mainHand, selectedDart, player);
                    mainHand.getOrCreateTag().putBoolean("loaded", true);

                    player.displayClientMessage(Component.literal("¡Blowpipe loaded! ✅").withStyle(ChatFormatting.GREEN), true);
                } else {
                    System.out.println("[DEBUG] No se encontró ningún dardo disponible para recargar.");
                    player.displayClientMessage(Component.literal("¡No dart selected! ❌").withStyle(ChatFormatting.DARK_RED), true);
                }
            }
        });
        context.setPacketHandled(true);
    }

}
