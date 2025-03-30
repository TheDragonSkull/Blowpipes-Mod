package net.thedragonskull.blowpipemod.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class EvaporateWitherTrigger extends SimpleCriterionTrigger<EvaporateWitherTrigger.Instance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    public Codec<EvaporateWitherTrigger.Instance> codec() {
        return EvaporateWitherTrigger.Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<EvaporateWitherTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EvaporateWitherTrigger.Instance::player)
        ).apply(instance, EvaporateWitherTrigger.Instance::new));
    }
}
