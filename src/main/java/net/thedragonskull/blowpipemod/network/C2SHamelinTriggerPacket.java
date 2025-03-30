package net.thedragonskull.blowpipemod.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.thedragonskull.blowpipemod.BlowPipeMod;


public record C2SHamelinTriggerPacket() implements CustomPacketPayload{

    public static final CustomPacketPayload.Type<C2SHamelinTriggerPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BlowPipeMod.MOD_ID, "hamelin_trigger_packet"));

    public static final StreamCodec<RegistryFriendlyByteBuf, C2SHamelinTriggerPacket> STREAM_CODEC = StreamCodec.unit(
            new C2SHamelinTriggerPacket()
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
