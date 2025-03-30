package net.thedragonskull.blowpipemod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.item.custom.BlowPipe;
import net.thedragonskull.blowpipemod.util.BlowpipeUtil;
import net.thedragonskull.blowpipemod.util.DartPouchUtil;

import java.util.function.Supplier;

import static net.thedragonskull.blowpipemod.util.ModMessageUtil.sendMessage;

public record C2SReloadBlowpipePacket() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<C2SReloadBlowpipePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "reload_blowpipe_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SReloadBlowpipePacket> STREAM_CODEC = StreamCodec.unit(
            new C2SReloadBlowpipePacket()
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}

/*    public void encode(FriendlyByteBuf buf) {
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
                            if (!player.isCreative() && !player.getInventory().add(dart)) {
                                player.drop(dart, false);
                            }

                        }
                    }

                    mainHand.getOrCreateTag().putBoolean("loaded", false);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BRUSH_GENERIC, SoundSource.PLAYERS, 1.0F, 1.0F);
                    sendMessage(player, "¡Blowpipe unloaded! ❌", ChatFormatting.RED);
                    tag.remove("Dart");
                    tag.remove("dart_type");
                    return;
                }

                ItemStack selectedDart = DartPouchUtil.findAvailableDart(player);

                if (!selectedDart.isEmpty()) {

                    BlowpipeUtil.loadBlowpipe(mainHand, selectedDart, player);
                    mainHand.getOrCreateTag().putBoolean("loaded", true);

                    sendMessage(player, "Blowpipe loaded! ✅", ChatFormatting.GREEN);
                } else {
                    System.out.println("[DEBUG] No se encontró ningún dardo disponible para recargar.");
                    sendMessage(player, "¡No dart selected! ❌", ChatFormatting.DARK_RED);
                }
            }
        });
        context.setPacketHandled(true);
    }*/
