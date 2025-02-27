package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.particle.ModParticles;

import java.util.List;

public class LureDartProjectileEntity extends AbstractDart{
    private int ticksSinceImpact = 0;
    private boolean isEmbedded = false;

    private int hitCount = 0;
    private int hitCooldown = 0;


    public LureDartProjectileEntity(EntityType<? extends LureDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public LureDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.LURE_DART.get(), level, shooter);
    }

    @Override
    protected float getDamage() {
        return 1.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        if (entity instanceof Mob mob) {
            mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100, 1, false, false));

            //Detect nearby monsters
            List<Monster> nearbyMonsters = this.level().getEntitiesOfClass(
                    Monster.class,
                    mob.getBoundingBox().inflate(5),
                    monster -> monster != mob && monster.isAlive()
            );

            //Make the mob attack new mob target
            for (Monster monster : nearbyMonsters) {
                if (monster.getTarget() != mob) {
                    monster.setTarget(mob);
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.isEmbedded = true;
    }


    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide) {
            this.detectNearbyHostileMobs();
        }

        if (this.hitCooldown > 0) {
            this.hitCooldown--;
        }

        if (this.isEmbedded) {
            ticksSinceImpact++;

            //Discard dart after 10 seconds
            if (ticksSinceImpact >= 200) {
                this.playSound(SoundEvents.BONE_BLOCK_BREAK, 1.0F, 1.0F);
                this.discard();
            }

            //Delay for the particles
            if (ticksSinceImpact >= 5) {
                this.level().addParticle(
                        ModParticles.LURE_GLINT_PARTICLES.get(),
                        this.getRandomX(1.5D),
                        this.getRandomY(),
                        this.getRandomZ(1.5D),
                        0, 0, 0
                );
            }
        }
    }


    //Detect nearby monsters and make em attack the dart
    private void detectNearbyHostileMobs() {
        double detectionRange = 5.0D;

        this.level().getEntities(this, this.getBoundingBox().inflate(detectionRange), entity -> {
            return entity instanceof Monster;
        }).forEach(entity -> {
            if (entity instanceof Mob mob) {
                mob.getNavigation().moveTo(this.getX(), this.getY(), this.getZ(), 1.0D);

                if (mob.distanceTo(this) < 1.2D) {
                    this.hurt(this.damageSources().mobAttack(mob), 1.0F);
                }
            }
        });
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        Entity sourceEntity = pSource.getEntity();
        if (sourceEntity instanceof Monster) {

            if (this.hitCooldown > 0) {
                return false;
            }

            this.hitCount++;
            this.hitCooldown = 20;
            System.out.println(hitCount);

            if (this.hitCount >= 4) {
                this.playSound(SoundEvents.BONE_BLOCK_BREAK, 1.0F, 1.0F);
                this.discard();
            }

            return true;
        }

        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        super.onInsideBlock(state);

        if (state.getBlock() == Blocks.WATER) {
            this.discard();
        } else if (state.getBlock() == Blocks.LAVA) {
            this.discard();
        }
    }

}
