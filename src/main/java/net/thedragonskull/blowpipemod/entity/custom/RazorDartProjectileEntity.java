package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.entity.ModEntities;

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

}
