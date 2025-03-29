package net.thedragonskull.blowpipemod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
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

        INSTANCE.messageBuilder(C2SAnnihilationDartParticlesPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal() + 3)
                .encoder(C2SAnnihilationDartParticlesPacket::encode)
                .decoder(C2SAnnihilationDartParticlesPacket::new)
                .consumerMainThread(C2SAnnihilationDartParticlesPacket::handle)
                .add();

        INSTANCE.messageBuilder(C2SOblivionDartParticlesPacket.class, NetworkDirection.PLAY_TO_SERVER.ordinal() + 4)
                .encoder(C2SOblivionDartParticlesPacket::encode)
                .decoder(C2SOblivionDartParticlesPacket::new)
                .consumerMainThread(C2SOblivionDartParticlesPacket::handle)
                .add();

        INSTANCE.messageBuilder(S2CCharmingAuraParticlesPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal() + 5)
                .encoder(S2CCharmingAuraParticlesPacket::encode)
                .decoder(S2CCharmingAuraParticlesPacket::new)
                .consumerMainThread(S2CCharmingAuraParticlesPacket::handle)
                .add();

        INSTANCE.messageBuilder(S2CLingeringCharmingAuraPacket.class, NetworkDirection.PLAY_TO_CLIENT.ordinal() + 6) // NetworkSide.CLIENTBOUND
                .encoder(S2CLingeringCharmingAuraPacket::encode)
                .decoder(S2CLingeringCharmingAuraPacket::new)
                .consumerMainThread(S2CLingeringCharmingAuraPacket::handle)
                .add();

    }



    public static void sendToServer(Object msg) {
        INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static void sendToAllPlayer(Object msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }
}
