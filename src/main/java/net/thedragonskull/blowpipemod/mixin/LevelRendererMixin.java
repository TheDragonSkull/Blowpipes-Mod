package net.thedragonskull.blowpipemod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.thedragonskull.blowpipemod.particle.ModParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow public abstract void addParticle(ParticleOptions pOptions, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed);

    @Shadow @Nullable private ClientLevel level;

    @Shadow @Nullable protected abstract Particle addParticleInternal(ParticleOptions pOptions, boolean pForce, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed);

    @Inject(method = "levelEvent", at = @At("HEAD"), cancellable = true)
    private void onCustomPotionEvent(int pType, BlockPos pPos, int pData, CallbackInfo ci) {
        RandomSource randomSource = RandomSource.create();

        if (pType == 4000) {
            Vec3 vec3 = Vec3.atBottomCenterOf(pPos);

            for(int i = 0; i < 8; ++i) {
                this.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPLASH_POTION)), vec3.x, vec3.y, vec3.z,
                        randomSource.nextGaussian() * 0.15D,
                        randomSource.nextDouble() * 0.2D,
                        randomSource.nextGaussian() * 0.15D);
            }

            if (level == null) return;

            for (int i = 0; i < 100; ++i) {
                double d13 = randomSource.nextDouble() * 4.0D;
                double d19 = randomSource.nextDouble() * Math.PI * 2.0D;
                double d25 = Math.cos(d19) * d13;
                double d30 = 0.01D + randomSource.nextDouble() * 0.5D;
                double d31 = Math.sin(d19) * d13;
                Particle particle1 = this.addParticleInternal(ModParticles.LURE_GLINT_PARTICLES.get(), ModParticles.LURE_GLINT_PARTICLES.get().getType().getOverrideLimiter(),
                        vec3.x + d25 * 0.1D, vec3.y + 0.3D, vec3.z + d31 * 0.1D, d25, d30, d31);

                if (particle1 != null) {
                    particle1.setPower((float)d13);
                }

            }

            Minecraft.getInstance().level.playLocalSound(
                    pPos, SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F,
                    randomSource.nextFloat() * 0.1F + 0.9F, false
            );

            ci.cancel();
        }
    }

}
