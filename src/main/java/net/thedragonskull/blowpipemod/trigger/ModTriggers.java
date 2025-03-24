package net.thedragonskull.blowpipemod.trigger;

import net.minecraft.advancements.CriteriaTriggers;

public class ModTriggers {
    public static final BlowpipeHamelinTrigger BLOWPIPE_HAMELIN = new BlowpipeHamelinTrigger();
    public static final ChargedCreeperTrigger CHARGED_CREEPER = new ChargedCreeperTrigger();
    public static final EvaporateWitherTrigger EVAPORATE_WITHER = new EvaporateWitherTrigger();
    public static final NightSnipeTrigger NIGHT_SNIPE = new NightSnipeTrigger();

    public static void register() {
        CriteriaTriggers.register(BLOWPIPE_HAMELIN);
        CriteriaTriggers.register(CHARGED_CREEPER);
        CriteriaTriggers.register(EVAPORATE_WITHER);
        CriteriaTriggers.register(NIGHT_SNIPE);
    }
}
