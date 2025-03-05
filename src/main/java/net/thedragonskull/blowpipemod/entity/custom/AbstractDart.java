package net.thedragonskull.blowpipemod.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.thedragonskull.blowpipemod.enchantment.ModEnchantments;
import net.thedragonskull.blowpipemod.sound.ModSounds;
import net.thedragonskull.blowpipemod.util.DartPouchUtil;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDart extends AbstractArrow {
    public Vec2 groundedOffset = new Vec2(0, 0);

    public AbstractDart(EntityType<? extends AbstractDart> entityType, Level level) {
        super(entityType, level);
    }

    public AbstractDart(EntityType<? extends AbstractDart> entityType, Level level, LivingEntity shooter) {
        super(entityType, shooter, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();

        float damage = this.getDamage();

        if (this.getOwner() instanceof LivingEntity shooter) {
            ItemStack itemStack = shooter.getItemBySlot(EquipmentSlot.MAINHAND);
            int blowPowerLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.BLOW_POWER.get(), itemStack);

            damage += blowPowerLevel;
        }

        entity.hurt(this.damageSources().generic(), damage);

        if (entity instanceof Mob mob && this.getOwner() instanceof LivingEntity owner) {
            mob.setLastHurtByMob(owner);
        }

            if (!this.level().isClientSide) {
           this.level().playSound(
                    null,
                    entity.blockPosition(),
                    ModSounds.DART_HIT.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
            );

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    public boolean isGrounded() {
        return inGround;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        this.setOnGround(true);
        this.setSoundEvent(getDefaultHitGroundSoundEvent());
        this.groundedOffset = new Vec2(this.getXRot(), this.getYRot() + 180f);
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return ModSounds.DART_HIT.get();
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent pSoundEvent) {
        super.setSoundEvent(pSoundEvent);
    }

    protected abstract float getDamage();

    @Override
    protected abstract @NotNull ItemStack getPickupItem();

    @Override
    public void playerTouch(@NotNull Player player) {
        if (!this.level().isClientSide && this.isAlive() && this.isGrounded()) {
            ItemStack dartStack = this.getPickupItem();
            boolean addedToPouch;

            if (!dartStack.isEmpty()) {
                ItemStack pouchStack = DartPouchUtil.findDartPouch(player);
                addedToPouch = DartPouchUtil.addDartToPouch(pouchStack, dartStack);

                if (!addedToPouch) {
                    if (!player.addItem(dartStack)) {
                        this.spawnAtLocation(dartStack, 0.1F);
                    }
                }

                player.take(this, 1);
                this.discard();
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void onInsideBlock(BlockState pState) {
        super.onInsideBlock(pState);

        if (pState.getBlock() == Blocks.LAVA) {
            discard();
        }
    }
}
