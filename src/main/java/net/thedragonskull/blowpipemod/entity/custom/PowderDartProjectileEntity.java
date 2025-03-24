package net.thedragonskull.blowpipemod.entity.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
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
import net.thedragonskull.blowpipemod.trigger.ModTriggers;
import org.jetbrains.annotations.Nullable;

public class PowderDartProjectileEntity extends AbstractDart{
    private int ticksSinceImpact = 0;
    private boolean isEmbedded = false;
    private boolean isExtinguished = false;

    @Nullable
    private SimpleSoundInstance fuseSoundInstance;

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
        if (!this.isExtinguished && this.fuseSoundInstance == null) {
            this.fuseSoundInstance = SimpleSoundInstance.forUI(
                    ModSounds.BOMB_FUSE.get(),
                    1.0F,
                    0.2F
            );

            System.out.println("playing sound");

            Minecraft.getInstance().getSoundManager().play(this.fuseSoundInstance);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.isExtinguished && !result.getEntity().isUnderWater()) {
            if (result.getEntity() instanceof Creeper creeper) {
                if (creeper.isPowered() && this.getOwner() instanceof ServerPlayer serverPlayer) {
                    ModTriggers.CHARGED_CREEPER.trigger(serverPlayer);
                }

                creeper.ignite();
            } else {
                explode();
                result.getEntity().setSecondsOnFire(5);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isEmbedded && !this.isExtinguished) {
            BlockState state = this.level().getBlockState(this.blockPosition());
            if (state.getBlock() == Blocks.WATER) {
                extinguishDart();
            }
        }

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

        if (this.fuseSoundInstance != null) {
            Minecraft.getInstance().getSoundManager().stop(this.fuseSoundInstance);
            this.fuseSoundInstance = null;
        }
    }

}
