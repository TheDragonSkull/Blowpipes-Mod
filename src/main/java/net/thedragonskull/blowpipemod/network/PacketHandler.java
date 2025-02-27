package net.thedragonskull.blowpipemod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(BlowPipeMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals);

    public static void register() {

        INSTANCE.messageBuilder(C2SReloadBlowpipePacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal())
                .encoder(C2SReloadBlowpipePacket::encode)
                .decoder(C2SReloadBlowpipePacket::new)
                .consumerMainThread(C2SReloadBlowpipePacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SOpenPouchMenuPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal() + 1)
                .encoder(C2SOpenPouchMenuPacket::encode)
                .decoder(C2SOpenPouchMenuPacket::new)
                .consumerMainThread(C2SOpenPouchMenuPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SHamelinTriggerPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal() + 2)
                .encoder(C2SHamelinTriggerPacket::encode)
                .decoder(C2SHamelinTriggerPacket::new)
                .consumerMainThread(C2SHamelinTriggerPacket::handle)
                .add();

    }



    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToAllPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
