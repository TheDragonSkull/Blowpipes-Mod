package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import javax.annotation.Nonnull;


public record S2CCharmingAuraParticlesPacket(int entityId) implements CustomPacketPayload {

    public static final Type<S2CCharmingAuraParticlesPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "charming_aura_particles_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CCharmingAuraParticlesPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.INT,
                    S2CCharmingAuraParticlesPacket::entityId,
                    S2CCharmingAuraParticlesPacket::new);

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
