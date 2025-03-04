package net.thedragonskull.blowpipemod.network;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.thedragonskull.blowpipemod.util.BlowpipeUtil;
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

                if (!(mainHand.getItem() instanceof BlowPipe blowpipe)) {
                    return;
                }

                CompoundTag tag = mainHand.getOrCreateTag();
                boolean isLoaded = tag.getBoolean("loaded");
                if (isLoaded) {

                    if (tag.contains("Dart")) {
                        ItemStack dart = ItemStack.of(tag.getCompound("Dart"));
                        dart.setCount(1);

                        ItemStack pouchStack = DartPouchUtil.findDartPouch(player);

                        if (!pouchStack.isEmpty() && DartPouchUtil.addDartToPouch(pouchStack, dart)) {
                        } else {

                            if (!player.getInventory().add(dart)) {
                                player.drop(dart, false);
                            }

                        }
                    }

                    mainHand.getOrCreateTag().putBoolean("loaded", false);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.displayClientMessage(Component.literal("¡Blowpipe unloaded! ❌").withStyle(ChatFormatting.RED), true);
                    return;
                }

                ItemStack selectedDart = DartPouchUtil.findAvailableDart(player);

                if (!selectedDart.isEmpty()) {

                    BlowpipeUtil.loadBlowpipe(mainHand, selectedDart, player);
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
