package net.thedragonskull.blowpipemod.entity.custom;


import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.entity.ModEntities;
import net.thedragonskull.blowpipemod.item.ModItems;

public class DartProjectileEntity extends AbstractDart {

    public DartProjectileEntity(EntityType<? extends DartProjectileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DartProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.DART.get(), level, shooter);
    }

    @Override
    protected float getDamage() {
        return 1.0F;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.DART_BASE.get().getDefaultInstance();
    }





}
