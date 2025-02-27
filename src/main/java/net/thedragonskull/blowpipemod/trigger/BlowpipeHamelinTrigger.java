package net.thedragonskull.blowpipemod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class BlowpipeHamelinTrigger extends SimpleCriterionTrigger<BlowpipeHamelinTrigger.Instance> {
    static final ResourceLocation ID = new ResourceLocation(BlowPipeMod.MOD_ID, "blowpipe_hamelin");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected BlowpipeHamelinTrigger.Instance createInstance(JsonObject json, ContextAwarePredicate playerPredicate, DeserializationContext context) {
        return new BlowpipeHamelinTrigger.Instance(playerPredicate);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate playerPredicate) {
            super(BlowpipeHamelinTrigger.ID, playerPredicate);
        }

        public static BlowpipeHamelinTrigger.Instance blowpipeSound() {
            return new BlowpipeHamelinTrigger.Instance(ContextAwarePredicate.ANY);
        }
    }
}
