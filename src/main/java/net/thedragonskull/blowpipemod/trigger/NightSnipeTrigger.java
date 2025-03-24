package net.thedragonskull.blowpipemod.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.client.handler.ClientForgeHandler;
import net.thedragonskull.blowpipemod.util.RangeGogglesUtil;

public class NightSnipeTrigger extends SimpleCriterionTrigger<NightSnipeTrigger.Instance> {
    static final ResourceLocation ID = new ResourceLocation(BlowPipeMod.MOD_ID, "blowpipe_night_snipe");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected NightSnipeTrigger.Instance createInstance(JsonObject json, ContextAwarePredicate playerPredicate, DeserializationContext context) {
        return new NightSnipeTrigger.Instance(playerPredicate);
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

    public static class Instance extends AbstractCriterionTriggerInstance {
        public Instance(ContextAwarePredicate playerPredicate) {
            super(NightSnipeTrigger.ID, playerPredicate);
        }
    }
}
