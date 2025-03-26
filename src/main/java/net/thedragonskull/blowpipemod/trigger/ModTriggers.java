package net.thedragonskull.blowpipemod.trigger;

import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final BlowpipeHamelinTrigger BLOWPIPE_HAMELIN = new BlowpipeHamelinTrigger();
    public static final ChargedCreeperTrigger CHARGED_CREEPER = new ChargedCreeperTrigger();
    public static final EvaporateWitherTrigger EVAPORATE_WITHER = new EvaporateWitherTrigger();
    public static final NightSnipeTrigger NIGHT_SNIPE = new NightSnipeTrigger();
    public static final TripleEffectTrigger TRIPLE_EFFECT = new TripleEffectTrigger();
    public static final FollowLeaderTrigger FOLLOW_LEADER = new FollowLeaderTrigger();
    public static final CatCreeperTrigger CAT_CREEPER = new CatCreeperTrigger();

    public static void register() {
        CriteriaTriggers.register(BLOWPIPE_HAMELIN);
        CriteriaTriggers.register(CHARGED_CREEPER);
        CriteriaTriggers.register(EVAPORATE_WITHER);
        CriteriaTriggers.register(NIGHT_SNIPE);
        CriteriaTriggers.register(TRIPLE_EFFECT);
        CriteriaTriggers.register(FOLLOW_LEADER);
        CriteriaTriggers.register(CAT_CREEPER);
    }
}
