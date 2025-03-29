package net.thedragonskull.blowpipemod.trigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thedragonskull.blowpipemod.BlowPipeMod;

import java.util.function.Supplier;

public class ModTriggers {

    public static final DeferredRegister<CriterionTrigger<?>> TRIGGER_TYPES =
            DeferredRegister.create(Registries.TRIGGER_TYPE, BlowPipeMod.MOD_ID);

    public static final Supplier<BlowpipeHamelinTrigger> BLOWPIPE_HAMELIN =
            TRIGGER_TYPES.register("blowpipe_hamelin", BlowpipeHamelinTrigger::new);

    public static final ChargedCreeperTrigger CHARGED_CREEPER = new ChargedCreeperTrigger();
    public static final EvaporateWitherTrigger EVAPORATE_WITHER = new EvaporateWitherTrigger();
    public static final NightSnipeTrigger NIGHT_SNIPE = new NightSnipeTrigger();
    public static final TripleEffectTrigger TRIPLE_EFFECT = new TripleEffectTrigger();
    public static final FollowLeaderTrigger FOLLOW_LEADER = new FollowLeaderTrigger();
    public static final CatCreeperTrigger CAT_CREEPER = new CatCreeperTrigger();
    public static final OblivionSuicideTrigger OBLIVION_SUICIDE = new OblivionSuicideTrigger();
}
