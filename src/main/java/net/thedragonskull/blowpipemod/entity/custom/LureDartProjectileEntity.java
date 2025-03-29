package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.particle.ModParticles;

public class LureDartProjectileEntity extends AbstractDart{
    private int ticksSinceImpact = 0;
    private boolean isEmbedded = false;

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
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(ModEffects.CHARMING_AURA_EFFECT, 100, 0, false, false, true));
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

        if (this.level().isClientSide) {
            this.makeParticles();
        }

        if (this.isEmbedded) {
            ticksSinceImpact++;

            //Discard dart after 5 seconds
            if (ticksSinceImpact >= 100) {
                this.playSound(SoundEvents.BONE_BLOCK_BREAK, 1.0F, 1.0F);
                this.discard();
            }
        }
    }

    private void makeParticles() {
        this.level().addParticle(
                ModParticles.LURE_GLINT_PARTICLES.get(),
                this.getRandomX(1.5D),
                this.getRandomY(),
                this.getRandomZ(1.5D),
                0, 0, 0
        );
    }

    //Detect nearby mobs and make em attack the dart
    private void detectNearbyHostileMobs() {
        double detectionRange = 15.0D;

        this.level().getEntities(this, this.getBoundingBox().inflate(detectionRange), entity -> {
            return entity instanceof Mob &&
                    !(entity.getType().equals(EntityType.GIANT) || entity.getType().equals(EntityType.WARDEN));
        }).forEach(entity -> {
            if (entity instanceof Mob mob) {
                mob.getNavigation().moveTo(this.getX(), this.getY(), this.getZ(), 1.0D);
            }
        });
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        super.onInsideBlock(state);

        if (state.getBlock() == Blocks.LAVA) {
            this.discard();
        }
    }

}
