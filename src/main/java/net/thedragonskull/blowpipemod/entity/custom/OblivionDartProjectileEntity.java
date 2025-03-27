package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.network.C2SOblivionDartParticlesPacket;
import net.thedragonskull.blowpipemod.network.PacketHandler;
import net.thedragonskull.blowpipemod.sound.ModSounds;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;

public class OblivionDartProjectileEntity extends AbstractDart{

    public OblivionDartProjectileEntity(EntityType<? extends OblivionDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public OblivionDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.OBLIVION_DART.get(), level, shooter);
    }

    @Override
    protected float getDamage() {
        return 0.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.OBLIVION_DART.get().getDefaultInstance();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        if (entity instanceof EnderDragonPart dragonPart) {
            entity = dragonPart.parentMob;
        }



        if (entity instanceof LivingEntity livingEntity) {

            if (entity instanceof WitherBoss wither) {
                if (wither.getInvulnerableTicks() > 0) {
                    if (this.getOwner() instanceof ServerPlayer player) {
                        ModTriggers.EVAPORATE_WITHER.trigger(player);
                    }
                }
            }

            if (livingEntity instanceof ServerPlayer player) {
                if (this.getOwner() instanceof ServerPlayer shooter && shooter == player) {
                    ModTriggers.OBLIVION_SUICIDE.trigger(player);
                }
            }

            if (entity instanceof EnderDragon) {
                entity.kill();
                entity.discard();
            } else {
                entity.kill();
                livingEntity.discard();
            }

            Vec3 entityPos = entity.position();
            double entityHeight = entity.getBbHeight();
            PacketHandler.sendToServer(new C2SOblivionDartParticlesPacket(entityPos, entityHeight));

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.BOOM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
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

            for (int i = 0; i < 2; i++) {
                this.level().addParticle(
                        ParticleTypes.DRAGON_BREATH,
                        this.getRandomX(1.5D),
                        this.getRandomY(),
                        this.getRandomZ(1.5D),
                        0, 0, 0
                );
            }
        }
    }
}
