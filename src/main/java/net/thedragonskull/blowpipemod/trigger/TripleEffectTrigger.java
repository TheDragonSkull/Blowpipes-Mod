package net.thedragonskull.blowpipemod.trigger;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class TripleEffectTrigger extends SimpleCriterionTrigger<TripleEffectTrigger.Instance> {

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    @Override
    public Codec<TripleEffectTrigger.Instance> codec() {
        return TripleEffectTrigger.Instance.CODEC;
    }

    public record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<TripleEffectTrigger.Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TripleEffectTrigger.Instance::player)
        ).apply(instance, TripleEffectTrigger.Instance::new));
    }
}
