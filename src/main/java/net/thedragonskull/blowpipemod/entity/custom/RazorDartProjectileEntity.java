package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import org.jetbrains.annotations.NotNull;

public class RazorDartProjectileEntity extends AbstractDart{

    public RazorDartProjectileEntity(EntityType<? extends RazorDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public RazorDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.RAZOR_DART.get(), level, shooter);
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
    protected void onHitBlock(@NotNull BlockHitResult result) {

        this.discard();
        this.setSoundEvent(SoundEvents.TRIDENT_HIT);

        super.onHitBlock(result);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(ModEffects.BLEED_EFFECT.get(), 100, 0));
        }
    }

}
