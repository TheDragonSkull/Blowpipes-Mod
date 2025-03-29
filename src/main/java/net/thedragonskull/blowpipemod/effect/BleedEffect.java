package net.thedragonskull.blowpipemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedEffect extends MobEffect {
    public BleedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            float maxHealth = entity.getHealth();
            float damage = maxHealth * 0.1F;

            if (damage < 0.5F) {
                damage = 0.5F;
            }

            entity.hurt(entity.damageSources().magic(), damage);
        }
        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int interval = 20 >> amplifier;

        if (interval > 0) {
            return duration % interval == 0;
        } else {
            return true;
        }
    }

}
