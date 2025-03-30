package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import org.joml.Vector3f;

public record C2SAnnihilationDartParticlesPacket(Vector3f entityPos, double entityHeight) implements CustomPacketPayload{

    public static final CustomPacketPayload.Type<C2SAnnihilationDartParticlesPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "annihilation_dart_particles_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SAnnihilationDartParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F,
            C2SAnnihilationDartParticlesPacket::entityPos,
            ByteBufCodecs.DOUBLE,
            C2SAnnihilationDartParticlesPacket::entityHeight,
            C2SAnnihilationDartParticlesPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
