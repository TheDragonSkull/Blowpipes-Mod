package net.thedragonskull.blowpipemod.entity.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.particle.ModParticles;

public class CharmingAuraArrowEntity extends AbstractArrow {

    public CharmingAuraArrowEntity(EntityType<? extends CharmingAuraArrowEntity> entityType, Level level) {
        super(entityType, level);
    }

    public CharmingAuraArrowEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.CHARMING_AURA_ARROW_ENTITY.get(), pShooter, pLevel, new ItemStack(ModItems.CHARMING_AURA_ARROW.get()), null);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ModParticles.LURE_GLINT_PARTICLES.get(),
                    this.getRandomX(1.0D),
                    this.getRandomY(),
                    this.getRandomZ(1.0D),
                    0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ModItems.CHARMING_AURA_ARROW.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        MobEffectInstance mobEffectInstance = new MobEffectInstance(ModEffects.CHARMING_AURA_EFFECT,
                50, 0, false, false, true);
        target.addEffect(mobEffectInstance, this.getEffectSource());
    }
}
