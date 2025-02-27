package net.thedragonskull.blowpipemod.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class LureGlintParticles extends TextureSheetParticle {
    protected LureGlintParticles(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.8F;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.quadSize *= 1.0F;
        this.lifetime = 5;
        this.setSpriteFromAge(spriteSet);

        Random random = new Random();
        int dominantColor = random.nextInt(3);

        this.rCol = dominantColor == 0 ? 1.0f : Mth.clamp(random.nextFloat(), 0.0f, 1.0f);
        this.gCol = dominantColor == 1 ? 1.0f : Mth.clamp(random.nextFloat(), 0.0f, 1.0f);
        this.bCol = dominantColor == 2 ? 1.0f : Mth.clamp(random.nextFloat(), 0.0f, 1.0f);

    }

    public int getLightColor(float pPartialTick) {
        return 0xF000F0;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)lifetime) * age + 1);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new LureGlintParticles(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
