package net.thedragonskull.blowpipemod.trigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import java.util.function.Supplier;

public class ModTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES =
            DeferredRegister.create(Registries.TRIGGER_TYPE, BlowPipeMod.MOD_ID);


    public static final Supplier<BlowpipeHamelinTrigger> BLOWPIPE_HAMELIN =
            TRIGGER_TYPES.register("blowpipe_hamelin", BlowpipeHamelinTrigger::new);

    public static final Supplier<ChargedCreeperTrigger> CHARGED_CREEPER =
            TRIGGER_TYPES.register("blowpipe_charged_creeper", ChargedCreeperTrigger::new);

    public static final Supplier<EvaporateWitherTrigger> EVAPORATE_WITHER =
            TRIGGER_TYPES.register("blowpipe_evaporate_wither", EvaporateWitherTrigger::new);

    public static final Supplier<NightSnipeTrigger> NIGHT_SNIPE =
            TRIGGER_TYPES.register("blowpipe_night_snipe", NightSnipeTrigger::new);

    public static final Supplier<TripleEffectTrigger> TRIPLE_EFFECT =
            TRIGGER_TYPES.register("blowpipe_triple_effect", TripleEffectTrigger::new);

    public static final Supplier<FollowLeaderTrigger> FOLLOW_LEADER =
            TRIGGER_TYPES.register("blowpipe_follow_leader", FollowLeaderTrigger::new);

    public static final Supplier<CatCreeperTrigger> CAT_CREEPER =
            TRIGGER_TYPES.register("blowpipe_cat_creeper", CatCreeperTrigger::new);

    public static final Supplier<OblivionSuicideTrigger> OBLIVION_SUICIDE =
            TRIGGER_TYPES.register("blowpipe_oblivion_suicide", OblivionSuicideTrigger::new);


    public static void register(IEventBus eventBus) {
        TRIGGER_TYPES.register(eventBus);
    }
}
