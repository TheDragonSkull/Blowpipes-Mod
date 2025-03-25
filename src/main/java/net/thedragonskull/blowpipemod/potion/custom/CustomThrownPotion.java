package net.thedragonskull.blowpipemod.potion.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.network.S2CCharmingAuraParticlesPacket;

import java.util.List;

public class CustomThrownPotion extends ThrownPotion {

    public CustomThrownPotion(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
    }

    @Override
    protected void onHit(HitResult pResult) {
        //super.onHit(pResult);

        if (!this.level().isClientSide) {
            if (this.getItem().is(ModItems.CHARMING_AURA_SPLASH_POTION.get())) {
                this.applyCustomSplash(pResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult) pResult).getEntity() : null);

                this.level().levelEvent(4000, this.blockPosition(), 0x11ff00);
                this.discard();
            }
        }
    }

    private void applyCustomSplash(@javax.annotation.Nullable Entity pTarget) {
        AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, aabb);

        if (!list.isEmpty()) {
            Entity entity = this.getEffectSource();

            for(LivingEntity livingentity : list) {
                if (livingentity.isAffectedByPotions()) {
                    double d0 = this.distanceToSqr(livingentity);
                    if (d0 < 16.0D) {
                        MobEffectInstance charmingAuraEffect = new MobEffectInstance(ModEffects.CHARMING_AURA_EFFECT.get(),
                                200, 0, false,false, true);
                        livingentity.addEffect(charmingAuraEffect, entity);

                        PacketHandler.sendToServer(new S2CCharmingAuraParticlesPacket(livingentity.getId()));
                    }
                }
            }
        }
    }

}
