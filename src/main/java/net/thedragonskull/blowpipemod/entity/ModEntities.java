package net.thedragonskull.blowpipemod.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.entity.custom.*;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, BlowPipeMod.MOD_ID);

    public static final RegistryObject<EntityType<DartProjectileEntity>> DART =
            ENTITY_TYPES.register("dart",
                    () -> EntityType.Builder.<DartProjectileEntity>of(DartProjectileEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .build("dart"));

    public static final RegistryObject<EntityType<IronHeadDartProjectileEntity>> IRON_HEAD_DART =
            ENTITY_TYPES.register("iron_head_dart",
                    () -> EntityType.Builder.<IronHeadDartProjectileEntity>of(IronHeadDartProjectileEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .build("iron_head_dart"));

    public static final RegistryObject<EntityType<PoisonDartProjectileEntity>> POISON_DART =
            ENTITY_TYPES.register("poison_dart",
                    () -> EntityType.Builder.<PoisonDartProjectileEntity>of(PoisonDartProjectileEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .build("poison_dart"));

    public static final RegistryObject<EntityType<PowderDartProjectileEntity>> POWDER_DART =
            ENTITY_TYPES.register("powder_dart",
                    () -> EntityType.Builder.<PowderDartProjectileEntity>of(PowderDartProjectileEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .build("powder_dart"));

    public static final RegistryObject<EntityType<LureDartProjectileEntity>> LURE_DART =
            ENTITY_TYPES.register("lure_dart",
                    () -> EntityType.Builder.<LureDartProjectileEntity>of(LureDartProjectileEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .build("lure_dart"));

    public static final RegistryObject<EntityType<RazorDartProjectileEntity>> RAZOR_DART =
            ENTITY_TYPES.register("razor_dart",
                    () -> EntityType.Builder.<RazorDartProjectileEntity>of(RazorDartProjectileEntity::new, MobCategory.MISC)
                            .sized(0.2F, 0.2F)
                            .build("razor_dart"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
