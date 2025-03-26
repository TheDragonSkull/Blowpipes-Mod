package net.thedragonskull.blowpipemod.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.entity.custom.LureDartProjectileEntity;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;

import java.util.List;

@Mod.EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    private static boolean isLureDartDMGSource = false;

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (!level.isClientSide && enemyHasAllEffects(entity)) {
            if (level.getNearestPlayer(entity, 50) instanceof ServerPlayer player) {
                ModTriggers.TRIPLE_EFFECT.trigger(player);
            }
        }

        if (!level.isClientSide && catChasingCreeper(entity) && isLureDartDMGSource) {
            if (level.getNearestPlayer(entity, 50) instanceof ServerPlayer player) {
                ModTriggers.CAT_CREEPER.trigger(player);
                player.addItem(new ItemStack(Items.SALMON, 5));
                isLureDartDMGSource = false;
            }
        }

    }

    private static boolean enemyHasAllEffects(LivingEntity entity) {
        return entity.hasEffect(MobEffects.POISON) &&
                entity.hasEffect(MobEffects.WITHER) &&
                entity.hasEffect(ModEffects.BLEED_EFFECT.get()) &&
                entity instanceof Enemy;
    }

    private static boolean catChasingCreeper(LivingEntity entity) {

        if (entity instanceof Creeper creeper) {
            if (creeper.hasEffect(ModEffects.CHARMING_AURA_EFFECT.get())) {
                AABB box = creeper.getBoundingBox().inflate(5);
                List<Cat> catsInArea = entity.level().getEntitiesOfClass(Cat.class, box);

                if (creeper.getLastHurtByMob() instanceof Player) {
                    if (!catsInArea.isEmpty()) {
                        for (Cat cat : catsInArea) {
                            if (cat.distanceTo(creeper) <= 5.0) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Entity source = event.getSource().getDirectEntity();

        if (source instanceof LureDartProjectileEntity) {

            if (entity instanceof Creeper) {
                isLureDartDMGSource = true;
            }
        }
    }


}
