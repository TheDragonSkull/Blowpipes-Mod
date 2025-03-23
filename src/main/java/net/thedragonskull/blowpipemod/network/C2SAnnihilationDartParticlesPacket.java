package net.thedragonskull.blowpipemod.network;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SAnnihilationDartParticlesPacket {
    private final Vec3 entityPos;
    private final double entityHeight;


    public C2SAnnihilationDartParticlesPacket(Vec3 entityPos, double entityHeight) {
        this.entityPos = entityPos;
        this.entityHeight = entityHeight;
    }

    public C2SAnnihilationDartParticlesPacket(FriendlyByteBuf buf) {
        this.entityPos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.entityHeight = buf.readDouble();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(this.entityPos.x);
        buf.writeDouble(this.entityPos.y);
        buf.writeDouble(this.entityPos.z);

        buf.writeDouble(this.entityHeight);
    }

    public static void handle(C2SAnnihilationDartParticlesPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();

            if (player == null)
                return;

            ServerLevel level = player.serverLevel();
            double centerY = msg.entityPos.y + msg.entityHeight / 2.0;

            level.sendParticles(ParticleTypes.SMOKE, msg.entityPos.x, centerY, msg.entityPos.z,
                    500, 0.5, 0.5, 0.5, 0.1);

        });
        ctx.get().setPacketHandled(true);
    }

}
