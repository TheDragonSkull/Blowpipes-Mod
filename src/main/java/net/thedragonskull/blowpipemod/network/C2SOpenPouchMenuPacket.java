package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkEvent;
import net.thedragonskull.blowpipemod.menu.DartPouchMenu;
import static net.thedragonskull.blowpipemod.util.DartPouchUtil.findDartPouch;

import java.util.function.Supplier;

public class C2SOpenPouchMenuPacket {

    public C2SOpenPouchMenuPacket() {
    }

    public C2SOpenPouchMenuPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(C2SOpenPouchMenuPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            ItemStack pouchStack = findDartPouch(player);
            if (pouchStack.isEmpty()) return; // Si no tiene el pouch, no hacemos nada

            pouchStack.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(cap -> {
                if (cap instanceof ItemStackHandler pouchInventory) {
                    player.openMenu(new SimpleMenuProvider(
                            (id, inv, p) -> new DartPouchMenu(id, inv, pouchInventory),
                            Component.literal("Dart Pouch")
                    ));
                }
            });

        });
        ctx.get().setPacketHandled(true);
    }




}
