package net.thedragonskull.blowpipemod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;
import net.thedragonskull.blowpipemod.particle.ModParticles;

import java.util.function.Supplier;

public class S2CCharmingAuraParticlesPacket {
    private final int entityId;

    public S2CCharmingAuraParticlesPacket(int entityId) {
        this.entityId = entityId;
    }

    public S2CCharmingAuraParticlesPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
    }

    public static void handle(S2CCharmingAuraParticlesPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null) {
                Entity entity = mc.level.getEntity(msg.entityId);
                if (entity instanceof LivingEntity livingEntity) {
                    mc.level.addParticle(ModParticles.LURE_GLINT_PARTICLES.get(),
                            livingEntity.getRandomX(1.0D),
                            livingEntity.getRandomY(),
                            livingEntity.getRandomZ(1.0D),
                            0, 0, 0);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
