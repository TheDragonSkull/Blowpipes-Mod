package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;

public class PoisonDartProjectileEntity extends AbstractDart {

    public PoisonDartProjectileEntity(EntityType<? extends PoisonDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public PoisonDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.POISON_DART.get(), level, shooter);
    }

    @Override
    protected float getDamage() {
        return 1.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.POISON_DART.get().getDefaultInstance();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1));
        }
    }

}
