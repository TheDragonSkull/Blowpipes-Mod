package net.thedragonskull.blowpipemod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.sound.ModSounds;

public class PowderDartProjectileEntity extends AbstractDart{
    private int ticksSinceImpact = 0;
    private boolean isEmbedded = false;
    private boolean isExtinguished = false;

    public PowderDartProjectileEntity(EntityType<? extends PowderDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PowderDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.POWDER_DART.get(), level, shooter);
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
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.isEmbedded = true;

        BlockPos pos = result.getBlockPos();
        Level level = this.level();

        //Ignites TNT
        if (level.getBlockState(pos).is(Blocks.TNT)) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            PrimedTnt tntEntity = new PrimedTnt(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, (LivingEntity) this.getOwner());
            level.addFreshEntity(tntEntity);
            tntEntity.setFuse(60);

            this.discard();
        }
    }

    private void playImpactSound() {
        if (!this.isExtinguished) {
            this.level().playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    ModSounds.BOMB_FUSE.get(),
                    SoundSource.PLAYERS,
                    0.5F,
                    1.0F
            );
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        explode();
        result.getEntity().setSecondsOnFire(5);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isEmbedded && ticksSinceImpact == 0) {
            playImpactSound();
        }

        if (this.isEmbedded && !this.isRemoved() && !this.isExtinguished) {
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(
                        ParticleTypes.FALLING_LAVA,
                        this.getX() + random.nextDouble() * 0.5 - 0.1,
                        this.getY() + random.nextDouble() * 0.5 - 0.1,
                        this.getZ() + random.nextDouble() * 0.5 - 0.1,
                        random.nextDouble() * 0.02 - 0.01,
                        random.nextDouble() * 0.02,
                        random.nextDouble() * 0.02 - 0.01
                );
            }
        }

        if (this.isEmbedded && !this.isExtinguished) {
            ticksSinceImpact++;

            if (ticksSinceImpact >= 60) {
                this.explode();
            }
        }
    }

    @Override
    protected void onInsideBlock(BlockState state) {
        super.onInsideBlock(state);

        if (!this.isExtinguished) {
            if (state.getBlock() == Blocks.WATER) {
                extinguishDart();
            } else if (state.getBlock() == Blocks.LAVA) {
                explode();
            }
        }
    }

    private void explode() {
        this.level().explode(
                this,
                this.getX(), this.getY(), this.getZ(),
                2.0F,
                Level.ExplosionInteraction.TNT
        );

        this.discard();
    }

    private void extinguishDart() {
        if (this.isExtinguished) return;

        this.isExtinguished = true;

        if (!this.level().isClientSide) {
            this.level().playSound(
                    null,
                    this.blockPosition(),
                    SoundEvents.GENERIC_EXTINGUISH_FIRE,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );
        }
    }



}
