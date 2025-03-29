package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;

import java.util.function.Supplier;

import static net.thedragonskull.blowpipemod.trigger.ModTriggers.BLOWPIPE_HAMELIN;

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
                BLOWPIPE_HAMELIN.get().trigger(serverPlayer);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
