package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;

public class IronHeadDartProjectileEntity extends AbstractDart {

    public IronHeadDartProjectileEntity(EntityType<? extends IronHeadDartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IronHeadDartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.IRON_HEAD_DART.get(), level, shooter);
    }

    @Override
    protected float getDamage() {
        return 2.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.IRON_HEAD_DART.get().getDefaultInstance();
    }

}
