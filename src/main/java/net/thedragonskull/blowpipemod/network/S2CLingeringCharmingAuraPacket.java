package net.thedragonskull.blowpipemod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CLingeringCharmingAuraPacket {
    private final double x, y, z;

    public S2CLingeringCharmingAuraPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public S2CLingeringCharmingAuraPacket(FriendlyByteBuf buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level != null) {
                for (int i = 0; i < 8; ++i) {
                    level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), x, y, z,
                            (Math.random() - 0.5) * 0.15,
                            Math.random() * 0.2,
                            (Math.random() - 0.5) * 0.15);
                }
                level.playLocalSound(x, y, z, SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
            }
        });
        context.setPacketHandled(true);
    }
}
