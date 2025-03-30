package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import javax.annotation.Nonnull;

public record S2CLingeringCharmingAuraPacket(double x, double z, double y) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<S2CLingeringCharmingAuraPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "lingering_charming_aura_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, S2CLingeringCharmingAuraPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE, S2CLingeringCharmingAuraPacket::x,
                    ByteBufCodecs.DOUBLE, S2CLingeringCharmingAuraPacket::z,
                    ByteBufCodecs.DOUBLE, S2CLingeringCharmingAuraPacket::y,
                    S2CLingeringCharmingAuraPacket::new);

    @Nonnull
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
