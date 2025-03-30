package net.thedragonskull.blowpipemod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.thedragonskull.blowpipemod.particle.ModParticles;

public class BlowpipeClientPayloadHandler {

    private static final BlowpipeClientPayloadHandler INSTANCE = new BlowpipeClientPayloadHandler();

    public static BlowpipeClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handleCharmingAuraParticles(final S2CCharmingAuraParticlesPacket data, final IPayloadContext ctx) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            Entity entity = mc.level.getEntity(data.entityId());
            if (entity instanceof LivingEntity livingEntity) {
                mc.level.addParticle(ModParticles.LURE_GLINT_PARTICLES.get(),
                        livingEntity.getRandomX(1.0D),
                        livingEntity.getRandomY(),
                        livingEntity.getRandomZ(1.0D),
                        0, 0, 0);
            }
        }
    }

    public void handleLingeringCharmingAura(final S2CLingeringCharmingAuraPacket data, final IPayloadContext ctx) {
        Level level = Minecraft.getInstance().level;
        if (level != null) {
            for (int i = 0; i < 8; ++i) {
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), data.x(), data.y(), data.z(),
                        (Math.random() - 0.5) * 0.15,
                        Math.random() * 0.2,
                        (Math.random() - 0.5) * 0.15);
            }
            level.playLocalSound(data.x(), data.y(), data.z(), SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
        }
    }


}
