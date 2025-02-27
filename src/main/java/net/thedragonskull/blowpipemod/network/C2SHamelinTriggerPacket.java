package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;

import java.util.function.Supplier;

public class C2SHamelinTriggerPacket {

    public C2SHamelinTriggerPacket() {
    }

    public C2SHamelinTriggerPacket(FriendlyByteBuf buf) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public static void handle(C2SHamelinTriggerPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.get().getSender();
            if (serverPlayer != null) {
                ModTriggers.BLOWPIPE_HAMELIN.trigger(serverPlayer);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
