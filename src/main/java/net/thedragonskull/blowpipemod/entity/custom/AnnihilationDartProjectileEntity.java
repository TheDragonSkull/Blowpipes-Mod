package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.network.C2SAnnihilationDartParticlesPacket;
import net.thedragonskull.blowpipemod.network.PacketHandler;

public class AnnihilationDartProjectileEntity extends AbstractDart{

    public AnnihilationDartProjectileEntity(EntityType<? extends AnnihilationDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public AnnihilationDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.ANNIHILATION_DART.get(), level, shooter);
    }

    @Override
    protected float getDamage() {
        return 1.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.ANNIHILATION_DART.get().getDefaultInstance();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, Integer.MAX_VALUE, 5));

            Vec3 entityPos = entity.position();
            double entityHeight = entity.getBbHeight();
            PacketHandler.sendToServer(new C2SAnnihilationDartParticlesPacket(entityPos, entityHeight));

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 0.6F, 1.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();


        if (!this.isGrounded()) {
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(
                        ParticleTypes.SMOKE,
                        this.getRandomX(1.5D),
                        this.getRandomY(),
                        this.getRandomZ(1.5D),
                        0, 0, 0
                );
            }
        }
    }
}
