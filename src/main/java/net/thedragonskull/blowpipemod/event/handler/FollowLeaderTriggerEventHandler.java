package net.thedragonskull.blowpipemod.event.handler;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.thedragonskull.blowpipemod.BlowPipeMod;
import net.thedragonskull.blowpipemod.effect.ModEffects;
import net.thedragonskull.blowpipemod.item.ModItems;
import net.thedragonskull.blowpipemod.trigger.ModTriggers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber(modid = BlowPipeMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class FollowLeaderTriggerEventHandler {
    private static final int REQUIRED_ANIMALS = 10;
    private static final int REQUIRED_TIME = 15 * 20; // 15 seconds in ticks
    private static final double RADIUS = 10.0;
    private static final Map<UUID, PlayerTrackingData> trackingData = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        PlayerTrackingData data = trackingData.computeIfAbsent(player.getUUID(), PlayerTrackingData::new);

        if (!player.hasEffect(ModEffects.CHARMING_AURA_EFFECT)) {
            data.reset();
            return;
        }

        // Count nearby animals
        AABB box = player.getBoundingBox().inflate(RADIUS);
        long animalCount = player.level().getEntitiesOfClass(Animal.class, box).size();

        if (animalCount >= REQUIRED_ANIMALS) {
            data.increaseTime();
        } else {
            data.reset();
        }

        // Check if advancement conditions are met
        if (data.getTime() >= REQUIRED_TIME) {
            ModTriggers.FOLLOW_LEADER.get().trigger(player);
            data.reset();
            player.addItem(new ItemStack(ModItems.LURE_DART.get(), 16));
        }
    }

    private static class PlayerTrackingData {
        private int time;

        public PlayerTrackingData(UUID uuid) {
            reset();
        }

        public void reset() {
            time = 0;
        }

        public void increaseTime() {
            time++;
        }

        public int getTime() {
            return time;
        }
    }
}
