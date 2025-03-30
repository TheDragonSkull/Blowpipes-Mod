package net.thedragonskull.blowpipemod.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.client.handler.ClientForgeHandler;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;

import java.util.Optional;

public class NightSnipeTrigger extends SimpleCriterionTrigger<NightSnipeTrigger.Instance> {

    @Override
    public Codec<NightSnipeTrigger.Instance> codec() {
        return NightSnipeTrigger.Instance.CODEC;
    }

    public void trigger(ServerPlayer player, LivingEntity target, double distance) {
        Level level = player.level();

        boolean isNight = level.getDayTime() % 24000 >= 13000;
        boolean isMonster = target instanceof Enemy;
        boolean farEnough = distance >= 30;
        boolean hasGoggles = RangeGogglesUtil.hasGogglesEquipped(player);
        boolean hasNightVision = ClientForgeHandler.isNightVision;

        if (isNight && isMonster && farEnough && hasGoggles && hasNightVision) {
            this.trigger(player, instance -> true);
        }
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<NightSnipeTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(NightSnipeTrigger.Instance::player)
        ).apply(instance, NightSnipeTrigger.Instance::new));
    }
}
