package net.thedragonskull.blowpipemod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.thedragonskull.blowpipemod.BlowPipeMod;

public class OblivionSuicideTrigger extends SimpleCriterionTrigger<OblivionSuicideTrigger.Instance> {
    static final ResourceLocation ID = new ResourceLocation(BlowPipeMod.MOD_ID, "blowpipe_oblivion_suicide");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected OblivionSuicideTrigger.Instance createInstance(JsonObject json, ContextAwarePredicate playerPredicate, DeserializationContext context) {
        return new OblivionSuicideTrigger.Instance(playerPredicate);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, instance -> true);
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate playerPredicate) {
            super(OblivionSuicideTrigger.ID, playerPredicate);
        }
    }
}
