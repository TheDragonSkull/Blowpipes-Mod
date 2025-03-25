package net.thedragonskull.blowpipemod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.network.S2CCharmingAuraParticlesPacket;

import java.util.List;

public class CharmingAuraEffect extends MobEffect {


    protected CharmingAuraEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);

        if (!pLivingEntity.level().isClientSide) {
            PacketHandler.sendToAllPlayer(new S2CCharmingAuraParticlesPacket(pLivingEntity.getId()));
        }

        List<Mob> nearbyEntities = pLivingEntity.level().getEntitiesOfClass(
                Mob.class,
                pLivingEntity.getBoundingBox().inflate(15),
                mob -> mob != pLivingEntity && mob.isAlive() && mob.getType() != EntityType.WARDEN
        );

        for (Mob mob : nearbyEntities) {
            if (mob.getTarget() == null || mob.getTarget() != pLivingEntity) {
                mob.getNavigation().moveTo(pLivingEntity, 1.0D);
                System.out.println(mob.getSpeed());
            }

        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
